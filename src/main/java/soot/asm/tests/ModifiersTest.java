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
 * Test for various combinations of accessibility modifiers
 *
 * @author Tobias Hamann, Florian Kuebler, Dominik Helm, Lukas Sommer, Kristen Newbury
 *
 */
public class ModifiersTest  extends AbstractCacheSrcTest{

	
    public static void genExampleInput(ClassWriter cw){
    MethodVisitor mv;
		FieldVisitor fv;
		

		cw.visit(V1_3, ACC_PUBLIC + ACC_SUPER + ACC_ABSTRACT, "ModifiersGenerated", null, "java/lang/Object", null);
		cw.visitSource("Modifiers.java", null);

		{
		fv = cw.visitField(ACC_PRIVATE + ACC_VOLATILE, "i", "I", null, null);
		fv.visitEnd();
		}
		{
		fv = cw.visitField(ACC_PRIVATE + ACC_FINAL, "j", "I", null, new Integer(213));
		fv.visitEnd();
		}
		{
		fv = cw.visitField(ACC_PRIVATE + ACC_TRANSIENT, "k", "I", null, null);
		fv.visitEnd();
		}
		{
		mv = cw.visitMethod(ACC_PUBLIC + ACC_STRICT, "<init>", "()V", null, null);
		mv.visitCode();
		mv.visitVarInsn(ALOAD, 0);
		mv.visitMethodInsn(INVOKESPECIAL, "java/lang/Object", "<init>", "()V", false);
		mv.visitVarInsn(ALOAD, 0);
		mv.visitIntInsn(SIPUSH, 213);
		mv.visitFieldInsn(PUTFIELD, "ModifiersGenerated", "j", "I");
		mv.visitInsn(RETURN);
		mv.visitMaxs(0, 0);
		mv.visitEnd();
		}
		{

		mv.visitCode();
		mv.visitInsn(RETURN);
		mv.visitMaxs(0, 0);
		mv.visitEnd();
		}
		{
		mv = cw.visitMethod(ACC_PUBLIC + ACC_SYNCHRONIZED + ACC_STRICT, "b", "()V", null, null);
		mv.visitCode();
		mv.visitInsn(RETURN);
		mv.visitMaxs(0, 0);
		mv.visitEnd();
		}
		{

		mv.visitCode();
		mv.visitInsn(RETURN);
		mv.visitMaxs(0, 0);
		mv.visitEnd();
		}
		{
		mv = cw.visitMethod(ACC_STRICT, "d", "()V", null, null);
		mv.visitCode();
		mv.visitInsn(RETURN);
		mv.visitMaxs(0, 0);
		mv.visitEnd();
		}
		{
		mv = cw.visitMethod(ACC_PROTECTED + ACC_STRICT, "e", "()V", null, null);
		mv.visitCode();
		mv.visitInsn(RETURN);
		mv.visitMaxs(0, 0);
		mv.visitEnd();
		}
		{
		mv = cw.visitMethod(ACC_ABSTRACT, "f", "()V", null, null);
		mv.visitEnd();
		}
		{
		    mv = cw.visitMethod(ACC_PRIVATE + ACC_NATIVE + ACC_STRICT, "g", "()V", null, null);
		mv.visitEnd();
		}
		cw.visitEnd();


	}

	
	protected String getTargetClass() {
		return "soot.asm.tests.targets.Modifiers";
	}

	
	protected String getRequiredJavaVersion(){
		return "1.3";
	}

}
