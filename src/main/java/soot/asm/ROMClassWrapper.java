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

public class ROMClassWrapper implements IBootstrapRunnable{

    private J9ROMClassPointer pointer;
    private static byte[] classrep;
    
    public void run(IVMData vmData, Object[] userData){

	Long addr = new Long((long)userData[0]);
	
	System.out.println("TESTING THE WRAPPER!");
	System.out.println(addr);

	System.out.println("THECLASSLOADER OF J9ClassPointer WILL BE: ");
        System.out.println(J9ROMClassPointer.class.getClassLoader());

	pointer = J9ROMClassPointer.cast(addr);

	try{
	J9UTF8Pointer name = pointer.superclassName();
	if (name != J9UTF8Pointer.NULL){
	    System.out.println("RUNTESTsuperNAME");
	    System.out.println(name);
	}else{
	    System.out.println("FAILED to get super name");
	}
	
	System.out.println("Major version");
	System.out.println(pointer.majorVersion());
	
	}catch (CorruptDataException e){
	    System.out.println("Cannot get pointer name" + e.getMessage());

	}
	this.classrep = new byte[10];
    }

    public static byte[] getClassRep(){
	return classrep;
    }

}
