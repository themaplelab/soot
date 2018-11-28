package soot;

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

import com.ibm.oti.shared.HelperAlreadyDefinedException;
import java.net.MalformedURLException;

/**
 * OpenJ9 Shared Cache Class class provider.
 * 
 * @author Kristen Newbury
 */
public class CacheClassProvider implements ClassProvider {

  public ClassSource find(String cls) {

    SharedClassHelperFactory factory = com.ibm.oti.shared.Shared.getSharedClassHelperFactory();
    if (factory == null) {
      System.err.print("Cannot return cache access factory.");
    }else{

	URL[] urls = null;
	URL url = null;
	SharedClassURLClasspathHelper helper = null;

	try{
	    //use absolute path to classfile that we are trying to find class of
	    File file = new File("/root/soot");
	    url = file.toURI().toURL(); 
	    urls = new URL[]{new URL("file:///root/soot/")};
	}catch (MalformedURLException e) {
	    System.out.println("Bad URL provided");
	    e.printStackTrace();
	}
	URLClassLoader loader = new URLClassLoader(urls);

	//just a test, based on this url can a classloader find this class. the answer is yes of course.
	/*	try{
	    Class c = loader.loadClass("ConstraintErrorExample");
	}catch(ClassNotFoundException e){
	    System.out.println("Cannot load this class at all\n");
            e.printStackTrace();
	    }*/
	

	//get helper to find classes in cache
	try{
	    helper = factory.getURLClasspathHelper(loader, urls);
	}catch (HelperAlreadyDefinedException e) {}
	
	//not sure if this is needed, think probably not?
	helper.confirmAllEntries();
	//is this actually the format of what needs to be provided here? should it be just classname or full url to classfile?
	byte[] result = helper.findSharedClass("ConstraintErrorExample", null);
	if (result == null) {
	    System.err.print("Cannot find class in cache.\n");
	}else{
  	    System.out.println("Found the method!");
	}
    }
    return new CacheClassSource("");
  }
}
