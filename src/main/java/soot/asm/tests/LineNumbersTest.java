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

import org.objectweb.asm.Label;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.ClassWriter;

/**
 * Test for line number labels
 *
 * @author Tobias Hamann, Florian Kuebler, Dominik Helm, Lukas Sommer, Kristen Newbury
 *
 */
public class LineNumbersTest extends AbstractCacheSrcTest{

	
public static void genExampleInput(ClassWriter cw){
    MethodVisitor mv;

    cw.visit(V1_1, ACC_PUBLIC + ACC_SUPER, "LineNumbersGenerated", null, "java/lang/Object", null);
		cw.visitSource("LineNumbers.java", null);

		{
			mv = cw.visitMethod(ACC_PUBLIC, "<init>", "()V", null, null);
			mv.visitCode();
			Label l1 = new Label();
			mv.visitLabel(l1);
			mv.visitLineNumber(25, l1);
			mv.visitVarInsn(ALOAD, 0);
			Label l2 = new Label();
			mv.visitLabel(l2);
			mv.visitLineNumber(25, l2);
			mv.visitMethodInsn(INVOKESPECIAL, "java/lang/Object", "<init>", "()V", false);
			Label l3 = new Label();
			mv.visitLabel(l3);
			mv.visitLineNumber(25, l3);
			mv.visitInsn(RETURN);
			mv.visitMaxs(0, 0);
			mv.visitEnd();
		}
		{

			mv.visitCode();
			Label l0 = new Label();
			mv.visitLabel(l0);
			mv.visitLineNumber(28, l0);
			mv.visitInsn(RETURN);
			mv.visitMaxs(0, 0);
			mv.visitEnd();
		}
		cw.visitEnd();

	}

	
	protected String getTargetClass() {
		return "soot.asm.tests.targets.LineNumbers";
	}

}