package soot.asm;

/*-
 * #%L
 * Soot - a J*va Optimization Framework
 * %%
 * Copyright (C) 1997 - 2019 Kristen Newbury
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

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import com.ibm.j9ddr.corereaders.memory.MemoryFault;
import com.ibm.j9ddr.tools.ddrinteractive.CacheMemory;
import com.ibm.j9ddr.tools.ddrinteractive.CacheMemorySource;

//for ddr init attempt
import com.ibm.j9ddr.vm29.j9.VMData;
import com.ibm.j9ddr.IVMData;
import com.ibm.j9ddr.VMDataFactory;
import com.ibm.j9ddr.corereaders.memory.IProcess;

//for romclasspointer
import com.ibm.j9ddr.vm29.pointer.generated.J9ROMClassPointer;
import com.ibm.j9ddr.vm29.pointer.helper.J9ROMClassHelper;

import com.ibm.j9ddr.vm29.pointer.generated.J9UTF8Pointer;

//for ddrclassloader
import com.ibm.j9ddr.J9DDRClassLoader;
import java.lang.reflect.Method;
import com.ibm.j9ddr.vm29.j9.DataType;

import  java.lang.reflect.Constructor;
import java.lang.reflect.Field;

//base address test
import com.ibm.j9ddr.vm29.pointer.generated.*;
import com.ibm.j9ddr.vm29.pointer.AbstractPointer;

/**
 * Cache class source implementation.
 * 
 * @author Kristen Newbury
 */
public class CacheClassSource extends ClassSource {

    private byte[] cookiesource;
    private byte[] classsource;
    private CacheMemorySingleton memory;
    private long cacheaddr;
    private int cachesize;
  /**
   * Constructs a new Cache class source.
   * 
   * @param cls
   *          fully qualified name of the class.
   * @param data
   *          stream containing data for class.
   */
    CacheClassSource(String cls, byte[] source, CacheMemorySingleton memory, long cacheaddr, int cachesize) {
	super(cls);
	if (source == null) {
	    throw new IllegalStateException("Error: The class source must not be null.");
	}
	this.cookiesource = source;
	this.memory = memory;
	this.cacheaddr = cacheaddr;
	this.cachesize = cachesize;
    }

  @Override
  public Dependencies resolve(SootClass sc) {
    InputStream d = null;
    try{

	//TODO replace index (24) into cookie with the runtime val for offset of romclass address
	long addr = 0;
	for (int i = 0; i < 8; i++)
	    {
		addr += ((long) cookiesource[i+24] & 0xffL) << (8 * i);
	    }
	      
	Dependencies deps = new Dependencies();
	SootClassBuilder scb = new SootClassBuilder(sc);
	tryWithMemModel(addr, scb);
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

    void tryWithMemModel(long addr, SootClassBuilder scb){

	IProcess proc = (IProcess)memory.getMemory();
	try{
	    //setup DDR - init datatype
	    assert proc != null : "Process should not be null";
	    IVMData aVMData = VMDataFactory.getVMData(proc);
	    assert  aVMData != null : "VMDATA should not be null";

	    //now add the memory source                                                                                  
	    memory.addMemorySource(this.cacheaddr, this.cachesize);
	    
	    //can force our wrapper to be loaded by J9DDRClassLoader
	    aVMData.bootstrap("com.ibm.j9ddr.vm29.ROMClassWrapper", new Object[] {addr, scb, memory});

	}catch(Exception e){
	    System.out.println("Could not setup ddr"+ e.getMessage());
	    e.printStackTrace(System.out);
	}
	
    }
}
