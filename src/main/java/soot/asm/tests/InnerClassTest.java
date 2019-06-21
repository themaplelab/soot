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
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.ClassWriter;

/**
 * Test for inner class in class
 *
 * @author Tobias Hamann, Florian Kuebler, Dominik Helm, Lukas Sommer, Kristen Newbury
 *
 */
public class InnerClassTest  extends AbstractCacheSrcTest{

	
public static void genExampleInput(ClassWriter visitor){
    MethodVisitor mv;
		FieldVisitor fv;

		visitor.visit(V1_1, ACC_SUPER, "InnerClass$Inner", null,
				"java/lang/Object", null);
		
		visitor.visitSource("InnerClass.java", null);

		visitor.visitInnerClass("soot/asm/tests/targets/InnerClass$Inner", "soot/asm/tests/targets/InnerClass",
				"Inner", ACC_PRIVATE);

		{

		    fv = visitor.visitField(ACC_FINAL + ACC_STATIC, "a", "I", null,
					new Integer(3));
			fv.visitEnd();
		}
		{
			fv = visitor.visitField(ACC_FINAL + ACC_SYNTHETIC, "this$0",
					"Lsoot/asm/tests/targets/InnerClass;", null, null);
			fv.visitEnd();
		}
		{
			mv = visitor.visitMethod(ACC_PRIVATE, "<init>",
					"(Lsoot/asm/tests/targets/InnerClass;)V", null, null);
			mv.visitCode();
			mv.visitVarInsn(ALOAD, 0);
			mv.visitVarInsn(ALOAD, 1);
			mv.visitFieldInsn(PUTFIELD, "soot/asm/tests/targets/InnerClass$Inner", "this$0",
					"Lsoot/asm/tests/targets/InnerClass;");
			mv.visitVarInsn(ALOAD, 0);
			mv.visitMethodInsn(INVOKESPECIAL, "java/lang/Object", "<init>",
					"()V", false);
			mv.visitInsn(RETURN);
			mv.visitMaxs(0, 0);
			mv.visitEnd();
		}
		visitor.visitEnd();

	}

	
	protected String getTargetClass() {
		return "soot.asm.tests.targets.InnerClass$Inner";
	}

}
