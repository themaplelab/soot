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
 * Test for several store bytecode instructions
 *
 * @author Tobias Hamann, Florian Kuebler, Dominik Helm, Lukas Sommer, Kristen Newbury
 *
 */
public class StoresTest  extends AbstractCacheSrcTest{

	
    public static void genExampleInput(ClassWriter visitor){
	MethodVisitor mv;

	visitor.visit(V1_1, ACC_PUBLIC + ACC_SUPER, "StoresGenerated", null, "java/lang/Object", null);
		visitor.visitSource("Stores.java", null);
		{
		mv = visitor.visitMethod(ACC_PUBLIC, "<init>", "()V", null, null);
		mv.visitCode();
		mv.visitVarInsn(ALOAD, 0);
		mv.visitMethodInsn(INVOKESPECIAL, "java/lang/Object", "<init>", "()V", false);
		mv.visitInsn(RETURN);
		mv.visitMaxs(0, 0);
		mv.visitEnd();
		}
		{
		    mv = visitor.visitMethod(ACC_PUBLIC, "doSth", "()I", null, null);
			mv.visitCode();
			mv.visitMethodInsn(INVOKESTATIC, "java/lang/System", "currentTimeMillis", "()J", false);
			mv.visitInsn(LCONST_0);
			mv.visitInsn(LCMP);
			Label l0 = new Label();
			mv.visitJumpInsn(IFLE, l0);
			mv.visitInsn(ICONST_1);
			mv.visitVarInsn(ISTORE, 1);			
			Label l1 = new Label();
			mv.visitJumpInsn(GOTO, l1);
			
			mv.visitLabel(l0);
			mv.visitInsn(ICONST_0);
			mv.visitVarInsn(ISTORE, 1);

			mv.visitLabel(l1);
			mv.visitTypeInsn(NEW, "java/lang/Object");
			mv.visitVarInsn(ASTORE, 0);
			mv.visitVarInsn(ALOAD, 0);
			mv.visitMethodInsn(INVOKESPECIAL, "java/lang/Object", "<init>", "()V", false);
			mv.visitInsn(ICONST_3);
			mv.visitIntInsn(NEWARRAY, T_INT);
			mv.visitInsn(ICONST_1);
			mv.visitLdcInsn(new Integer(24355764));
			mv.visitInsn(IASTORE);
			mv.visitFieldInsn(GETSTATIC, "java/lang/System", "out", "Ljava/io/PrintStream;");
			mv.visitTypeInsn(NEW, "java/lang/StringBuilder");
			mv.visitInsn(DUP);
			mv.visitMethodInsn(INVOKESPECIAL, "java/lang/StringBuilder", "<init>", "()V", false);
			mv.visitLdcInsn(new Integer(2343249));
			mv.visitInsn(I2D);
			mv.visitLdcInsn(new Double("3.14324"));
			mv.visitInsn(DADD);
			mv.visitLdcInsn(new Float("3.143"));
			mv.visitInsn(F2D);
			mv.visitInsn(DADD);
			mv.visitIntInsn(SIPUSH, 4636);
			mv.visitInsn(I2D);
			mv.visitInsn(DADD);
			mv.visitMethodInsn(INVOKEVIRTUAL, "java/lang/StringBuilder", "append", "(D)Ljava/lang/StringBuilder;", false);
			mv.visitLdcInsn("");
			mv.visitMethodInsn(INVOKEVIRTUAL, "java/lang/StringBuilder", "append", "(Ljava/lang/String;)Ljava/lang/StringBuilder;", false);
			mv.visitVarInsn(ILOAD, 1);
			mv.visitMethodInsn(INVOKEVIRTUAL, "java/lang/StringBuilder", "append", "(Z)Ljava/lang/StringBuilder;", false);
			mv.visitLdcInsn(new Integer(2343249));
			mv.visitInsn(I2B);
			mv.visitMethodInsn(INVOKEVIRTUAL, "java/lang/StringBuilder", "append", "(I)Ljava/lang/StringBuilder;", false);
			mv.visitLdcInsn(new Long(314435665L));
			mv.visitMethodInsn(INVOKEVIRTUAL, "java/lang/StringBuilder", "append", "(J)Ljava/lang/StringBuilder;", false);
			mv.visitIntInsn(BIPUSH, 123);
			mv.visitMethodInsn(INVOKEVIRTUAL, "java/lang/StringBuilder", "append", "(C)Ljava/lang/StringBuilder;", false);
			mv.visitLdcInsn(" ");
			mv.visitMethodInsn(INVOKEVIRTUAL, "java/lang/StringBuilder", "append", "(Ljava/lang/String;)Ljava/lang/StringBuilder;", false);
			mv.visitVarInsn(ALOAD, 0);
			mv.visitMethodInsn(INVOKEVIRTUAL, "java/lang/StringBuilder", "append", "(Ljava/lang/Object;)Ljava/lang/StringBuilder;", false);
			mv.visitMethodInsn(INVOKEVIRTUAL, "java/lang/StringBuilder", "toString", "()Ljava/lang/String;", false);
			mv.visitMethodInsn(INVOKEVIRTUAL, "java/io/PrintStream", "println", "(Ljava/lang/String;)V", false);
			mv.visitLdcInsn(new Integer(2343249));
			mv.visitInsn(IRETURN);
			mv.visitMaxs(0, 0);
			mv.visitEnd();
		}
		visitor.visitEnd();


	}

	
	protected String getTargetClass() {
		return "soot.asm.tests.targets.Stores";
	}

}
