package soot.asm.tests;
/*-                                                                                                                  
 * #%L                                                                                                               
 * Soot - a J*va Optimization Framework                                                                              
 * %%                                                                                                                
 * Copyright (C) 1997 - 2019                                                              
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
import com.google.common.io.Files;
import java.io.File;
import java.util.Arrays;
import java.io.IOException;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Type;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.ClassWriter;

import soot.Main;

/*                                                                                                          
 * This class handles generating the jimple and classfiles for each 
 * test specified in the tests.config file
 *
 * WHEN ADDING NEW TESTS TO SUITE: PUT THEM IN HERE TOO!
 */

public class cacheSrcTest{

    private static File testDir;
    private static File classFile;


    	/*                                                                                                          
         * for each test:                                                                                           
         * Test.genExampleInput writes the classfile                                                                
         * this.runSoot() gens the jimple                                                                           
         */

    public static void main(String[] args) throws IOException{


	//read the tests to perform
	String[] testsToRun = readFile();
	
	ClassWriter visitor = new ClassWriter(ClassWriter.COMPUTE_FRAMES | ClassWriter.COMPUTE_MAXS);

	for(int i=0; i<testsToRun.length; i++){

	    switch(testsToRun[i]){

	    case "LDCTest":
		LDCTest.genExampleInput(visitor);
                genExampleClassfile("LDCTest", visitor);
                runSoot("LDCTest");
		break;
	    case "AllOpsTest":
		ClassWriter specialvisitor = AllOpsTest.genExampleInput();
		//this visitor is "special" bc it does not have the "handle self" flags set... not sure if nec
		genExampleClassfile("AllOpsTest", specialvisitor);
		runSoot("AllOpsTest");
		break;
	    case "MethodExampleTest":
		MethodExampleTest.genExampleInput(visitor);
		genExampleClassfile("MethodExampleTest", visitor);
		runSoot("MethodExampleTest");
		break;
	    case "ReturnsTest":
		ReturnsTest.genExampleInput(visitor);
		genExampleClassfile("ReturnsTest", visitor);
		runSoot("ReturnsTest");
		break;
	    case "AnnotatedAnnotatedClassTest":
		AnnotatedAnnotatedClassTest.genExampleInput(visitor);
		genExampleClassfile("AnnotatedAnnotatedClassTest", visitor);
		runSoot("AnnotatedAnnotatedClassTest");
		break;
	    case "AnnotatedAnnotationTest":
		AnnotatedAnnotationTest.genExampleInput(visitor);
		genExampleClassfile("AnnotatedAnnotationTest", visitor);
		runSoot("AnnotatedAnnotationTest");
		break;
	    case "AnnotatedClassTest":
		AnnotatedClassTest.genExampleInput(visitor);
		genExampleClassfile("AnnotatedClassTest", visitor);
		runSoot("AnnotatedClassTest");
		break;
	    case "AnnotatedFieldTest":
		AnnotatedFieldTest.genExampleInput(visitor);
		genExampleClassfile("AnnotatedFieldTest", visitor);
		runSoot("AnnotatedFieldTest");
		break;
	    case "AnnotatedMethodTest":
		AnnotatedMethodTest.genExampleInput(visitor);
		genExampleClassfile("AnnotatedMethodTest", visitor);
		runSoot("AnnotatedMethodTest");
		break;
	    case "AnnotatedParameterTest":
		AnnotatedParameterTest.genExampleInput(visitor);
		genExampleClassfile("AnnotatedParameterTest", visitor);
		runSoot("AnnotatedParameterTest");
		break;
	    case "AnnotationTest":
		AnnotationTest.genExampleInput(visitor);
		genExampleClassfile("AnnotationTest", visitor);
		runSoot("AnnotationTest");
		break;
	    case "ArithmeticTest":
		ArithmeticTest.genExampleInput(visitor);
		genExampleClassfile("ArithmeticTest", visitor);
		runSoot("ArithmeticTest");
		break;
	    case "ArraysTest":
		ArraysTest.genExampleInput(visitor);
		genExampleClassfile("ArraysTest", visitor);
		runSoot("ArraysTest");
		break;
	    case "CompareArithmeticInstructions2Test":
		CompareArithmeticInstructions2Test.genExampleInput(visitor);
		genExampleClassfile("CompareArithmeticInstructions2Test", visitor);
		runSoot("CompareArithmeticInstructions2Test");
		break;
	    case "CompareArithmeticInstructionsTest":
		CompareArithmeticInstructionsTest.genExampleInput(visitor);
		genExampleClassfile("CompareArithmeticInstructionsTest", visitor);
		runSoot("CompareArithmeticInstructionsTest");
		break;
	    case "CompareInstructionsTest":
		CompareInstructionsTest.genExampleInput(visitor);
		genExampleClassfile("CompareInstructionsTest", visitor);
		runSoot("CompareInstructionsTest");
		break;
	    case "ConstantPoolTest":
		ConstantPoolTest.genExampleInput(visitor);
		genExampleClassfile("ConstantPoolTest", visitor);
		runSoot("ConstantPoolTest");
		break;
	    case "ControlStructuresTest":
		ControlStructuresTest.genExampleInput(visitor);
		genExampleClassfile("ControlStructuresTest", visitor);
		runSoot("ControlStructuresTest");
		break;
	    case "DupsTest":
		DupsTest.genExampleInput(visitor);
		genExampleClassfile("DupsTest", visitor);
		runSoot("DupsTest");
		break;
	    case "EnumTest":
		EnumTest.genExampleInput(visitor);
		genExampleClassfile("EnumTest", visitor);
		runSoot("EnumTest");
		break;
	    case "ExceptionTest":
		ExceptionTest.genExampleInput(visitor);
		genExampleClassfile("ExceptionTest", visitor);
		runSoot("ExceptionTest");
		break;
	    case "ExtendedArithmeticLibTest":
		ExtendedArithmeticLibTest.genExampleInput(visitor);
		genExampleClassfile("ExtendedArithmeticLibTest", visitor);
		runSoot("ExtendedArithmeticLibTest");
		break;
	    case "InnerClass2Test":
		InnerClass2Test.genExampleInput(visitor);
		genExampleClassfile("InnerClass2Test", visitor);
		runSoot("InnerClass2Test");
		break;
	    case "InnerClassTest":
		InnerClassTest.genExampleInput(visitor);
		genExampleClassfile("InnerClassTest", visitor);
		runSoot("InnerClassTest");
		break;
	    case "InstanceOfCastsTest":
		InstanceOfCastsTest.genExampleInput(visitor);
		genExampleClassfile("InstanceOfCastsTest", visitor);
		runSoot("InstanceOfCastsTest");
		break;
	    case "InterfaceTest":
		InterfaceTest.genExampleInput(visitor);
		genExampleClassfile("InterfaceTest", visitor);
		runSoot("InterfaceTest");
		break;
	    case "LineNumbersTest":
		LineNumbersTest.genExampleInput(visitor);
		genExampleClassfile("LineNumbersTest", visitor);
		runSoot("LineNumbersTest");
		break;
	    case "LogicalOperationsTest":
		LogicalOperationsTest.genExampleInput(visitor);
		genExampleClassfile("LogicalOperationsTest", visitor);
		runSoot("LogicalOperationsTest");
		break;
	    case "ModifiersTest":
		ModifiersTest.genExampleInput(visitor);
		genExampleClassfile("ModifiersTest", visitor);
		runSoot("ModifiersTest");
		break;
	    case "MonitorTest":
		MonitorTest.genExampleInput(visitor);
		genExampleClassfile("MonitorTest", visitor);
		runSoot("MonitorTest");
		break;
	    case "NullTypesTest":
		NullTypesTest.genExampleInput(visitor);
		genExampleClassfile("NullTypesTest", visitor);
		runSoot("NullTypesTest");
		break;
	    case "OuterClassTest":
		OuterClassTest.genExampleInput(visitor);
		genExampleClassfile("OuterClassTest", visitor);
		runSoot("OuterClassTest");
		break;
	    case "StoresTest":
		StoresTest.genExampleInput(visitor);
		genExampleClassfile("StoresTest", visitor);
		runSoot("StoresTest");
		break;
	    case "TryCatchTest":
		TryCatchTest.genExampleInput(visitor);
		genExampleClassfile("TryCatchTest", visitor);
		runSoot("TryCatchTest");
		break;
	    default:
		System.out.println("---------------------------------------------");
		System.out.println("Test in config file does not exist: "+ testsToRun[i]);
		System.out.println("---------------------------------------------");
		break;

	    }
	}
    }

    /*
     * Reads a config file for which tests to perform
     */
    
    public static String[] readFile() throws IOException {
        FileReader fileReader = new FileReader("/root/soot/src/main/java/soot/asm/tests/tests.config");
        BufferedReader bufferedReader = new BufferedReader(fileReader);
        List<String> lines = new ArrayList<String>();
        String line = null;
        while ((line = bufferedReader.readLine()) != null) {
            lines.add(line);
        }
        bufferedReader.close();
        return lines.toArray(new String[lines.size()]);
    }
    
    public static void genExampleClassfile(String classname, ClassWriter cv) throws IOException{
	
	testDir = new File("cacheTestClassfiles");
	if (!testDir.exists()) {
	    testDir.mkdir();
	}
	String fullclassname = classname + "Generated";
	classFile = new File(testDir, fullclassname+".class");
	Files.write(cv.toByteArray(), classFile);

	//TODO could add check here that this step succeeded, file exists before trying next step
    }

    private static void runSoot(String testclassname){

	String fulltestname = testclassname + "Generated";
	//-allow-phantom-refs so that only? this file will be handled, dont care about rest
	String[] commandLine = { "-pp", "-cp", testDir.getAbsolutePath() +":/root/soot/src/main/java", "-allow-phantom-refs", "-f", "J", "-d", testDir.toString(), fulltestname};

	System.out.println("Running test for: "+ fulltestname);
	System.out.println("Command Line: " + Arrays.toString(commandLine));
	
	Main.main(commandLine);
	
    }
    

     

}
