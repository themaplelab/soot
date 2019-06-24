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

import org.objectweb.asm.FieldVisitor;
import org.objectweb.asm.Label;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.ClassWriter;

/**
 * Test for methods taken from the ASM 4.0 guide
 *
 * @author Kristen Newbury
 *
 */
public class LDCTest extends AbstractCacheSrcTest{

 	public static void genExampleInput(ClassWriter visitor) {
		FieldVisitor fv;
		MethodVisitor mv;

		visitor.visit(Opcodes.V1_1, Opcodes.ACC_PUBLIC + Opcodes.ACC_SUPER,
			      "LDCTestGenerated", null, "java/lang/Object", null);
		visitor.visitSource("LDCTest.java", null);
		//only need one method, one with each asm opcode recognizable op (no test JBldc2lw, JBldc2dw)
		{
		    mv = visitor.visitMethod(Opcodes.ACC_PUBLIC, "<init>", "()V", null, null);
		    mv.visitCode();
		    //int const test
		    mv.visitLdcInsn(190);
		    // const str test                                                                                  
                    mv.visitLdcInsn("javax/crypto/Cipher");
		    mv.visitInsn(Opcodes.RETURN);
		    mv.visitMaxs(0, 0);
		    mv.visitEnd();
		}
		visitor.visitEnd();

	}

	protected String getTargetClass() {
		return "soot.asm.tests.targets.LDC";
	}

}
