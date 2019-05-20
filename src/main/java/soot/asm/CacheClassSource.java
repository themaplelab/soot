package soot.asm;

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

import java.io.IOException;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.ByteArrayInputStream;

import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.Opcodes;

import soot.ClassSource;
import soot.FoundFile;
import soot.SootClass;
import soot.javaToJimple.IInitialResolver.Dependencies;

import java.util.Arrays;
import java.util.ArrayList;
import java.util.List;

import java.nio.ByteOrder;
import com.ibm.j9ddr.corereaders.memory.MemoryFault;
import com.ibm.j9ddr.tools.ddrinteractive.CacheMemory;
import com.ibm.j9ddr.tools.ddrinteractive.CacheMemorySource;

//for ddr init attempt
import com.ibm.j9ddr.IVMData;
import com.ibm.j9ddr.VMDataFactory;
import com.ibm.j9ddr.corereaders.memory.IProcess;

//for romclasspointer
import com.ibm.j9ddr.vm29.pointer.generated.J9ROMClassPointer;
import com.ibm.j9ddr.vm29.pointer.helper.J9ROMClassHelper;

//for srp
import com.ibm.j9ddr.vm29.pointer.SelfRelativePointer;
import com.ibm.j9ddr.vm29.types.U32;
import com.ibm.j9ddr.vm29.pointer.generated.J9UTF8Pointer;
//just kidding
//import static com.ibm.j9ddr.vm29.structure.J9JavaAccessFlags.*;

//for ddrclassloader
import com.ibm.j9ddr.J9DDRClassLoader;
import java.lang.reflect.Method;
import com.ibm.j9ddr.vm29.j9.DataType;

import  java.lang.reflect.Constructor;
import java.lang.reflect.Field;

//base address test
import com.ibm.j9ddr.vm29.pointer.generated.*;
import com.ibm.j9ddr.vm29.pointer.helper.J9RASHelper;
import com.ibm.j9ddr.vm29.pointer.AbstractPointer;



/**
 * Cache class source implementation.
 * 
 * @author Kristen Newbury
 */
class CacheClassSource extends ClassSource {

    private byte[] cookiesource;
    private byte[] classsource;
    
  /**
   * Constructs a new Cache class source.
   * 
   * @param cls
   *          fully qualified name of the class.
   * @param data
   *          stream containing data for class.
   */
  CacheClassSource(String cls, byte[] source) {
    super(cls);
    if (source == null) {
	throw new IllegalStateException("Error: The class source must not be null.");
    }
    this.cookiesource = source;
  }

  @Override
  public Dependencies resolve(SootClass sc) {
    InputStream d = null;
    try{
	System.out.println("Byte array contents: ");
	System.out.println(Arrays.toString(cookiesource));
	System.out.println("-------------");
	System.out.write(cookiesource);
	System.out.println("-------------");

	long maps = 0;
	maps |= 0x40000000;	 

	long addr = 0;
	for (int i = 0; i < 8; i++)
	    {
		addr += ((long) cookiesource[i+24] & 0xffL) << (8 * i);
	    }

	byte[] tempclasssource = tryWithMemModel(addr);	 
	System.out.println("ROM array contents: ");
	System.out.println(Arrays.toString(tempclasssource));
	System.out.println("-------------");

      
	ClassReader clsr = new ClassReader(tempclasssource);
	SootClassBuilder scb = new SootClassBuilder(sc);
	clsr.accept(scb, ClassReader.SKIP_FRAMES);
	Dependencies deps = new Dependencies();
	deps.typesToSignature.addAll(scb.deps);
	return deps;
    } catch (Exception e) {
	throw new RuntimeException("Error: Failed to create class reader from class source.", e);
    } finally {
	try {
	    if (d != null) {
		d.close();
		d = null;
	    }
	} catch (IOException e) {
	    throw new RuntimeException("Error: Failed to close source input stream.", e);
	} finally {
	    close();
	}
    }
  }

byte[] tryWithMemModel(long addr){

    int len = 520;
    byte[] buffer = new byte[len];
    byte[] classRep = null;
    //not great to hardcode len but will do for now
    CacheMemory memory = new CacheMemory(ByteOrder.LITTLE_ENDIAN);
    IProcess proc = (IProcess)memory;
    memory.addMemorySource(new CacheMemorySource(addr, len));
    try{
	//setup DDR - init datatype
	assert proc != null : "Process should not be null";
	IVMData aVMData = VMDataFactory.getVMData(proc);
	assert  aVMData != null : "VMDATA should not be null";


	//System.out.println("Our vm structures are: ");
	//System.out.println(aVMData.getStructures());

	
	J9DDRClassLoader ddrClassLoader = aVMData.getClassLoader();
	System.out.println("In cachesource, this is our classloader: ");
	System.out.println(ddrClassLoader);
	System.out.println(ddrClassLoader.hashCode());
	Class<?> clazz1 = ddrClassLoader.loadClass("com.ibm.j9ddr.vm29.pointer.generated.J9ROMClassPointer",true);
	System.out.println(clazz1.getClass());

	Class<?> redoclass = ddrClassLoader.loadClass("com.ibm.j9ddr.vm29.pointer.generated.J9RASPointer", true);
	
	System.out.println("WEIRDSANE");
	//	System.out.println(ddrClassLoader.getStructures());
	
	System.out.println("----------");
	ddrClassLoader.printCache();
	System.out.println("----------");
	
	System.out.println("THECLASSLOADER OF J9ClassPointer WILL BE: ");
        System.out.println(J9ClassPointer.class.getClassLoader());
	System.out.println(clazz1.getClassLoader());
	System.out.println("THECLASSLOADER OF DATATYPE WILL BE: ");
        System.out.println(DataType.class.getClassLoader().toString());
	
	J9ClassPointer classpointer = J9ClassPointer.cast(addr);
	J9ROMClassPointer instance = classpointer.romClass();
	System.out.println(instance.className());
	
	//if datatype is init'd - will be able to get process and create the romclass pointer
	classRep = romPointerRepExtraction(clazz1, addr, ddrClassLoader);
	
    }catch(Exception e){
	System.out.println("Could not setup ddr"+ e.getMessage());
	e.printStackTrace(System.out);
    }

    try{
	//fetch cache bytes as byte array
	memory.getBytesAt(addr, buffer, 0 , len);
	System.out.println("WRITINGBUF");
	System.out.println("-------");
	System.out.write(buffer);
	System.out.println("-------");
    }catch(Exception e){
	System.out.println("Could not read the memory " + e.getMessage());
        e.printStackTrace(System.out);
    }
    return(classRep);
}

    private byte[] romPointerRepExtraction(Class<?> clazz1, long addr, J9DDRClassLoader ddrClassLoader) throws Exception{
	byte[] classRepresentation = null;

	//obtain the romclasspointer object
	Method getStructureMethod = clazz1.getDeclaredMethod("cast", new Class[] { Long.TYPE });
        Object clazz = getStructureMethod.invoke(null, new Object[] { addr });
	
	
	//test to use this romclasspoiter                                                                                   
        Method modifiers = clazz1.getDeclaredMethod("modifiers", null);
	Object udataModifiers = modifiers.invoke(clazz);

	//temp. testing
        if(udataModifiers ==null){
            System.out.println("modifiers fetch gives null");
        }

        System.out.println("TEST FOR ROMCLASS CLASS COUNT\n");
        System.out.println(udataModifiers);
        System.out.println(udataModifiers.getClass());

	//string testing
	Method className = clazz1.getDeclaredMethod("className", null);
	Object fqn = className.invoke(clazz);


	System.out.println("TEST FOR ROMCLASS CLASS STR NAME\n");
        System.out.println(fqn);
        System.out.println(fqn.getClass());

	
	//test for base address of cache
	System.out.println("TESTING BASE ADDRESS");
	//doing this: DataType.getJ9RASPointer()
	Class<?> dataTypeClazz = ddrClassLoader.loadClassRelativeToStream("j9.DataType", false);
	Method getMethod = dataTypeClazz.getDeclaredMethod("getJ9RASPointer");
	Object pointer = getMethod.invoke(null);

	System.out.println(ClassLoader.class);
	
	 Field[] fields = ClassLoader.class.getDeclaredFields(); // Get system class loader
	 for(int i = 0; i < fields.length; i++) {
            System.out.println("The field is: " + fields[i].toString());
         }

	 Field apploader = ClassLoader.class.getDeclaredField("applicationClassLoader");
	 apploader.setAccessible(true); // Set accessible
	 System.out.println(apploader);
        apploader.set(null, ddrClassLoader); // Update it to your class loader
	 
	//but first
	System.out.println("THECLASSLOADER OF ABSTR WILL BE: ");
	System.out.println(AbstractPointer.class.getClassLoader());

	
	//now this: J9JavaVMPointer vm = J9RASHelper.getVM(DataType.getJ9RASPointer());
	Class<?> J9RASHelperClazz = ddrClassLoader.loadClass("com.ibm.j9ddr.vm29.pointer.helper.J9RASHelper", true);
	Method getVMMethod = J9RASHelperClazz.getDeclaredMethod("getVM", pointer.getClass());
	Object vm = getVMMethod.invoke(null, new Object[] { pointer });

	//plan to get J9RASPointer via reflection using cast on romclass address then use J9RASPointer.vm call

	
	

	/*	J9SharedClassConfigPointer sc = vm.sharedClassConfig();
	J9SharedClassCacheDescriptorPointer cacheDescriptor = sc.cacheDescriptorList();
	System.out.println(sc.getHexAddress());
	System.out.println(cacheDescriptor.cacheStartAddress());
	*/

	//asm class writer to give to give to asm class reader
	//just garbage in it so far, no real vals
	ClassWriter cw = new ClassWriter(0);
	cw.visit(Opcodes.V1_8, Opcodes.ACC_PUBLIC ,
		 "ConstraintErrorExample", null, "java/lang/Object",
		 null);
	cw.visitEnd();
	classRepresentation = cw.toByteArray();

	return classRepresentation;
    }


}
