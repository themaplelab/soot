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
 * Test for array stores, loads and creation
 *
 * @author Tobias Hamann, Florian Kuebler, Dominik Helm, Lukas Sommer, Kristen Newbury
 *
 */
public class ArraysTest  extends AbstractCacheSrcTest{

	
	 public static void genExampleInput(ClassWriter cw) {
		MethodVisitor mv;


		cw.visit(V1_1, ACC_PUBLIC + ACC_SUPER, "ArraysTestGenerated", null,
				"java/lang/Object", null);
		cw.visitSource("Arrays.java", null);

		{
			mv = cw.visitMethod(ACC_PUBLIC, "<init>", "()V", null, null);
			mv.visitCode();
			mv.visitVarInsn(ALOAD, 0);
			mv.visitMethodInsn(INVOKESPECIAL, "java/lang/Object", "<init>",
					"()V", false);
			mv.visitInsn(RETURN);
			mv.visitMaxs(0, 0);
			mv.visitEnd();
		}
		{
			mv = cw.visitMethod(0, "doBool", "()V", null, null);
			mv.visitCode();
			mv.visitInsn(ICONST_3);

			mv.visitInsn(DUP);
			mv.visitInsn(ICONST_1);
			mv.visitInsn(BALOAD);
			mv.visitVarInsn(ISTORE, 0);
			mv.visitInsn(ICONST_0);
			mv.visitVarInsn(ILOAD, 0);
			mv.visitInsn(BASTORE);
			mv.visitInsn(RETURN);
			mv.visitMaxs(0, 0);
			mv.visitEnd();
		}
		{
			mv = cw.visitMethod(0, "doByte", "()V", null, null);
			mv.visitCode();
			mv.visitInsn(ICONST_4);

			mv.visitInsn(DUP);
			mv.visitInsn(ICONST_1);
			mv.visitInsn(BALOAD);
			mv.visitVarInsn(ISTORE, 0);
			mv.visitInsn(ICONST_0);
			mv.visitVarInsn(ILOAD, 0);
			mv.visitInsn(BASTORE);
			mv.visitInsn(RETURN);
			mv.visitMaxs(0, 0);
			mv.visitEnd();
		}
		{
			mv = cw.visitMethod(0, "doChar", "()V", null, null);
			mv.visitCode();
			mv.visitInsn(ICONST_5);

			mv.visitInsn(DUP);
			mv.visitInsn(ICONST_1);
			mv.visitInsn(CALOAD);
			mv.visitVarInsn(ISTORE, 0);
			mv.visitInsn(ICONST_0);
			mv.visitVarInsn(ILOAD, 0);
			mv.visitInsn(CASTORE);
			mv.visitInsn(RETURN);
			mv.visitMaxs(0, 0);
			mv.visitEnd();
		}
		{
			mv = cw.visitMethod(0, "doDouble", "()V", null, null);
			mv.visitCode();
			mv.visitIntInsn(BIPUSH, 6);

			mv.visitInsn(DUP);
			mv.visitInsn(ICONST_1);
			mv.visitInsn(DALOAD);
			mv.visitVarInsn(DSTORE, 1);
			mv.visitInsn(ICONST_0);
			mv.visitVarInsn(DLOAD, 1);
			mv.visitInsn(DASTORE);
			mv.visitInsn(RETURN);
			mv.visitMaxs(0, 0);
			mv.visitEnd();
		}
		{
			mv = cw.visitMethod(0, "doFloat", "()V", null, null);
			mv.visitCode();
			mv.visitIntInsn(BIPUSH, 7);

			mv.visitInsn(DUP);
			mv.visitInsn(ICONST_1);
			mv.visitInsn(FALOAD);
			mv.visitVarInsn(FSTORE, 0);
			mv.visitInsn(ICONST_0);
			mv.visitVarInsn(FLOAD, 0);
			mv.visitInsn(FASTORE);
			mv.visitInsn(RETURN);
			mv.visitMaxs(0, 0);
			mv.visitEnd();
		}
		{
			mv = cw.visitMethod(0, "doInt", "()V", null, null);
			mv.visitCode();
			mv.visitIntInsn(BIPUSH, 8);

			mv.visitInsn(DUP);
			mv.visitInsn(ICONST_1);
			mv.visitInsn(IALOAD);
			mv.visitVarInsn(ISTORE, 0);
			mv.visitInsn(ICONST_0);
			mv.visitVarInsn(ILOAD, 0);
			mv.visitInsn(IASTORE);
			mv.visitInsn(RETURN);
			mv.visitMaxs(0, 0);
			mv.visitEnd();
		}
		{
			mv = cw.visitMethod(0, "doIntInt", "()V", null, null);
			mv.visitCode();
			mv.visitInsn(ICONST_3);
			mv.visitInsn(ICONST_3);
			mv.visitMultiANewArrayInsn("[[I", 2);
			mv.visitVarInsn(ASTORE, 0);
			mv.visitVarInsn(ALOAD, 0);
			mv.visitInsn(ICONST_0);
			mv.visitVarInsn(ALOAD, 0);
			mv.visitInsn(ICONST_1);
			mv.visitInsn(AALOAD);
			mv.visitInsn(AASTORE);
			mv.visitVarInsn(ALOAD, 0);
			mv.visitInsn(ICONST_1);
			mv.visitInsn(AALOAD);
			mv.visitInsn(ICONST_2);
			mv.visitVarInsn(ALOAD, 0);
			mv.visitInsn(ICONST_2);
			mv.visitInsn(AALOAD);
			mv.visitInsn(ICONST_1);
			mv.visitInsn(IALOAD);
			mv.visitInsn(IASTORE);
			mv.visitInsn(RETURN);
			mv.visitMaxs(0, 0);
			mv.visitEnd();
		}
		{
			mv = cw.visitMethod(0, "doLong", "()V", null, null);
			mv.visitCode();
			mv.visitIntInsn(BIPUSH, 9);

			mv.visitInsn(DUP);
			mv.visitInsn(ICONST_1);

			mv.visitVarInsn(LSTORE, 1);
			mv.visitInsn(ICONST_0);
			mv.visitVarInsn(LLOAD, 1);
			mv.visitInsn(LASTORE);
			mv.visitInsn(RETURN);
			mv.visitMaxs(0, 0);
			mv.visitEnd();
		}
		{
			mv = cw.visitMethod(0, "doObject", "()V", null, null);
			mv.visitCode();
			mv.visitIntInsn(BIPUSH, 11);
			mv.visitTypeInsn(ANEWARRAY, "java/lang/Object");
			mv.visitVarInsn(ASTORE, 0);
			mv.visitVarInsn(ALOAD, 0);
			mv.visitInsn(ICONST_0);
			mv.visitVarInsn(ALOAD, 0);
			mv.visitInsn(ICONST_1);
			mv.visitInsn(AALOAD);
			mv.visitInsn(AASTORE);
			mv.visitVarInsn(ALOAD, 0);
			mv.visitInsn(ICONST_3);
			mv.visitInsn(ACONST_NULL);
			mv.visitInsn(AASTORE);
			mv.visitInsn(RETURN);
			mv.visitMaxs(0, 0);
			mv.visitEnd();
		}
		{
			mv = cw.visitMethod(0, "doObjectObject", "()V", null, null);
			mv.visitCode();
			mv.visitInsn(ICONST_4);
			mv.visitInsn(ICONST_4);
			mv.visitMultiANewArrayInsn("[[Ljava/lang/Object;", 2);
			mv.visitVarInsn(ASTORE, 0);
			mv.visitVarInsn(ALOAD, 0);
			mv.visitInsn(ICONST_0);
			mv.visitVarInsn(ALOAD, 0);
			mv.visitInsn(ICONST_1);
			mv.visitInsn(AALOAD);
			mv.visitInsn(AASTORE);
			mv.visitVarInsn(ALOAD, 0);
			mv.visitInsn(ICONST_1);
			mv.visitInsn(AALOAD);
			mv.visitInsn(ICONST_2);
			mv.visitVarInsn(ALOAD, 0);
			mv.visitInsn(ICONST_2);
			mv.visitInsn(AALOAD);
			mv.visitInsn(ICONST_1);
			mv.visitInsn(AALOAD);
			mv.visitInsn(AASTORE);
			mv.visitInsn(RETURN);
			mv.visitMaxs(0, 0);
			mv.visitEnd();
		}
		{
			mv = cw.visitMethod(0, "doShort", "()V", null, null);
			mv.visitCode();
			mv.visitIntInsn(BIPUSH, 10);

			mv.visitInsn(DUP);
			mv.visitInsn(ICONST_1);
			mv.visitInsn(SALOAD);
			mv.visitVarInsn(ISTORE, 0);
			mv.visitInsn(ICONST_0);
			mv.visitVarInsn(ILOAD, 0);
			mv.visitInsn(SASTORE);
			mv.visitInsn(RETURN);
			mv.visitMaxs(0, 0);
			mv.visitEnd();
		}
		{
			mv = cw.visitMethod(0, "doString", "()V", null, null);
			mv.visitCode();
			mv.visitIntInsn(BIPUSH, 12);
			mv.visitTypeInsn(ANEWARRAY, "java/lang/String");
			mv.visitInsn(DUP);
			mv.visitInsn(ICONST_1);
			mv.visitInsn(AALOAD);
			mv.visitVarInsn(ASTORE, 0);
			mv.visitInsn(ICONST_0);
			mv.visitVarInsn(ALOAD, 0);
			mv.visitInsn(AASTORE);
			mv.visitInsn(RETURN);
			mv.visitMaxs(0, 0);
			mv.visitEnd();
		}
		cw.visitEnd();

	}

	
	protected String getTargetClass() {
		return "soot.asm.tests.targets.Arrays";
	}

}
