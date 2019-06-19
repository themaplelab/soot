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
import org.objectweb.asm.util.TraceClassVisitor;

import soot.Main;

/*                                                                                                          
 * This class handles generating the jimple and classfiles for each 
 * test specified in the tests.config file
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

	    case "AllOpsTest":
		ClassWriter specialvisitor = AllOpsTest.genExampleInput();
		genExampleClassfile("AllOpsTest", specialvisitor);
		runSoot("AllOpsTest");
		break;
	    case "MethodExampleTest":
		MethodExampleTest.genExampleInput(visitor);
		genExampleClassfile("MethodExampleTest", visitor);
		runSoot("MethodExampleTest");
		break;
	    default:
		System.out.println("Test in config file does not exist: "+ testsToRun[i]);
		break;
	    }
	}
	/*	AnnotatedAnnotatedClassTest.genExampleInput(visitor);
	runSoot("AnnotatedAnnotatedClassTest");
	
	AnnotatedAnnotationTest.genExampleInput(visitor);
	runSoot("AnnotatedAnnotationTest");
	
	AnnotatedClassTest.genExampleInput(visitor);
	runSoot("AnnotatedClassTest");

	AnnotatedFieldTest.genExampleInput(visitor);
	runSoot("AnnotatedFieldTest");
	
	AnnotatedMethodTest.genExampleInput(visitor);
	runSoot("AnnotatedMethodTest");
	
	AnnotatedParameterTest.genExampleInput(visitor);
	runSoot("AnnotatedParameterTest");
	
	AnnotationTest.genExampleInput(visitor);
	runSoot("AnnotationTest");
	
	ArithmeticTest.genExampleInput(visitor);
	runSoot("ArithmeticTest");
	
	ArraysTest.genExampleInput(visitor);
	runSoot("ArraysTest");

	CompareArithmeticInstructions2Test.genExampleInput(visitor);
	runSoot("CompareArithmeticInstructions2Test");

	CompareArithmeticInstructionsTest.genExampleInput(visitor);
	runSoot("CompareArithmeticInstructionsTest");

	CompareInstructionsTest.genExampleInput(visitor);
	runSoot("CompareInstructionsTest");

	ConstantPoolTest.genExampleInput(visitor);
	runSoot("ConstantPoolTest");
	
	ControlStructuresTest.genExampleInput(visitor);
	runSoot("ControlStructuresTest");

	DupsTest.genExampleInput(visitor);
	runSoot("DupsTest");

	EnumTest.genExampleInput(visitor);
	runSoot("EnumTest");

	ExceptionTest.genExampleInput(visitor);
	runSoot("ExceptionTest");

	ExtendedArithmeticLibTest.genExampleInput(visitor);
	runSoot("ExtendedArithmeticLibTest");

	InnerClass2Test.genExampleInput(visitor);
	runSoot("InnerClass2Test");

	InnerClassTest.genExampleInput(visitor);
	runSoot("InnerClassTest");

	InstanceOfCastsTest.genExampleInput(visitor);
	runSoot("InstanceOfCastsTest");
	
	InterfaceTest.genExampleInput(visitor);
	runSoot("InterfaceTest");
	
	LineNumbersTest.genExampleInput(visitor);
	runSoot("LineNumbersTest");
	
	LogicalOperationsTest.genExampleInput(visitor);
	runSoot("LogicalOperationsTest");

	ModifiersTest.genExampleInput(visitor);
	runSoot("ModifiersTest");
	
	MonitorTest.genExampleInput(visitor);
	runSoot("MonitorTest");
	
	NullTypesTest.genExampleInput(visitor);
	runSoot("NullTypesTest");
	
	OuterClassTest.genExampleInput(visitor);
	runSoot("OuterClassTest");
	
	ReturnsTest.genExampleInput(visitor);
	runSoot("ReturnsTest");
	
	StoresTest.genExampleInput(visitor);
	runSoot("StoresTest");
	
	TryCatchTest.genExampleInput(visitor);
	runSoot("TryCatchTest");
	
	ASMBackendMockingTest.genExampleInput(visitor);
	runSoot("ASMBackendMockingTest");
	
	SootASMClassWriterTest.genExampleInput(visitor);
	runSoot("SootASMClassWriterTest");
	
	MinimalJavaVersionTest.genExampleInput(visitor);
	runSoot("MinimalJavaVersionTest");
	*/
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
