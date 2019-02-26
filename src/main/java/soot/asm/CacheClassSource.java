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

//for srp
import com.ibm.j9ddr.vm29.pointer.SelfRelativePointer;
import com.ibm.j9ddr.vm29.types.U32;

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

      
      ClassReader clsr = new ClassReader(classsource);
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

    @Override
  public void close() {
	//not applicable
  }

byte[] tryWithMemModel(long addr){

    int len = 208;
    byte[] buffer = new byte[len];
    //    IProcess proc = new CacheMemory(ByteOrder.LITTLE_ENDIAN);
    CacheMemory memory = new CacheMemory(ByteOrder.LITTLE_ENDIAN);
    try{
	//	IProcess process = memory;
	//	IVMData aVMData = VMDataFactory.getVMData(proc);    
    }catch(Exception e){
	System.out.println("Could not setup ddr"+ e.getMessage());
    }

    memory.addMemorySource(new CacheMemorySource(addr, len));
    try{
	memory.getBytesAt(addr, buffer, 0 , len); 
	System.out.write(buffer);
    }catch(Exception e){
 System.out.println("Could not read the memory " + e.getMessage());
         e.printStackTrace(System.out);
    }
    return(buffer);
}



}
