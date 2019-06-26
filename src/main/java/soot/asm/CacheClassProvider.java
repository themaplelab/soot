package soot.asm;
 
/*-
 * #%L
 * Soot - a J*va Optimization Framework
 * %%
 * Copyright (C) 2018 Kristen Newbury
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

import soot.ClassProvider;
import soot.ClassSource;
import soot.FoundFile;
import soot.SourceLocator;
import com.ibm.oti.shared.Shared;
import com.ibm.oti.shared.SharedClassHelperFactory;
import com.ibm.oti.shared.SharedClassURLClasspathHelper;

import java.net.URLClassLoader;
import java.net.URL;
import java.io.File;
import java.util.Arrays;

import com.ibm.oti.shared.HelperAlreadyDefinedException;
import java.net.MalformedURLException;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;

/**
 * OpenJ9 Shared Cache Class class provider.
 * 
 * @author Kristen Newbury
 */
public class CacheClassProvider implements ClassProvider {
    
  public ClassSource find(String cls) {

    byte[] romCookie = null;
    CacheMemorySingleton cacheMem = null;
    ByteBuffer wrapper = null;
    
    SharedClassHelperFactory factory = Shared.getSharedClassHelperFactory();
    if (factory == null) {
	System.err.print("Cannot return cache access factory.");
    }else{
	URL[] urls = null;
	URL url = null;
	URL testurl = null;
	SharedClassURLClasspathHelper helper = null;
	try{
	    //TODO eventually replace this
	    //use absolute path to classfile that we are trying to find class of
	    url = new URL("file:///root/soot/tests/");
	    //hackish
	    testurl = new URL("file:///root/soot/cacheTestClassfiles/");
	    urls = new URL[]{url, testurl};
	}catch (MalformedURLException e) {
	    System.out.println("Bad URL provided");
	    e.printStackTrace();
	}
	URLClassLoader loader = new URLClassLoader(urls);

	//get helper to find classes in cache
	try{
	    helper = factory.getURLClasspathHelper(loader, urls);
	}catch (HelperAlreadyDefinedException e) {
	    System.out.println("Helper already defined?"+e.getMessage());
	    e.printStackTrace();
	}
	
	//not sure if this is needed, think probably not?
	helper.confirmAllEntries();
	
	System.out.println("THIS IS CACHE START");
	try {
	    //for now this part happens every time
	    //maybe consider avoiding that later 
	    byte[] cacheInfo = helper.findSharedCache();
	    if (cacheInfo != null){
		wrapper = ByteBuffer.wrap(cacheInfo);
		//jni filled byte array
		wrapper.order(ByteOrder.nativeOrder());
		cacheMem = CacheMemorySingleton.getInstance();
	    }else{
		System.out.println("Cannot get cache start");
	    }
	}catch (Exception e){
	    System.out.println(e.getMessage());
	    e.printStackTrace();
	}

	//is this actually the format of what needs to be provided here? should it be just classname or full url to classfile?
	romCookie = helper.findSharedClass(cls, null);
	System.out.println(cls);
	if (romCookie == null) {
	    System.out.println("Cannot find class in cache.");
	}else{
  	    System.out.println("Found the method!");
	}
    }
    return romCookie == null ? null : new CacheClassSource(cls, romCookie, cacheMem, wrapper.getLong(), wrapper.getInt());
  }
}