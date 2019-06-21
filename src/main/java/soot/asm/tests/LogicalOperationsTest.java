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
 * Test for bitwise logical operation bytecode instructions
 *
 * @author Tobias Hamann, Florian Kuebler, Dominik Helm, Lukas Sommer, Kristen Newbury
 *
 */
public class LogicalOperationsTest  extends AbstractCacheSrcTest{

	
    public static void genExampleInput(ClassWriter cw){
	MethodVisitor mv;
		FieldVisitor fv;

		cw.visit(V1_1, ACC_PUBLIC + ACC_SUPER, "LogicalOperationsGenerated", null,
				"java/lang/Object", null);
		cw.visitSource("LogicalOperations.java", null);
		{
			fv = cw.visitField(ACC_PRIVATE, "i1", "I", null, null);
			fv.visitEnd();
		}
		{
			fv = cw.visitField(ACC_PRIVATE, "b1", "Z", null, null);
			fv.visitEnd();
		}
		{
			fv = cw.visitField(ACC_PRIVATE, "l1", "J", null, null);
			fv.visitEnd();
		}
		{
			fv = cw.visitField(ACC_PRIVATE, "i2", "I", null, null);
			fv.visitEnd();
		}
		{
			fv = cw.visitField(ACC_PRIVATE, "b2", "Z", null, null);
			fv.visitEnd();
		}
		{
			fv = cw.visitField(ACC_PRIVATE, "l2", "J", null, null);
			fv.visitEnd();
		}
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
			mv = cw.visitMethod(ACC_PUBLIC, "doAnd", "()V", null, null);
			mv.visitCode();
			mv.visitVarInsn(ALOAD, 0);
			mv.visitVarInsn(ALOAD, 0);
			mv.visitFieldInsn(GETFIELD, "LogicalOperationsGenerated", "i2", "I");
			mv.visitVarInsn(ALOAD, 0);
			mv.visitFieldInsn(GETFIELD, "LogicalOperationsGenerated", "i1", "I");
			mv.visitInsn(IAND);
			mv.visitFieldInsn(PUTFIELD, "LogicalOperationsGenerated", "i1", "I");
			mv.visitVarInsn(ALOAD, 0);
			mv.visitVarInsn(ALOAD, 0);
			mv.visitFieldInsn(GETFIELD, "LogicalOperationsGenerated", "l2", "J");
			mv.visitVarInsn(ALOAD, 0);
			mv.visitFieldInsn(GETFIELD, "LogicalOperationsGenerated", "l1", "J");
			mv.visitInsn(LAND);
			mv.visitFieldInsn(PUTFIELD, "LogicalOperationsGenerated", "l1", "J");
			mv.visitVarInsn(ALOAD, 0);
			mv.visitVarInsn(ALOAD, 0);
			mv.visitFieldInsn(GETFIELD, "LogicalOperationsGenerated", "b2", "Z");
			mv.visitVarInsn(ALOAD, 0);
			mv.visitFieldInsn(GETFIELD, "LogicalOperationsGenerated", "b1", "Z");
			mv.visitInsn(IAND);
			mv.visitFieldInsn(PUTFIELD, "LogicalOperationsGenerated", "b1", "Z");
			mv.visitInsn(RETURN);
			mv.visitMaxs(0, 0);
			mv.visitEnd();
		}
		{
			mv = cw.visitMethod(ACC_PUBLIC, "doInv", "()V", null, null);
			mv.visitCode();
			mv.visitVarInsn(ALOAD, 0);
			mv.visitVarInsn(ALOAD, 0);
			mv.visitFieldInsn(GETFIELD, "LogicalOperationsGenerated", "i2", "I");
			mv.visitInsn(ICONST_M1);
			mv.visitInsn(IXOR);
			mv.visitFieldInsn(PUTFIELD, "LogicalOperationsGenerated", "i1", "I");
			mv.visitVarInsn(ALOAD, 0);
			mv.visitVarInsn(ALOAD, 0);
			mv.visitFieldInsn(GETFIELD, "LogicalOperationsGenerated", "i2", "I");
			mv.visitInsn(ICONST_M1);
			mv.visitInsn(IXOR);
			mv.visitInsn(I2L);
			mv.visitFieldInsn(PUTFIELD, "LogicalOperationsGenerated", "l1", "J");
			mv.visitInsn(RETURN);
			mv.visitMaxs(0, 0);
			mv.visitEnd();
		}
		{
			mv = cw.visitMethod(ACC_PUBLIC, "doOr", "()V", null, null);
			mv.visitCode();
			mv.visitVarInsn(ALOAD, 0);
			mv.visitVarInsn(ALOAD, 0);
			mv.visitFieldInsn(GETFIELD, "LogicalOperationsGenerated", "i2", "I");
			mv.visitVarInsn(ALOAD, 0);
			mv.visitFieldInsn(GETFIELD, "LogicalOperationsGenerated", "i1", "I");
			mv.visitInsn(IOR);
			mv.visitFieldInsn(PUTFIELD, "LogicalOperationsGenerated", "i1", "I");
			mv.visitVarInsn(ALOAD, 0);
			mv.visitVarInsn(ALOAD, 0);
			mv.visitFieldInsn(GETFIELD, "LogicalOperationsGenerated", "l2", "J");
			mv.visitVarInsn(ALOAD, 0);
			mv.visitFieldInsn(GETFIELD, "LogicalOperationsGenerated", "l1", "J");
			mv.visitInsn(LOR);
			mv.visitFieldInsn(PUTFIELD, "LogicalOperationsGenerated", "l1", "J");
			mv.visitVarInsn(ALOAD, 0);
			mv.visitVarInsn(ALOAD, 0);
			mv.visitFieldInsn(GETFIELD, "LogicalOperationsGenerated", "b2", "Z");
			mv.visitVarInsn(ALOAD, 0);
			mv.visitFieldInsn(GETFIELD, "LogicalOperationsGenerated", "b1", "Z");
			mv.visitInsn(IOR);
			mv.visitFieldInsn(PUTFIELD, "LogicalOperationsGenerated", "b1", "Z");
			mv.visitInsn(RETURN);
			mv.visitMaxs(0, 0);
			mv.visitEnd();
		}
		{
			mv = cw.visitMethod(ACC_PUBLIC, "doShl", "()V", null, null);
			mv.visitCode();
			mv.visitVarInsn(ALOAD, 0);
			mv.visitVarInsn(ALOAD, 0);
			mv.visitFieldInsn(GETFIELD, "LogicalOperationsGenerated", "i1", "I");
			mv.visitVarInsn(ALOAD, 0);
			mv.visitFieldInsn(GETFIELD, "LogicalOperationsGenerated", "i2", "I");
			mv.visitInsn(ISHL);
			mv.visitFieldInsn(PUTFIELD, "LogicalOperationsGenerated", "i1", "I");
			mv.visitVarInsn(ALOAD, 0);
			mv.visitVarInsn(ALOAD, 0);
			mv.visitFieldInsn(GETFIELD, "LogicalOperationsGenerated", "l1", "J");
			mv.visitVarInsn(ALOAD, 0);
			mv.visitFieldInsn(GETFIELD, "LogicalOperationsGenerated", "l2", "J");
			mv.visitInsn(L2I);
			mv.visitInsn(LSHL);
			mv.visitFieldInsn(PUTFIELD, "LogicalOperationsGenerated", "l1", "J");
			mv.visitInsn(RETURN);
			mv.visitMaxs(0, 0);
			mv.visitEnd();
		}
		{
			mv = cw.visitMethod(ACC_PUBLIC, "doShr", "()V", null, null);
			mv.visitCode();
			mv.visitVarInsn(ALOAD, 0);
			mv.visitVarInsn(ALOAD, 0);
			mv.visitFieldInsn(GETFIELD, "LogicalOperationsGenerated", "i1", "I");
			mv.visitVarInsn(ALOAD, 0);
			mv.visitFieldInsn(GETFIELD, "LogicalOperationsGenerated", "i2", "I");
			mv.visitInsn(ISHR);
			mv.visitFieldInsn(PUTFIELD, "LogicalOperationsGenerated", "i1", "I");
			mv.visitVarInsn(ALOAD, 0);
			mv.visitVarInsn(ALOAD, 0);
			mv.visitFieldInsn(GETFIELD, "LogicalOperationsGenerated", "l1", "J");
			mv.visitVarInsn(ALOAD, 0);
			mv.visitFieldInsn(GETFIELD, "LogicalOperationsGenerated", "l2", "J");
			mv.visitInsn(L2I);
			mv.visitInsn(LSHR);
			mv.visitFieldInsn(PUTFIELD, "LogicalOperationsGenerated", "l1", "J");
			mv.visitInsn(RETURN);
			mv.visitMaxs(0, 0);
			mv.visitEnd();
		}
		{
			mv = cw.visitMethod(ACC_PUBLIC, "doUShr", "()V", null, null);
			mv.visitCode();
			mv.visitVarInsn(ALOAD, 0);
			mv.visitVarInsn(ALOAD, 0);
			mv.visitFieldInsn(GETFIELD, "LogicalOperationsGenerated", "i1", "I");
			mv.visitVarInsn(ALOAD, 0);
			mv.visitFieldInsn(GETFIELD, "LogicalOperationsGenerated", "i2", "I");
			mv.visitInsn(IUSHR);
			mv.visitFieldInsn(PUTFIELD, "LogicalOperationsGenerated", "i1", "I");
			mv.visitVarInsn(ALOAD, 0);
			mv.visitVarInsn(ALOAD, 0);
			mv.visitFieldInsn(GETFIELD, "LogicalOperationsGenerated", "l1", "J");
			mv.visitVarInsn(ALOAD, 0);
			mv.visitFieldInsn(GETFIELD, "LogicalOperationsGenerated", "l2", "J");
			mv.visitInsn(L2I);
			mv.visitInsn(LUSHR);
			mv.visitFieldInsn(PUTFIELD, "LogicalOperationsGenerated", "l1", "J");
			mv.visitInsn(RETURN);
			mv.visitMaxs(0, 0);
			mv.visitEnd();
		}
		{
			mv = cw.visitMethod(ACC_PUBLIC, "doXOr", "()V", null, null);
			mv.visitCode();
			mv.visitVarInsn(ALOAD, 0);
			mv.visitVarInsn(ALOAD, 0);
			mv.visitFieldInsn(GETFIELD, "LogicalOperationsGenerated", "i2", "I");
			mv.visitVarInsn(ALOAD, 0);
			mv.visitFieldInsn(GETFIELD, "LogicalOperationsGenerated", "i1", "I");
			mv.visitInsn(IXOR);
			mv.visitFieldInsn(PUTFIELD, "LogicalOperationsGenerated", "i1", "I");
			mv.visitVarInsn(ALOAD, 0);
			mv.visitVarInsn(ALOAD, 0);
			mv.visitFieldInsn(GETFIELD, "LogicalOperationsGenerated", "l2", "J");
			mv.visitVarInsn(ALOAD, 0);
			mv.visitFieldInsn(GETFIELD, "LogicalOperationsGenerated", "l1", "J");
			mv.visitInsn(LXOR);
			mv.visitFieldInsn(PUTFIELD, "LogicalOperationsGenerated", "l1", "J");
			mv.visitVarInsn(ALOAD, 0);
			mv.visitVarInsn(ALOAD, 0);
			mv.visitFieldInsn(GETFIELD, "LogicalOperationsGenerated", "b2", "Z");
			mv.visitVarInsn(ALOAD, 0);
			mv.visitFieldInsn(GETFIELD, "LogicalOperationsGenerated", "b1", "Z");
			mv.visitInsn(IXOR);
			mv.visitFieldInsn(PUTFIELD, "LogicalOperationsGenerated", "b1", "Z");
			mv.visitInsn(RETURN);
			mv.visitMaxs(0, 0);
			mv.visitEnd();
		}
		cw.visitEnd();

	}

	
	protected String getTargetClass() {
		return "soot.asm.tests.targets.LogicalOperations";
	}

}
