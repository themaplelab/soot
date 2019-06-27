package com.ibm.j9ddr.vm29;

import com.ibm.j9ddr.vm29.j9.BCNames;

import com.ibm.j9ddr.CorruptDataException;
import com.ibm.j9ddr.NullPointerDereference;
import com.ibm.j9ddr.IBootstrapRunnable;
import com.ibm.j9ddr.IVMData;
import com.ibm.j9ddr.vm29.pointer.generated.J9ROMClassPointer;
import com.ibm.j9ddr.vm29.pointer.generated.J9UTF8Pointer;
import com.ibm.j9ddr.vm29.pointer.helper.J9UTF8Helper;
import com.ibm.j9ddr.vm29.j9.ROMHelp;
import com.ibm.j9ddr.vm29.pointer.generated.J9ROMMethodPointer;

import com.ibm.j9ddr.vm29.j9.DataType;
import com.ibm.j9ddr.vm29.pointer.generated.J9JavaVMPointer;
import com.ibm.j9ddr.vm29.pointer.helper.J9RASHelper;
import com.ibm.j9ddr.vm29.pointer.generated.J9SharedClassConfigPointer;
import com.ibm.j9ddr.vm29.pointer.generated.J9SharedClassCacheDescriptorPointer;
import com.ibm.j9ddr.vm29.pointer.generated.J9ROMNameAndSignaturePointer;
import com.ibm.j9ddr.vm29.pointer.helper.J9MethodHelper;
import com.ibm.j9ddr.vm29.pointer.helper.J9ROMClassHelper;
import com.ibm.j9ddr.vm29.pointer.helper.J9ROMMethodHelper;
import com.ibm.j9ddr.vm29.pointer.generated.J9ROMConstantPoolItemPointer;
import com.ibm.j9ddr.vm29.pointer.generated.J9ROMSingleSlotConstantRefPointer;
import com.ibm.j9ddr.vm29.pointer.generated.J9ROMStringRefPointer;
import com.ibm.j9ddr.vm29.pointer.generated.J9ROMFieldRefPointer;
import com.ibm.j9ddr.vm29.pointer.generated.J9ROMMethodRefPointer;
import com.ibm.j9ddr.vm29.pointer.generated.J9ROMClassRefPointer;
import com.ibm.j9ddr.vm29.pointer.generated.J9ROMMethodHandleRefPointer;

import com.ibm.j9ddr.vm29.types.UDATA;
import com.ibm.j9ddr.vm29.pointer.U16Pointer;
import com.ibm.j9ddr.vm29.pointer.SelfRelativePointer;

import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.ClassReader;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Label;
import org.objectweb.asm.Handle;

import com.ibm.j9ddr.tools.ddrinteractive.CacheMemorySource;
import com.ibm.j9ddr.tools.ddrinteractive.CacheMemory;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.nio.ByteOrder;

//this organization is not great, later we can refactor
//its too entangled with soot atm
import soot.javaToJimple.IInitialResolver.Dependencies;
import soot.SootClass;
import soot.asm.CacheClassSource;
import soot.asm.SootClassBuilder;
import soot.asm.CacheMemorySingleton;

/*                                                                                                                          
 * This implementation strongly relies upon the visitor invoke pattern defined in ASM Classreader:                                                             
 * https://gitlab.ow2.org/asm/asm/blob/ASM_5_2/src/org/objectweb/asm/ClassReader.java                                       
 * HOWEVER, did not want inheritance bc need to avoid asm ClassReader constructor behaviour which relies upon                              
 * many hardcoded indices                                                                                                                          
 */

public class ROMClassWrapper implements IBootstrapRunnable{

    //TODO fix. currently stolen hardcode from https://github.com/eclipse/openj9/blob/v0.14.0-release/runtime/oti/j9nonbuilder.h#L1965
    //the reason is that these are only defined in: com.ibm.j9ddr.vm29.structure.J9BCTranslationData
    //and only sizeof is exposed in: emacs openj9/debugtools/DDR_VM/src/com/ibm/j9ddr/vm29/pointer/generated/J9DescriptionBitsPointer.java
    //though somehow bytecode dumper can use ... https://github.com/eclipse/openj9/blob/v0.14.0-release/debugtools/DDR_VM/src/com/ibm/j9ddr/vm29/tools/ddrinteractive/commands/ByteCodeDumper.java#L96
    
    private int BCT_J9DescriptionCpTypeScalar  = 0;
    private int BCT_J9DescriptionCpTypeObject = 1;
    private int BCT_J9DescriptionCpTypeClass  = 2;

    private int J9DescriptionCpTypeShift = 4;
    /////////////////////////////////////

    
    private J9ROMClassPointer pointer;
    private CacheMemorySingleton cacheMem;
    private static ClassVisitor classVisitor;

    //classreader specific attributes
    static final boolean FRAMES = true;

    public void run(IVMData vmData, Object[] userData){

	Long addr = new Long((long)userData[0]);
	this.pointer = J9ROMClassPointer.cast(addr);

	this.classVisitor = (SootClassBuilder)userData[1];

	//need this for method iter, getting a bit much to have everyone here
	this.cacheMem = (CacheMemorySingleton)userData[2];

	accept(this.classVisitor);

    }

    public static ClassVisitor getClassVisitor(){
	return classVisitor;
    }

    public void accept(final ClassVisitor classVisitor) {

	try{
	    int version = pointer.majorVersion().intValue();
	    int classModifiers = pointer.modifiers().intValue();
	    String classname = J9UTF8Helper.stringValue(pointer.className());
	    String superclassname = J9UTF8Helper.stringValue(pointer.superclassName());

	    //reference to constant pool
	    J9ROMConstantPoolItemPointer constantPool = J9ROMClassHelper.constantPool(pointer);
	    
	    //header class info
	    // version, int access, String name, String signature, String superName, String[] interfaces
	    classVisitor.visit(version, classModifiers, classname, "", superclassname, new String[] {});

	    //method handling
	    int methodCount = pointer.romMethodCount().intValue();
	    //first method, therefore use index 1 for loop start
	    J9ROMMethodPointer romMethod = pointer.romMethods();
	    readMethod(romMethod, constantPool);
	    for(int i = 1; i < methodCount; i++){
		romMethod = ROMHelp.nextROMMethod(romMethod);
		readMethod(romMethod, constantPool);
	    }
	    
	}catch(Exception e){
	    System.out.println("Issue in visitor pattern driving: " + e.getMessage());
	    e.printStackTrace(System.out);
	}
	//finish up
	classVisitor.visitEnd();
    }

    void readMethod(J9ROMMethodPointer romMethod, J9ROMConstantPoolItemPointer constantPool) throws CorruptDataException{
	//method info                                                                                           
	int methodModifiers = romMethod.modifiers().intValue();
	J9ROMNameAndSignaturePointer nameAndSignature = romMethod.nameAndSignature();
	String name = J9UTF8Helper.stringValue(nameAndSignature.name());
	String signature= J9UTF8Helper.stringValue(nameAndSignature.signature());

	int maxStack = romMethod.maxStack().intValue();
	int maxLocals = romMethod.tempCount().intValue() + romMethod.argCount().intValue();
	int argCount = romMethod.argCount().intValue();
	System.out.println("The name and sig: "+ name +" " + signature);

	System.out.println("The max stack and max loc and arg count: "+ maxStack+" "+ maxLocals+" "+argCount );
	int romMethodSize = J9ROMMethodHelper.bytecodeSize(romMethod).intValue();
	System.out.println("The size of the method is :  "+romMethodSize);

	long bytecodeSt = J9ROMMethodHelper.bytecodes(romMethod).longValue();
	long bytecodeEnd = J9ROMMethodHelper.bytecodeEnd(romMethod).longValue();
	System.out.println("the st and end of the bytes: " + bytecodeSt+"and "+bytecodeEnd);
	//visitMethod(int access, String name, String desc, String signature, String[] exceptions)              
	MethodVisitor mv = classVisitor.visitMethod(methodModifiers, name, signature, signature, new String[] {});
	readMethodBody(bytecodeSt, bytecodeEnd, mv, constantPool);
	//for now
        mv.visitMaxs(maxStack, maxLocals);
	mv.visitEnd();
    }

    void readMethodBody(long bytecodeSt, long bytecodeEnd, MethodVisitor mv, J9ROMConstantPoolItemPointer constantPool) throws CorruptDataException{
	//drives the visitor to define the body of the method
	CacheMemorySource src = this.cacheMem.getMemorySource();
	long ptr = bytecodeSt;
	
	//for our targets, as we find them
	//Label[] labels = new Label[(bytecodeEnd-bytecodeSt)];
	
        while(ptr < bytecodeEnd){
	    int offset = (int)(ptr - bytecodeSt);
	    int opcode = (int)(src.getByte(ptr) & 0xFF);

	    System.out.println("The opcode value is: "+ opcode);
	    System.out.println("The opcode offset is: "+offset);
	    
	    if((opcode == BCNames.JBnop) ||
	       (opcode == BCNames.JBinvokeinterface2)){
		ptr += 1;
	    }
	    else if((opcode == BCNames.JBdefaultvalue) ||
		    (opcode == BCNames.JBwithfield)){
		//these only exist for #if defined(J9VM_OPT_VALHALLA_VALUE_TYPES)?
		ptr += 2;
	    }
	    else if(opcode == BCNames.JBiinc){
                int LVAindex = src.getByte(ptr+1);
                mv.visitIincInsn(LVAindex, (int)src.getByte(LVAindex));
                ptr += 2;
            }
	    else if(opcode == BCNames.JBiincw){
		int LVAindex = src.getShort(ptr+1);
		mv.visitIincInsn(LVAindex, (int)src.getShort(LVAindex));
		ptr += 5;
	    }
	    else if(opcode == BCNames.JBmultianewarray){

		int index = src.getShort(ptr+1);
		
		J9ROMConstantPoolItemPointer info = constantPool.add(index);

		int dim = src.getByte(ptr + 3) & 0xFF;

		String arrName = J9UTF8Helper.stringValue(J9ROMStringRefPointer.cast(info).utf8Data());
		mv.visitMultiANewArrayInsn(arrName, dim);  
                ptr += 4;
	    }
	    //TODO fix retfromConstructor
	    //actually maybe don't expect to see the returnFromConstructor:
	    //https://github.com/eclipse/openj9/blob/v0.14.0-release/runtime/compiler/ilgen/J9ByteCode.hpp
	    else if((opcode == BCNames.JBreturnFromConstructor) ||
		    (opcode == BCNames.JBgenericReturn) ||
                    (opcode == BCNames.JBreturn0)){
		mv.visitInsn(Opcodes.RETURN);
		ptr += 1;
	    }else if((opcode == BCNames.JBreturnC) ||
                    (opcode == BCNames.JBreturnS) ||
                    (opcode == BCNames.JBreturnB) ||
                    (opcode == BCNames.JBreturnZ)){
		mv.visitInsn(Opcodes.IRETURN);
		ptr += 1;
	    }
	    else if((opcode == BCNames.JBreturn1) ||
		    (opcode == BCNames.JBreturn2)){
		//return0,1,2 correspond to return(pop 0 slots), return(pop 1 slot) and return(pop 2 slots)
		//we can almost deal with the last two as ireturn lreturn , but this may have a type problem
		//TODO fix this...
		mv.visitInsn(Opcodes.RETURN);
		ptr += 1;
	    }
	    else if((opcode == BCNames.JBinvokehandle) ||
		    (opcode == BCNames.JBinvokehandlegeneric) ||
		    (opcode == BCNames.JBinvokestaticsplit) ||
		    (opcode == BCNames.JBinvokespecialsplit)){
		//TODO handle
		ptr += 3;
	    }
	    else if((opcode == BCNames.JBiload0) ||
		    (opcode == BCNames.JBiload1) ||
		    (opcode == BCNames.JBiload2) ||
		    (opcode == BCNames.JBiload3) ||
		    (opcode == BCNames.JBlload0) ||
		    (opcode == BCNames.JBlload1) ||
		    (opcode == BCNames.JBlload2) ||
		    (opcode == BCNames.JBlload3) ||
		    (opcode == BCNames.JBfload0) ||
		    (opcode == BCNames.JBfload1) ||
		    (opcode == BCNames.JBfload2) ||
		    (opcode == BCNames.JBfload3) ||
		    (opcode == BCNames.JBdload0) ||
		    (opcode == BCNames.JBdload1) ||
		    (opcode == BCNames.JBdload2) ||
		    (opcode == BCNames.JBdload3) ||
		    (opcode == BCNames.JBaload0) ||
		    (opcode == BCNames.JBaload1) ||
		    (opcode == BCNames.JBaload2) ||
		    (opcode == BCNames.JBaload3) ||
		    
		    (opcode == BCNames.JBistore0) ||
                    (opcode == BCNames.JBistore1) ||
                    (opcode == BCNames.JBistore2) ||
                    (opcode == BCNames.JBistore3) ||
                    (opcode == BCNames.JBlstore0) ||
                    (opcode == BCNames.JBlstore1) ||
                    (opcode == BCNames.JBlstore2) ||
                    (opcode == BCNames.JBlstore3) ||
                    (opcode == BCNames.JBfstore0) ||
                    (opcode == BCNames.JBfstore1) ||
                    (opcode == BCNames.JBfstore2) ||
                    (opcode == BCNames.JBfstore3) ||
                    (opcode == BCNames.JBdstore0) ||
                    (opcode == BCNames.JBdstore1) ||
                    (opcode == BCNames.JBdstore2) ||
                    (opcode == BCNames.JBdstore3) ||
                    (opcode == BCNames.JBastore0) ||
                    (opcode == BCNames.JBastore1) ||
                    (opcode == BCNames.JBastore2) ||
                    (opcode == BCNames.JBastore3)){
		if (opcode > Opcodes.ISTORE) {
                    opcode -= 59; // ISTORE_0
                    mv.visitVarInsn(Opcodes.ISTORE + (opcode >> 2),
                            opcode & 0x3);
                } else {
                    opcode -= 26; // ILOAD_0
                    mv.visitVarInsn(Opcodes.ILOAD + (opcode >> 2), opcode & 0x3);
                }
		ptr += 1;	
	    }
	    else if(opcode == BCNames.JBaload0getfield){
		 mv.visitVarInsn(Opcodes.ILOAD + (opcode >> 2), opcode & 0x3);
		 ptr += 1;
	    }
	    else if((opcode == BCNames.JBifeq) ||
	    (opcode == BCNames.JBifne) ||
	    (opcode == BCNames.JBiflt) ||
	    (opcode == BCNames.JBifge) ||
	    (opcode == BCNames.JBifgt) ||
	    (opcode == BCNames.JBifle) ||
	    (opcode == BCNames.JBificmpeq) ||
	    (opcode == BCNames.JBificmpne) ||
	    (opcode == BCNames.JBificmplt) ||
	    (opcode == BCNames.JBificmpge) ||
	    (opcode == BCNames.JBificmpgt) ||
	    (opcode == BCNames.JBificmple) ||
	    (opcode == BCNames.JBifacmpeq) ||
	    (opcode == BCNames.JBifacmpne) ||
	    (opcode == BCNames.JBgoto) ||
	    (opcode == BCNames.JBifnull) ||
		    (opcode == BCNames.JBifnonnull)){
		//	    createLabel(opcode, ptr, labels);
		//mv.visitJumpInsn(opcode, labels[offset + src.getShort(ptr + 1)]);
		ptr += 3;
	    }
	    else if(opcode == BCNames.JBgotow){
		//mv.visitJumpInsn(opcode + opcodeDelta, labels[offset
		//						  + src.getInt(ptr + 1)]);
		ptr += 5;
		}
	    else if(opcode == 196){
	    //196 == WIDE                                                                                            
            //BCNames does not have a wide opcode...                                                                     
            //but both Walker https://github.com/eclipse/openj9/blob/v0.14.0-release/runtime/compiler/ilgen/Walker.cpp#L1494                                                                                                                      
            //and ilgen maybe expect it to exist https://github.com/eclipse/openj9/blob/v0.14.0-release/runtime/compiler/ilgen/J9ByteCode.hpp#L111   
		opcode = src.getInt(ptr + 1);
		if (opcode == BCNames.JBiinc) {
		    mv.visitIincInsn(src.getUnsignedShort(ptr + 2), src.getShort(ptr + 4));
		    ptr += 6;
		} else {
		    mv.visitVarInsn(opcode, src.getUnsignedShort(ptr + 2));
		    ptr += 4;
		}
	    }
	    else if(opcode == BCNames.JBtableswitch)
		{
		// skips 0 to 3 padding bytes
		ptr = ptr + 4 - (offset & 3);
		// reads instrptrction
		//    int label = offset + src.getInt(ptr);
		//int min = src.getInt(ptr + 4);
		//int max = src.getInt(ptr + 8);
		//Label[] table = new Label[max - min + 1];
		ptr += 12;
		//for (int i = 0; i < table.length; ++i) {
		//	table[i] = labels[offset + src.getInt(ptr)];
		//	ptr += 4;
		//}
		//mv.visitTableSwitchInsn(min, max, labels[label], table);
		}
	    else if(opcode == BCNames.JBlookupswitch){
		// skips 0 to 3 padding bytes
		ptr = ptr + 4 - (offset & 3);
		// reads instruction
		int label = offset + src.getInt(ptr);
		int len = src.getInt(ptr + 4);
		int[] keys = new int[len];
		Label[] values = new Label[len];
		ptr += 8;
		for (int i = 0; i < len; ++i) {
		    keys[i] = src.getInt(ptr);
		    //values[i] = labels[offset + src.getInt(ptr + 4)];
		    ptr += 8;
		    }
		//mv.visitLookupSwitchInsn(labels[label], keys, values);
	    }
	    else if(opcode == BCNames.JBiloadw){
		//TODO double check these
		mv.visitVarInsn(Opcodes.ILOAD, src.getInt(ptr + 1));
		ptr += 3;
	    }
	    else if(opcode == BCNames.JBlloadw){
		mv.visitVarInsn(Opcodes.LLOAD, src.getInt(ptr + 1));
		ptr += 3;
	    }
	    else if(opcode == BCNames.JBfloadw){
		mv.visitVarInsn(Opcodes.FLOAD, src.getInt(ptr + 1));
		ptr += 3;
                }
            else if(opcode == BCNames.JBdloadw){
		mv.visitVarInsn(Opcodes.DLOAD, src.getInt(ptr + 1));
                ptr += 3;
                }
	    else if(opcode == BCNames.JBaloadw){
		mv.visitVarInsn(Opcodes.ALOAD, src.getInt(ptr + 1));
		ptr += 3;
                }
            else if(opcode == BCNames.JBistorew){
		mv.visitVarInsn(Opcodes.ISTORE, src.getInt(ptr + 1));
                ptr += 3;
                }
	    else if(opcode == BCNames.JBlstorew){
		mv.visitVarInsn(Opcodes.LSTORE, src.getInt(ptr + 1));
		ptr += 3;
	    }
            else if(opcode == BCNames.JBfstorew){
		mv.visitVarInsn(Opcodes.FSTORE, src.getInt(ptr + 1));
                ptr += 3;
	    }
	    else if(opcode == BCNames.JBdstorew){
		mv.visitVarInsn(Opcodes.DSTORE, src.getInt(ptr + 1));
		ptr += 3;
	    }
	    else if(opcode == BCNames.JBastorew){
		mv.visitVarInsn(Opcodes.ASTORE, src.getInt(ptr + 1));
                ptr += 3;
	    }
	    else if((opcode == BCNames.JBistore) ||
		    (opcode == BCNames.JBlstore) ||
		    (opcode == BCNames.JBfstore) ||
		    (opcode == BCNames.JBdstore) ||
		    (opcode == BCNames.JBastore) ||
		    (opcode == BCNames.JBiload) ||
		    (opcode == BCNames.JBlload) ||
		    (opcode == BCNames.JBfload) ||
		    (opcode == BCNames.JBdload) ||
		    (opcode == BCNames.JBaload)){
		mv.visitVarInsn(opcode, src.getByte(ptr + 1) & 0xFF);
		ptr += 2;
	    }
	    else if((opcode == BCNames.JBnewarray) ||
		    (opcode == BCNames.JBbipush)){
		mv.visitIntInsn(opcode, src.getByte(ptr + 1));
		ptr += 2;
	    }
	    else if(opcode == BCNames.JBsipush){
		mv.visitIntInsn(opcode, src.getShort(ptr + 1));
		ptr += 3;
	    }
	    //not sure if its most readable to handle all separate or do a immediate double check on op val
	    else if(opcode == BCNames.JBldc){
		
		mv.visitLdcInsn(readConst(src.getByte(ptr + 1) & 0xFF, constantPool));
		ptr += 2;
	    }
	    else if (opcode == BCNames.JBldcw){
		mv.visitLdcInsn(readConst(src.getShort(ptr + 1), constantPool));
                ptr += 3;
	    }
	    else if(opcode == BCNames.JBldc2lw){

		int index = src.getShort(ptr + 1);
		J9ROMConstantPoolItemPointer info = constantPool.add(index);

		//since we are working with infoslots the order check is a bit ugly against the mem model, but must do
		long constantvalue = (src.getByteOrder() == ByteOrder.BIG_ENDIAN) ? ((info.slot1().longValue()) << 32) | (info.slot2().longValue() & 0xffffffffL) : ((info.slot2().longValue()) << 32) | (info.slot1().longValue() & 0xffffffffL);

		System.out.println("slto1 then 2: "+ info.slot1().longValue()+ " " +info.slot2().longValue());
		System.out.println("ldc2lw value is: "+ constantvalue);
		mv.visitLdcInsn(constantvalue);
                ptr += 3;
            }
	    else if(opcode == BCNames.JBldc2dw){

		int index = src.getShort(ptr + 1);
		J9ROMConstantPoolItemPointer info = constantPool.add(index);

                //since we are working with infoslots the order check is a bit ugly against the mem model, but must do
		long constantvalue = (src.getByteOrder() == ByteOrder.BIG_ENDIAN) ? ((info.slot1().longValue()) << 32) | (info.slot2().longValue() & 0xffffffffL) : ((info.slot2().longValue()) << 32) | (info.slot1().longValue() & 0xffffffffL);
                System.out.println("ldc2dw value is: "+  Double.longBitsToDouble(constantvalue));
		mv.visitLdcInsn(Double.longBitsToDouble(constantvalue));
                ptr += 3;
	    }
	    else if((opcode == BCNames.JBgetstatic) ||
		    (opcode == BCNames.JBputstatic) ||
		    (opcode == BCNames.JBgetfield) ||
		    (opcode == BCNames.JBputfield)){ 

		int index = src.getUnsignedShort(ptr+1);		
		J9ROMConstantPoolItemPointer info = constantPool.add(index);

		J9ROMFieldRefPointer romFieldRef = J9ROMFieldRefPointer.cast(info);
		String owner = J9UTF8Helper.stringValue(J9ROMClassRefPointer.cast(constantPool.add(romFieldRef.classRefCPIndex())).name());

		J9ROMNameAndSignaturePointer nameAndSig = romFieldRef.nameAndSignature();
		String name = J9UTF8Helper.stringValue(nameAndSig.name());
		String desc = J9UTF8Helper.stringValue(nameAndSig.signature());
		mv.visitFieldInsn(opcode, owner, name, desc);

		ptr += 3;
		
		//this has a strong duplication with the prev case except pointer types
		//TODO maybe, clean, maybe
	    }else if((opcode == BCNames.JBinvokevirtual) ||
                    (opcode == BCNames.JBinvokespecial) ||
                    (opcode == BCNames.JBinvokestatic) ||
                    (opcode == BCNames.JBinvokeinterface)){

		int index = src.getUnsignedShort(ptr+1);
		J9ROMConstantPoolItemPointer info = constantPool.add(index);
		
		J9ROMMethodRefPointer romMethodRef = J9ROMMethodRefPointer.cast(info);
		UDATA classRefCPIndex = romMethodRef.classRefCPIndex();
		J9ROMConstantPoolItemPointer cpItem = constantPool.add(classRefCPIndex);
		J9ROMClassRefPointer romClassRef = J9ROMClassRefPointer.cast(cpItem);
		
		String owner = J9UTF8Helper.stringValue(romClassRef.name());
		
		J9ROMNameAndSignaturePointer nameAndSig = romMethodRef.nameAndSignature();
		String name = J9UTF8Helper.stringValue(nameAndSig.name());
		String desc = J9UTF8Helper.stringValue(nameAndSig.signature()); 
		
		boolean itf = (opcode == BCNames.JBinvokeinterface);

		mv.visitMethodInsn(opcode, owner, name, desc, itf);

		if (itf) {
		    ptr += 5;
		} else {
		    ptr += 3;
		}
	    }

	    //TODO finish aka get real rest of info for handle
	    else if (opcode == BCNames.JBinvokedynamic) {
	
		int index = src.getShort(ptr + 1);

		long callSiteCount = pointer.callSiteCount().longValue();
		SelfRelativePointer callSiteData = SelfRelativePointer.cast(pointer.callSiteData());
		U16Pointer bsmIndices = U16Pointer.cast(callSiteData.addOffset(4*callSiteCount));

		//just a check for now about who the caller(s) are, not really needed for reader
		//TODO rm
		for(int i =0; i < callSiteCount; i++){
		    J9ROMNameAndSignaturePointer callerNameAndSig = J9ROMNameAndSignaturePointer.cast(callSiteData.add(i).get());
		    String callername = J9UTF8Helper.stringValue(callerNameAndSig.name());
		    String callerdesc = J9UTF8Helper.stringValue(callerNameAndSig.signature());
		    System.out.println("The caller name: " + callername +" and signature: " + callerdesc);
		}

		//gets a pointer to the exact entry in the callSiteData table to the bsm data of this invoked method
		int bsmIndex = bsmIndices.at(index).intValue(); //Bootstrap method index
		U16Pointer bsmDataCursor = bsmIndices.add(callSiteCount);
		bsmDataCursor = bsmDataCursor.add(bsmIndex);
		
		J9ROMMethodHandleRefPointer methodHandleRef = J9ROMMethodHandleRefPointer.cast(constantPool.add(bsmDataCursor.at(0).longValue()));
		J9ROMMethodRefPointer methodRef = J9ROMMethodRefPointer.cast(constantPool.add(methodHandleRef.methodOrFieldRefIndex().longValue()));

		//callee
		J9ROMNameAndSignaturePointer nameAndSig = methodRef.nameAndSignature();
		String name = J9UTF8Helper.stringValue(nameAndSig.name());
		String desc = J9UTF8Helper.stringValue(nameAndSig.signature());
		
		int bsmArgCount = bsmDataCursor.at(1).intValue();
		bsmDataCursor = bsmDataCursor.add(2);

		//map this info into the asm understanding of a handle
	        int methodType = methodHandleRef.handleTypeAndCpType().rightShift((int)J9DescriptionCpTypeShift).intValue();
		J9ROMClassRefPointer classRef = J9ROMClassRefPointer.cast(constantPool.add(methodRef.classRefCPIndex().longValue()));
		String owner = J9UTF8Helper.stringValue(classRef.name());
		//public Handle(int tag, String owner, String name, String desc, boolean itf)
		Handle bsm = new Handle(methodType, owner, name, desc, false);
		Object[] bsmArgs = new Object[bsmArgCount];

		//populate the invoked method args array
		for (int i = 0; i < bsmArgCount; i++) {
		    int argCPIndex = bsmDataCursor.at(0).intValue();
		    //TODO check if this is handled correctly, actually may need refactor of readConst, not
		    //sure if all types are going to be handled correctly, and if we need the cpShapeDesc actually
		    bsmArgs[i] = readConst(argCPIndex, constantPool);
		    bsmDataCursor = bsmDataCursor.add(1);
		}

		//how is providing name+desc not redundant with the bsm handle also haing those...
		mv.visitInvokeDynamicInsn(name, desc, bsm, bsmArgs);
		ptr += 5;
	    }	
	    else if((opcode == BCNames.JBnewdup) ||
                    (opcode == BCNames.JBnew)||
                    (opcode == BCNames.JBanewarray) ||
                    (opcode == BCNames.JBcheckcast) ||
                    (opcode == BCNames.JBinstanceof)){
		//newdup is the only openj9 specific
		if(opcode == BCNames.JBnewdup){
		    opcode = Opcodes.NEW;
		}
		int index = src.getShort(ptr+1);
		J9ROMConstantPoolItemPointer info = constantPool.add(index);
		String classname = J9UTF8Helper.stringValue(J9ROMStringRefPointer.cast(info).utf8Data());
		mv.visitTypeInsn(opcode, classname);
                ptr += 3;
	    }
	    else if(opcode == BCNames.JBiinc){
		mv.visitIincInsn(src.getByte(ptr + 1) & 0xFF, src.getByte(ptr + 2));
		ptr += 3;
		}
	    else{
		try{
		    mv.visitInsn(opcode);
		}
		catch(Exception e){
		    System.out.println("Encountered some not handled openj9 specific opcode: "+opcode);
		}
                ptr += 1;
	    }
	}
    }
    //populates a label table for us, to use later, that represent targets of jumps
    public void createLabel(byte opcode, long ptr, Label[] labels){
	int index;
	if(opcode == BCNames.JBgotow) {
	    index = 1;
	}else{
	    index = 2;
	}
	
    }

    /*
     * Reads a constant pool info entry
     *
     */
    
    public Object readConst(final int index, J9ROMConstantPoolItemPointer constantPool) throws CorruptDataException{

	System.out.println("The index is: "+ index);
	
	J9ROMConstantPoolItemPointer info = constantPool.add(index);
	J9ROMSingleSlotConstantRefPointer romSingleSlotConstantRef = J9ROMSingleSlotConstantRefPointer.cast(info);

	if (!romSingleSlotConstantRef.cpType().eq(BCT_J9DescriptionCpTypeScalar)) {
	    /* this is a string or class constant */
	    //TODO check if this handles class internal name as well
	    System.out.println("Constant pool str condition" + romSingleSlotConstantRef.cpType());
	    System.out.println("Constant pool str" + J9UTF8Helper.stringValue(J9ROMStringRefPointer.cast(info).utf8Data()));
	    return J9UTF8Helper.stringValue(J9ROMStringRefPointer.cast(info).utf8Data());
	    
	}else {
	    /* this is a float/int constant */
	    System.out.println("Constant pool int/float condition" + romSingleSlotConstantRef.cpType());
	    System.out.println("Constant pool int: "+ romSingleSlotConstantRef.data().longValue());
		return romSingleSlotConstantRef.data().longValue();	    
	}
    }
    
    
}
