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
	//just insert some no-ops for now...
	mv.visitInsn(0);
	mv.visitInsn(0);
	mv.visitInsn(0);
    }
}
