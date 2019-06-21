package soot.asm.tests;

/*-
 * #%L
 * Soot - a J*va Optimization Framework
 * %%
 * Copyright (C) 1997 - 2018 
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

import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.ClassWriter;

/**
 * Test for annotation class that contains an annotation
 *
 * @author Tobias Hamann, Florian Kuebler, Dominik Helm, Lukas Sommer, Kristen Newbury
 *
 */
public class AnnotatedAnnotationTest extends AbstractCacheSrcTest {

	
	 public static void genExampleInput(ClassWriter cw) {

		MethodVisitor mv;


		cw.visit(V1_5, ACC_PUBLIC + ACC_ANNOTATION + ACC_ABSTRACT + ACC_INTERFACE,
				"MyAnnotatedAnnotationGenerated", null,
				"java/lang/Object", new String[] { "java/lang/annotation/Annotation" }); //TODO V1_1 seems wrong here
		cw.visitSource("MyAnnotatedAnnotation.java", null);
		
		{
		    mv = cw.visitMethod(ACC_PUBLIC + ACC_ABSTRACT, "value",
				"()Lsoot/asm/tests/targets/MyTestAnnotation;", null, null);
		    mv.visitEnd();
		}
		cw.visitEnd();

	}

	
	protected String getTargetClass() {
		return "soot.asm.tests.targets.MyAnnotatedAnnotation";
	}

}
