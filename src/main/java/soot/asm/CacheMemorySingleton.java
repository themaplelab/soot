package soot.asm;

/*-                                                                                                                                      
 * #%L                                                                                                                                   
 * Soot - a J*va Optimization Framework                                                                                                  
 * %%                                                                                                                                    
 * Copyright (C) 2019 Kristen Newbury                                                                                                    
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

import java.nio.ByteOrder;

import com.ibm.j9ddr.tools.ddrinteractive.CacheMemory;
import com.ibm.j9ddr.tools.ddrinteractive.CacheMemorySource;

public class CacheMemorySingleton{

    //this singleton can only have one memory source ever added to it
    private static CacheMemorySingleton cacheMemorySingleton;
    private CacheMemory memory;
    private CacheMemorySource memorySource;
    
    private CacheMemorySingleton(){
	memory = new CacheMemory(ByteOrder.nativeOrder());                                                                   
    }

    public static CacheMemorySingleton getInstance(){
	if (cacheMemorySingleton == null) {
            cacheMemorySingleton = new CacheMemorySingleton(); 
	}  
        return cacheMemorySingleton; 
    } 

    public CacheMemory getMemory(){
	return memory;
    }

    public CacheMemorySource getMemorySource(){
	return memorySource;
    }
    
    public void addMemorySource(long addr, int size){
	if (cacheMemorySingleton != null && memorySource == null) {
	    memorySource = new CacheMemorySource(addr, size);
	    memory.addMemorySource(memorySource);
	}
    }
    
}
