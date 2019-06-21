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
import org.objectweb.asm.Type;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.ClassWriter;

/**
 * Test for enum classes
 *
 * @author Tobias Hamann, Florian Kuebler, Dominik Helm, Lukas Sommer, Kristen Newbury
 *
 */
public class EnumTest  extends AbstractCacheSrcTest{
	
    public static void genExampleInput(ClassWriter cw){

    FieldVisitor fv;
		MethodVisitor mv;


		cw.visit(V1_5, ACC_PUBLIC + ACC_FINAL + ACC_SUPER + ACC_ENUM,
				"MyEnumGenerated",
				"Ljava/lang/Enum<LMyEnumGenerated;>;",
				"java/lang/Enum", null);
		cw.visitSource("MyEnum.java", null);
		{
		    fv = cw.visitField(ACC_PUBLIC + ACC_FINAL + ACC_STATIC + ACC_ENUM, "NEIN", "Lsoot/asm/tests/targets/MyEnum;", null, null);
		fv.visitEnd();
		}
		{
		    fv = cw.visitField(ACC_PUBLIC + ACC_FINAL + ACC_STATIC + ACC_ENUM, "NEIN", "Lsoot/asm/tests/targets/MyEnum;", null, null);
		    fv.visitEnd();
		}
		{
		    fv = cw.visitField(ACC_PRIVATE + ACC_FINAL + ACC_STATIC + ACC_SYNTHETIC, "$VALUES", "[Lsoot/asm/tests/targets/MyEnum;", null, null);
		fv.visitEnd();
		}
		{
		mv = cw.visitMethod(ACC_STATIC, "<clinit>", "()V", null, null);
		mv.visitCode();
		mv.visitTypeInsn(NEW, "MyEnumGenerated");
		mv.visitInsn(DUP);
		mv.visitLdcInsn("JA");
		mv.visitInsn(ICONST_0);
		mv.visitMethodInsn(INVOKESPECIAL, "MyEnumGenerated", "<init>", "(Ljava/lang/String;I)V", false);
		mv.visitFieldInsn(PUTSTATIC, "MyEnumGenerated", "JA", "LMyEnumGenerated;");
		mv.visitTypeInsn(NEW, "MyEnumGenerated");
		mv.visitInsn(DUP);
		mv.visitLdcInsn("NEIN");
		mv.visitInsn(ICONST_1);
		mv.visitMethodInsn(INVOKESPECIAL, "MyEnumGenerated", "<init>", "(Ljava/lang/String;I)V", false);
		mv.visitFieldInsn(PUTSTATIC, "MyEnumGenerated", "NEIN", "LMyEnumGenerated;");
		mv.visitInsn(ICONST_2);
		mv.visitTypeInsn(ANEWARRAY, "MyEnumGenerated");
		mv.visitVarInsn(ASTORE, 0);
		mv.visitVarInsn(ALOAD, 0);
		mv.visitInsn(ICONST_0);
		mv.visitFieldInsn(GETSTATIC, "MyEnumGenerated", "JA", "LMyEnumGenerated;");
		mv.visitInsn(AASTORE);
		mv.visitVarInsn(ALOAD, 0);
		mv.visitInsn(ICONST_1);
		mv.visitFieldInsn(GETSTATIC, "MyEnumGenerated", "NEIN", "LMyEnumGenerated;");
		mv.visitInsn(AASTORE);
		mv.visitVarInsn(ALOAD, 0);
		mv.visitFieldInsn(PUTSTATIC, "MyEnumGenerated", "$VALUES", "[LMyEnumGenerated;");
		mv.visitInsn(RETURN);
		mv.visitMaxs(0, 0);
		mv.visitEnd();
		}
		{
		    mv = cw.visitMethod(ACC_PRIVATE, "<init>", "(Ljava/lang/String;I)V", "()V", null);
		mv.visitVarInsn(ALOAD, 0);
		mv.visitVarInsn(ALOAD, 1);
		mv.visitVarInsn(ILOAD, 2);
		mv.visitMethodInsn(INVOKESPECIAL, "java/lang/Enum", "<init>", "(Ljava/lang/String;I)V", false);
		mv.visitInsn(RETURN);
		mv.visitMaxs(0, 0);
		mv.visitEnd();
		}
		{

		mv.visitCode();
		mv.visitLdcInsn(Type.getType("LMyEnumGenerated;"));
		mv.visitVarInsn(ALOAD, 0);
		mv.visitMethodInsn(INVOKESTATIC, "java/lang/Enum", "valueOf", "(Ljava/lang/Class;Ljava/lang/String;)Ljava/lang/Enum;", false);
		mv.visitTypeInsn(CHECKCAST, "MyEnumGenerated");
		mv.visitInsn(ARETURN);
		mv.visitMaxs(0, 0);
		mv.visitEnd();
		}
		{

			mv.visitCode();
			mv.visitFieldInsn(GETSTATIC, "MyEnumGenerated", "$VALUES", "[LMyEnumGenerated;");
//			mv.visitMethodInsn(INVOKEVIRTUAL, "[LMyEnumGenerated;", "clone", "()Ljava/lang/Object;", false);
			mv.visitMethodInsn(INVOKEVIRTUAL, "java/lang/Object", "clone", "()Ljava/lang/Object;", false);
			mv.visitTypeInsn(CHECKCAST, "[LMyEnumGenerated;");
			mv.visitInsn(ARETURN);
			mv.visitMaxs(0, 0);
			mv.visitEnd();
		}
		cw.visitEnd();


	}

	
	protected String getTargetClass() {
		return "soot.asm.tests.targets.MyEnum";
	}

}
