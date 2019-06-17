package com.ibm.j9ddr.vm29;

/*-                                                                                                                                      
 * #%L                                                                                                                                   
 * Soot - a J*va Optimization Framework                                                                                                  
 * %%                                                                                                                                    
 * Copyright (C) 1997 - 2018 Kristen Newbury                                                                                             
 * %%                                                                                                                                    
 * This program is free software: you can redistribute it and/or modify                                                                  
 * it under the terms of the GNU Lesser General Public License as                                                                        
 * published by the Free Software Foundation, either version 2.1 of the                                                                  
 * License, or (at your option) any later version.                                                                                       
 *                                                                                                                                       
 * This program is distributed in the hope that it will be useful,                                                                       
 * but WITHOUT ANY WARRANTY; without even the implied warranty of                                                                        
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the                                                                         
 * GNU General Lesser Public License for more details.                                                                                   
 *                                                                                                                                       
 * You should have received a copy of the GNU General Lesser Public                                                                      
 * License along with this program.  If not, see                                                                                         
 * <http://www.gnu.org/licenses/lgpl-2.1.html>.                                                                                          
 * #L%                                                                                                                                   
 */
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
import com.ibm.j9ddr.vm29.pointer.helper.J9ROMMethodHelper;

import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.ClassReader;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Label;

import com.ibm.j9ddr.tools.ddrinteractive.CacheMemorySource;
import com.ibm.j9ddr.tools.ddrinteractive.CacheMemory;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

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
	    
	    //header class info
	    // version, int access, String name, String signature, String superName, String[] interfaces
	    classVisitor.visit(version, classModifiers, classname, "", superclassname, new String[] {});

	    //method handling
	    int methodCount = pointer.romMethodCount().intValue();
	    //first method, therefore use index 1 for loop start
	    J9ROMMethodPointer romMethod = pointer.romMethods();
	    readMethod(romMethod);
	    for(int i = 1; i < methodCount; i++){
		romMethod = ROMHelp.nextROMMethod(romMethod);
		readMethod(romMethod);
	    }
	    
	}catch(Exception e){
	    System.out.println("Issue in visitor pattern driving: " + e.getMessage());
	    e.printStackTrace(System.out);
	}
	//finish up
	classVisitor.visitEnd();
    }

    void readMethod(J9ROMMethodPointer romMethod) throws CorruptDataException{
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
	readMethodBody(bytecodeSt, bytecodeEnd, mv);
	//for now
        mv.visitMaxs(maxStack, maxLocals);
	mv.visitEnd();
    }

    void readMethodBody(long bytecodeSt, long bytecodeEnd, MethodVisitor mv){
	//drives the visitor to define the body of the method
	CacheMemorySource src = this.cacheMem.getMemorySource();
	long ptr = bytecodeSt;

	//TODO parse the actual max string length for a str in the const pool
	char[] c = new char[1000];
	//for our targets, as we find them
	//Label[] labels = new Label[(bytecodeEnd-bytecodeSt)];
	
        while(ptr < bytecodeEnd){
	    int offset = (int)(ptr - bytecodeSt);
	    int opcode = (int)(src.getByte(ptr) & 0xFF);
	    if((opcode == BCNames.JBnop) ||
	       (opcode == BCNames.JBinvokeinterface2)){
		ptr += 1;
	    }
	    else if((opcode == BCNames.JBdefaultvalue) ||
		    (opcode == BCNames.JBwithfield)){
		ptr += 2;
	    }
	    else if(opcode == BCNames.JBiincw){
		//TODO handle
		ptr += 5;
	    }
	    else if(opcode == BCNames.JBmultianewarray){
		mv.visitMultiANewArrayInsn(readClass(ptr + 1, c), src.getByte(ptr + 3) & 0xFF);  
                ptr += 4;
	    }
	    else if((opcode == BCNames.JBreturnFromConstructor) ||
		    (opcode == BCNames.JBgenericReturn) ||
		    (opcode == BCNames.JBreturnC) ||
		    (opcode == BCNames.JBreturnS) ||
		    (opcode == BCNames.JBreturnB) ||
		    (opcode == BCNames.JBreturnZ) ||
		    (opcode == BCNames.JBreturn0) ||
		    (opcode == BCNames.JBreturn1) ||
		    (opcode == BCNames.JBreturn2)){
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
		/*	    opcode == WIDE_INSN) ||
		opcode = src.getInt(ptr + 1);
		if (opcode == Opcodes.IINC) {
		    mv.visitIincInsn(src.getUnsignedShort(ptr + 2), src.getShort(ptr + 4));
		    ptr += 6;
		} else {
		    mv.visitVarInsn(opcode, src.getUnsignedShort(ptr + 2));
		    ptr += 4;
		}
		}*/
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
	    else if(opcode == BCNames.JBldc){
		mv.visitLdcInsn(readConst(src.getByte(ptr + 1) & 0xFF, c));
		ptr += 2;
	    }
	    else if((opcode == BCNames.JBldcw) ||
		    (opcode == BCNames.JBldc2lw) ||
		    (opcode == BCNames.JBldc2dw)){
		mv.visitLdcInsn(readConst(src.getUnsignedShort(ptr + 1), c));
		ptr += 3;
	    }
	    else if((opcode == BCNames.JBgetstatic) ||
		    (opcode == BCNames.JBputstatic) ||
		    (opcode == BCNames.JBgetfield) ||
		    (opcode == BCNames.JBputfield) ||
		    (opcode == BCNames.JBinvokevirtual) ||
		    (opcode == BCNames.JBinvokespecial) ||
		    (opcode == BCNames.JBinvokestatic) ||
		    (opcode == BCNames.JBinvokeinterface)) {
		/*int cpIndex = items[src.getUnsignedShort(ptr + 1)];
		boolean itf = src.getByte(cpIndex - 1) == ClassWriter.IMETH;
		String iowner = readClass(cpIndex, c);
		cpIndex = items[src.getUnsignedShort(cpIndex + 2)];
		String iname = readUTF8(cpIndex, c);
		String idesc = readUTF8(cpIndex + 2, c);
		if (opcode < Opcodes.INVOKEVIRTUAL) {
		    mv.visitFieldInsn(opcode, iowner, iname, idesc);
		} else {
		    mv.visitMethodInsn(opcode, iowner, iname, idesc, itf);
		    }*/
		if (opcode == Opcodes.INVOKEINTERFACE) {
		    ptr += 5;
		} else {
		    ptr += 3;
		}
	    }
	
	    else if (opcode == BCNames.JBinvokedynamic) {
	    /*		    int cpIndex = items[src.getUnsignedShort(ptr + 1)];
		int bsmIndex = context.bootstrapMethods[src.getUnsignedShort(cpIndex)];
		Handle bsm = (Handle) readConst(src.getUnsignedShort(bsmIndex), c);
		int bsmArgCount = src.getUnsignedShort(bsmIndex + 2);
		Object[] bsmArgs = new Object[bsmArgCount];
		bsmIndex += 4;
		for (int i = 0; i < bsmArgCount; i++) {
		    bsmArgs[i] = readConst(src.getUnsignedShort(bsmIndex), c);
		    bsmIndex += 2;
		}
		cpIndex = items[src.getUnsignedShort(cpIndex + 2)];
		String iname = readUTF8(cpIndex, c);
		String idesc = readUTF8(cpIndex + 2, c);
		mv.visitInvokeDynamicInsn(iname, idesc, bsm, bsmArgs);
	    */ptr += 5;
	    }	
	    else if(opcode == BCNames.JBnewdup){
		mv.visitTypeInsn(Opcodes.NEW, readClass(ptr + 1, c));
                ptr += 3;
	    }
	    else if((opcode == BCNames.JBnew) ||
		    (opcode == BCNames.JBanewarray) ||
		    (opcode == BCNames.JBcheckcast) ||
		    (opcode == BCNames.JBinstanceof)){
		mv.visitTypeInsn(opcode, readClass(ptr + 1, c));
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
    
    public Object readConst(final long address, final char[] buf) {
	return "";
    }
    
    public String readUTF8(final long address, final char[] buf){
        return "";
    }
    
    public String readClass(final long address, final char[] buf){
	return "";
    }
}
