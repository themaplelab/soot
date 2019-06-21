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

import org.objectweb.asm.AnnotationVisitor;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.ClassWriter;

/**
 * Test for an annotation class
 *
 * @author Tobias Hamann, Florian Kuebler, Dominik Helm, Lukas Sommer, Kristen Newbury
 *
 */
public class AnnotationTest  extends AbstractCacheSrcTest{

	
	 public static void genExampleInput(ClassWriter cw) {
		MethodVisitor mv;
		AnnotationVisitor av0;

		cw.visit(V1_5, ACC_PUBLIC + ACC_ANNOTATION + ACC_ABSTRACT
				+ ACC_INTERFACE, "MyTestAnnotationGenerated", null,
				"java/lang/Object",
				new String[] { "java/lang/annotation/Annotation" });
		
		cw.visitSource("MyTestAnnotation.java", null);

		{
		av0 = cw.visitAnnotation("Ljava/lang/annotation/Retention;", true);
		av0.visitEnum("value", "Ljava/lang/annotation/RetentionPolicy;", "RUNTIME");
		av0.visitEnd();
		}
		{
		mv = cw.visitMethod(ACC_PUBLIC + ACC_ABSTRACT, "bVal", "()B", null, null);
		mv.visitEnd();
		}
		{
		mv = cw.visitMethod(ACC_PUBLIC + ACC_ABSTRACT, "dVal", "()D", null, null);
		mv.visitEnd();
		}
		{
		mv = cw.visitMethod(ACC_PUBLIC + ACC_ABSTRACT, "fVal", "()F", null, null);
		mv.visitEnd();
		}
		{
			mv = cw.visitMethod(ACC_PUBLIC + ACC_ABSTRACT, "iAVal", "()[I", null, null);
			mv.visitEnd();
		}
		{
		mv = cw.visitMethod(ACC_PUBLIC + ACC_ABSTRACT, "iVal", "()I", null, null);
		mv.visitEnd();
		}
		{
		mv = cw.visitMethod(ACC_PUBLIC + ACC_ABSTRACT, "lVal", "()J", null, null);
		mv.visitEnd();
		}
		{
		mv = cw.visitMethod(ACC_PUBLIC + ACC_ABSTRACT, "rVal", "()Ljava/lang/Class;",
				"()Ljava/lang/Class<Lsoot/asm/tests/targets/AnnotatedClass;>;", null);
		mv.visitEnd();
		}
		{
			mv = cw.visitMethod(ACC_PUBLIC + ACC_ABSTRACT, "sAVal", "()[Ljava/lang/String;", null, null);
			mv.visitEnd();
		}
		{
			mv = cw.visitMethod(ACC_PUBLIC + ACC_ABSTRACT, "sVal", "()S", null, null);
			mv.visitEnd();
		}
		{
			mv = cw.visitMethod(ACC_PUBLIC + ACC_ABSTRACT, "strVal", "()Ljava/lang/String;", null, null);
			mv.visitEnd();
		}
		{
		mv = cw.visitMethod(ACC_PUBLIC + ACC_ABSTRACT, "zVal", "()Z", null, null);
		mv.visitEnd();
		}


		cw.visitEnd();

	}

	
	protected String getTargetClass() {
		return "soot.asm.tests.targets.MyTestAnnotation";
	}

}
