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
 * Test for method declaring exception
 *
 * @author Tobias Hamann, Florian Kuebler, Dominik Helm, Lukas Sommer, Kristen Newbury
 *
 */
public class ExceptionTest  extends AbstractCacheSrcTest{

	
public static void genExampleInput(ClassWriter cw){
    MethodVisitor mv;

		cw.visit(V1_4, ACC_PUBLIC + ACC_ABSTRACT + ACC_INTERFACE,
				"ExceptionMethodsGenerated", null,
				"java/lang/Object", null);
		
		cw.visitSource("ExceptionMethods.java", null);

		{
		mv = cw.visitMethod(ACC_PUBLIC + ACC_ABSTRACT, "foo", "()V", null, new String[] { "java/lang/NullPointerException" });
		mv.visitEnd();
		}

		cw.visitEnd();

	}

	
	protected String getTargetClass() {
		return "soot.asm.tests.targets.ExceptionMethods";
	}

	
	protected String getRequiredJavaVersion() {
		return "1.4";
	}

}
