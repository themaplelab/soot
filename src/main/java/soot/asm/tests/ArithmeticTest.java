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
 * Test for several arithmetic bytecode instructions
 *
 * @author Tobias Hamann, Florian Kuebler, Dominik Helm, Lukas Sommer, Kristen Newbury
 *
 */
public class ArithmeticTest extends AbstractCacheSrcTest {

	
	 public static void genExampleInput(ClassWriter cw) {

		FieldVisitor fv;
		MethodVisitor mv;

		cw.visit(V1_2, ACC_PUBLIC + ACC_SUPER, "ArithmeticLibGenerated", null,
				"java/lang/Object", null);
		
		cw.visitSource("ArithmeticLib.java", null);

		{
		fv = cw.visitField(ACC_PRIVATE, "rInt", "I", null, null);
		fv.visitEnd();
		}
		{
		fv = cw.visitField(ACC_PRIVATE, "rFloat", "F", null, null);
		fv.visitEnd();
		}
		{
		fv = cw.visitField(ACC_PRIVATE, "rLong", "J", null, null);
		fv.visitEnd();
		}
		{
		fv = cw.visitField(ACC_PRIVATE, "rDouble", "D", null, null);
		fv.visitEnd();
		}
		{
		fv = cw.visitField(ACC_PRIVATE, "rShort", "S", null, null);
		fv.visitEnd();
		}
		{
		fv = cw.visitField(ACC_PRIVATE, "rChar", "C", null, null);
		fv.visitEnd();
		}
		{
		fv = cw.visitField(ACC_PRIVATE, "rByte", "B", null, null);
		fv.visitEnd();
		}
		{
		fv = cw.visitField(ACC_FINAL, "cInt", "I", null, new Integer(1));
		fv.visitEnd();
		}
		{
		fv = cw.visitField(ACC_FINAL, "cFloat", "F", null, new Float("1.0"));
		fv.visitEnd();
		}
		{
		fv = cw.visitField(ACC_FINAL, "cLong", "J", null, new Long(1L));
		fv.visitEnd();
		}
		{
		fv = cw.visitField(ACC_FINAL, "cDouble", "D", null, new Double("1.0"));
		fv.visitEnd();
		}
		{
		mv = cw.visitMethod(ACC_PUBLIC, "<init>", "()V", null, null);
		mv.visitCode();
		mv.visitVarInsn(ALOAD, 0);
		mv.visitMethodInsn(INVOKESPECIAL, "java/lang/Object", "<init>", "()V", false);
		mv.visitVarInsn(ALOAD, 0);
		mv.visitInsn(ICONST_1);
		mv.visitFieldInsn(PUTFIELD, "ArithmeticLibGenerated", "cInt", "I");
		mv.visitVarInsn(ALOAD, 0);
		mv.visitInsn(FCONST_1);
		mv.visitFieldInsn(PUTFIELD, "ArithmeticLibGenerated", "cFloat", "F");
		mv.visitVarInsn(ALOAD, 0);
		mv.visitInsn(LCONST_1);
		mv.visitFieldInsn(PUTFIELD, "ArithmeticLibGenerated", "cLong", "J");
		mv.visitVarInsn(ALOAD, 0);
		mv.visitInsn(DCONST_1);
		mv.visitFieldInsn(PUTFIELD, "ArithmeticLibGenerated", "cDouble", "D");
		mv.visitInsn(RETURN);
		mv.visitMaxs(0, 0);
		mv.visitEnd();
		}
		
		{
		mv = cw.visitMethod(ACC_PUBLIC, "castInt2Byte", "()B", null, null);
		mv.visitCode();
		mv.visitVarInsn(ALOAD, 0);
		mv.visitVarInsn(ALOAD, 0);
		mv.visitFieldInsn(GETFIELD, "ArithmeticLibGenerated", "rInt", "I");
		mv.visitInsn(I2B);
		mv.visitFieldInsn(PUTFIELD, "ArithmeticLibGenerated", "rByte", "B");
		mv.visitVarInsn(ALOAD, 0);
		mv.visitFieldInsn(GETFIELD, "ArithmeticLibGenerated", "rByte", "B");
		mv.visitInsn(IRETURN);
		mv.visitMaxs(0, 0);
		mv.visitEnd();
		}
		{
		mv = cw.visitMethod(ACC_PUBLIC, "castInt2Char", "()C", null, null);
		mv.visitCode();
		mv.visitVarInsn(ALOAD, 0);
		mv.visitVarInsn(ALOAD, 0);
		mv.visitFieldInsn(GETFIELD, "ArithmeticLibGenerated", "rInt", "I");
		mv.visitInsn(I2C);
		mv.visitFieldInsn(PUTFIELD, "ArithmeticLibGenerated", "rChar", "C");
		mv.visitVarInsn(ALOAD, 0);
		mv.visitFieldInsn(GETFIELD, "ArithmeticLibGenerated", "rChar", "C");
		mv.visitInsn(IRETURN);
		mv.visitMaxs(0, 0);
		mv.visitEnd();
		}
		{
		mv = cw.visitMethod(ACC_PUBLIC, "castInt2Short", "()S", null, null);
		mv.visitCode();
		mv.visitVarInsn(ALOAD, 0);
		mv.visitVarInsn(ALOAD, 0);
		mv.visitFieldInsn(GETFIELD, "ArithmeticLibGenerated", "rInt", "I");
		mv.visitInsn(I2S);
		mv.visitFieldInsn(PUTFIELD, "ArithmeticLibGenerated", "rShort", "S");
		mv.visitVarInsn(ALOAD, 0);
		mv.visitFieldInsn(GETFIELD, "ArithmeticLibGenerated", "rShort", "S");
		mv.visitInsn(IRETURN);
		mv.visitMaxs(0, 0);
		mv.visitEnd();
		}

		{
		mv = cw.visitMethod(ACC_PUBLIC, "doCompDouble", "(D)D", null, null);
		mv.visitCode();
		mv.visitVarInsn(ALOAD, 0);
		mv.visitVarInsn(DLOAD, 1);
		mv.visitInsn(DCONST_1);
		mv.visitInsn(DDIV);
		mv.visitInsn(D2I);
		mv.visitFieldInsn(PUTFIELD, "ArithmeticLibGenerated", "rInt", "I");
		mv.visitVarInsn(ALOAD, 0);
		mv.visitVarInsn(DLOAD, 1);
		mv.visitLdcInsn(new Double("6.0"));
		mv.visitInsn(DMUL);
		mv.visitInsn(D2L);
		mv.visitFieldInsn(PUTFIELD, "ArithmeticLibGenerated", "rLong", "J");
		mv.visitVarInsn(ALOAD, 0);
		mv.visitVarInsn(DLOAD, 1);
		mv.visitInsn(DCONST_0);
		mv.visitInsn(DADD);
		mv.visitInsn(D2F);
		mv.visitFieldInsn(PUTFIELD, "ArithmeticLibGenerated", "rFloat", "F");
		mv.visitVarInsn(ALOAD, 0);
		mv.visitVarInsn(DLOAD, 1);
		mv.visitLdcInsn(new Double("4.0"));
		mv.visitInsn(DSUB);
		mv.visitFieldInsn(PUTFIELD, "ArithmeticLibGenerated", "rDouble", "D");
		mv.visitVarInsn(ALOAD, 0);
		mv.visitFieldInsn(GETFIELD, "ArithmeticLibGenerated", "rDouble", "D");
		mv.visitInsn(DRETURN);
		mv.visitMaxs(0, 0);
		mv.visitEnd();
		}
		{
		mv = cw.visitMethod(ACC_PUBLIC, "doCompFloat", "(F)F", null, null);
		mv.visitCode();
		mv.visitVarInsn(ALOAD, 0);
		mv.visitVarInsn(FLOAD, 1);
		mv.visitLdcInsn(new Float("13.0"));
		mv.visitInsn(FDIV);
		mv.visitInsn(F2I);
		mv.visitFieldInsn(PUTFIELD, "ArithmeticLibGenerated", "rInt", "I");
		mv.visitVarInsn(ALOAD, 0);
		mv.visitVarInsn(FLOAD, 1);
		mv.visitLdcInsn(new Float("3.0"));
		mv.visitInsn(FMUL);
		mv.visitFieldInsn(PUTFIELD, "ArithmeticLibGenerated", "rFloat", "F");
		mv.visitVarInsn(ALOAD, 0);
		mv.visitVarInsn(FLOAD, 1);
		mv.visitInsn(FCONST_2);
		mv.visitInsn(FSUB);
		mv.visitInsn(F2L);
		mv.visitFieldInsn(PUTFIELD, "ArithmeticLibGenerated", "rLong", "J");
		mv.visitVarInsn(ALOAD, 0);
		mv.visitVarInsn(FLOAD, 1);
		mv.visitInsn(FCONST_1);
		mv.visitInsn(FADD);
		mv.visitInsn(F2D);
		mv.visitFieldInsn(PUTFIELD, "ArithmeticLibGenerated", "rDouble", "D");
		mv.visitVarInsn(ALOAD, 0);
		mv.visitFieldInsn(GETFIELD, "ArithmeticLibGenerated", "rFloat", "F");
		mv.visitInsn(FRETURN);
		mv.visitMaxs(0, 0);
		mv.visitEnd();
		}
		{
		mv = cw.visitMethod(ACC_PUBLIC, "doCompInt", "(I)I", null, null);
		mv.visitCode();
		mv.visitVarInsn(ALOAD, 0);
		mv.visitVarInsn(ILOAD, 1);
		mv.visitInsn(ICONST_M1);
		mv.visitInsn(IDIV);
		mv.visitFieldInsn(PUTFIELD, "ArithmeticLibGenerated", "rInt", "I");
		mv.visitVarInsn(ALOAD, 0);
		mv.visitVarInsn(ILOAD, 1);
		mv.visitIntInsn(BIPUSH, 17);
		mv.visitInsn(IMUL);
		mv.visitInsn(I2F);
		mv.visitFieldInsn(PUTFIELD, "ArithmeticLibGenerated", "rFloat", "F");
		mv.visitVarInsn(ALOAD, 0);
		mv.visitVarInsn(ILOAD, 1);
		mv.visitInsn(ICONST_5);
		mv.visitInsn(IADD);
		mv.visitInsn(I2L);
		mv.visitFieldInsn(PUTFIELD, "ArithmeticLibGenerated", "rLong", "J");
		mv.visitVarInsn(ALOAD, 0);
		mv.visitVarInsn(ILOAD, 1);
		mv.visitInsn(ICONST_2);
		mv.visitInsn(ISUB);
		mv.visitInsn(I2D);
		mv.visitFieldInsn(PUTFIELD, "ArithmeticLibGenerated", "rDouble", "D");
		mv.visitVarInsn(ALOAD, 0);
		mv.visitFieldInsn(GETFIELD, "ArithmeticLibGenerated", "rInt", "I");
		mv.visitInsn(IRETURN);
		mv.visitMaxs(0, 0);
		mv.visitEnd();
		}
		{
		mv = cw.visitMethod(ACC_PUBLIC, "doCompLong", "(J)J", null, null);
		mv.visitCode();
		mv.visitVarInsn(ALOAD, 0);
		mv.visitVarInsn(LLOAD, 1);
		mv.visitLdcInsn(new Long(5L));
		mv.visitInsn(LMUL);
		mv.visitInsn(L2I);
		mv.visitFieldInsn(PUTFIELD, "ArithmeticLibGenerated", "rInt", "I");
		mv.visitVarInsn(ALOAD, 0);
		mv.visitVarInsn(LLOAD, 1);
		mv.visitLdcInsn(new Long(2L));
		mv.visitInsn(LADD);
		mv.visitInsn(L2F);
		mv.visitFieldInsn(PUTFIELD, "ArithmeticLibGenerated", "rFloat", "F");
		mv.visitVarInsn(ALOAD, 0);
		mv.visitVarInsn(LLOAD, 1);
		mv.visitLdcInsn(new Long(6L));
		mv.visitInsn(LMUL);
		mv.visitFieldInsn(PUTFIELD, "ArithmeticLibGenerated", "rLong", "J");
		mv.visitVarInsn(ALOAD, 0);
		mv.visitVarInsn(LLOAD, 1);
		mv.visitLdcInsn(new Long(6L));
		mv.visitInsn(LDIV);
		mv.visitInsn(L2D);
		mv.visitFieldInsn(PUTFIELD, "ArithmeticLibGenerated", "rDouble", "D");
		mv.visitVarInsn(ALOAD, 0);
		mv.visitFieldInsn(GETFIELD, "ArithmeticLibGenerated", "rLong", "J");
		mv.visitInsn(LRETURN);
		mv.visitMaxs(0, 0);
		mv.visitEnd();
		}
		cw.visitEnd();
	}

	
	protected String getTargetClass() {
		return "soot.asm.tests.targets.ArithmeticLib";
	}

	
	protected String getRequiredJavaVersion(){
		return "1.2";
	}
}
