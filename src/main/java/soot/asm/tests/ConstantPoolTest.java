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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;



import java.io.File;
import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;


import org.objectweb.asm.FieldVisitor;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.ClassWriter;

import soot.G;
import soot.Main;

/**
 * Test for fields that contain constant values
 *
 * @author Tobias Hamann, Florian Kuebler, Dominik Helm, Lukas Sommer, Kristen Newbury
 *
 */
public class ConstantPoolTest extends AbstractCacheSrcTest {
    private static final Logger logger = LoggerFactory.getLogger(ConstantPoolTest.class);

	
	 public static void genExampleInput(ClassWriter cw) {
		FieldVisitor fv;
		MethodVisitor mv;


		cw.visit(V1_1, ACC_PUBLIC + ACC_SUPER, "ConstantPoolTestGenerated", null,
				"java/lang/Object", null);
		cw.visitSource("ConstantPool.java", null);

		{
		    fv = cw.visitField(ACC_PUBLIC + ACC_FINAL + ACC_STATIC, "s1",
					"Ljava/lang/String;", null, "H:mm:ss.SSS");
			fv.visitEnd();
		}
		{
		    fv = cw.visitField(ACC_PUBLIC + ACC_FINAL + ACC_STATIC, "s2",
					"Ljava/lang/String;", null, null);
			fv.visitEnd();
		}
		{
		    fv = cw.visitField(ACC_PUBLIC + ACC_FINAL + ACC_STATIC, "o2",
					"Ljava/lang/Object;", null, null);
			fv.visitEnd();
		}
		{
		    fv = cw.visitField(ACC_PUBLIC + ACC_FINAL + ACC_STATIC, "o3",
					"Ljava/lang/Object;", null, null);
			fv.visitEnd();
		}
		{

		    fv = cw.visitField(ACC_PUBLIC + ACC_FINAL + ACC_STATIC, "o4",
					"Ljava/lang/Object;", null, null);
			fv.visitEnd();
		}
		{
		    fv = cw.visitField(ACC_PUBLIC + ACC_FINAL + ACC_STATIC, "o5",
					"Ljava/lang/Object;", null, null);
			fv.visitEnd();
		}
		{
                    fv = cw.visitField(ACC_PUBLIC + ACC_FINAL + ACC_STATIC, "i0", "I",
                                        null, new Integer(12));
		        fv.visitEnd();
                }
		{
		    fv = cw.visitField(ACC_PUBLIC + ACC_FINAL + ACC_STATIC, "i1", "I",
					null, new Integer(123));
			fv.visitEnd();
		}
		{
		    fv = cw.visitField(ACC_PUBLIC + ACC_FINAL + ACC_STATIC, "i2", "I",
					null, null);
			fv.visitEnd();
		}
		{
		    fv = cw.visitField(ACC_PUBLIC + ACC_FINAL + ACC_STATIC, "l1", "J",
					null, new Long(12233L));
			fv.visitEnd();
		}
		{
		    //smallest value required to be held in long
		    fv = cw.visitField(ACC_PUBLIC + ACC_FINAL + ACC_STATIC, "l2", "J",
					null, new Long(2147483648L));
			fv.visitEnd();
		}
		{
		    fv = cw.visitField(ACC_PUBLIC + ACC_FINAL + ACC_STATIC, "l3", "J",
					null, null);
			fv.visitEnd();
		}
		{
		    fv = cw.visitField(ACC_PUBLIC + ACC_FINAL + ACC_STATIC, "d1", "D",
					null, new Double("123.142"));
			fv.visitEnd();
		}
		{
		    fv = cw.visitField(ACC_PUBLIC + ACC_FINAL + ACC_STATIC, "d2", "D",
					null, new Double("1234.123046875"));
			fv.visitEnd();
		}
		{                                                                                                         

                    fv = cw.visitField(ACC_PUBLIC + ACC_FINAL , "d3", "D", null, null);                                                                     
                        fv.visitEnd();                                                                                    

                }
		{
		    fv = cw.visitField(ACC_PUBLIC + ACC_FINAL + ACC_STATIC, "d4", "D",
				       null, new Double("12345.07891235672"));
                        fv.visitEnd();
		}
		{
                    fv = cw.visitField(ACC_PUBLIC + ACC_FINAL + ACC_STATIC, "d5", "D",
                                       null, new Double("54321.5432154321"));
                        fv.visitEnd();
		}
		{
		    fv = cw.visitField(ACC_PUBLIC + ACC_FINAL + ACC_STATIC, "f1", "F",
		                       null, new Float("1.87f"));
                        fv.visitEnd();
                }
		{
			mv = cw.visitMethod(ACC_STATIC, "<clinit>", "()V", null, null);
			mv.visitCode();
			mv.visitInsn(ACONST_NULL);
			mv.visitFieldInsn(PUTSTATIC, "ConstantPoolGenerated", "s2",
					"Ljava/lang/String;");
			mv.visitLdcInsn("O");
			mv.visitFieldInsn(PUTSTATIC, "ConstantPoolGenerated", "o1",
					"Ljava/lang/Object;");
			mv.visitInsn(ACONST_NULL);
			mv.visitFieldInsn(PUTSTATIC, "ConstantPoolGenerated", "o2",
					"Ljava/lang/Object;");
			mv.visitIntInsn(BIPUSH, 123);
			mv.visitMethodInsn(INVOKESTATIC, "java/lang/Integer", "valueOf",
					"(I)Ljava/lang/Integer;", false);
			mv.visitFieldInsn(PUTSTATIC, "ConstantPoolGenerated", "o3",
					"Ljava/lang/Object;");
			mv.visitLdcInsn(new Long(1234L));
			mv.visitMethodInsn(INVOKESTATIC, "java/lang/Long", "valueOf",
					"(J)Ljava/lang/Long;", false);
			mv.visitFieldInsn(PUTSTATIC, "ConstantPoolGenerated", "o4",
					"Ljava/lang/Object;");
			mv.visitLdcInsn(new Double("123.3"));
			mv.visitMethodInsn(INVOKESTATIC, "java/lang/Double", "valueOf",
					"(D)Ljava/lang/Double;", false);
			mv.visitFieldInsn(PUTSTATIC, "ConstantPoolGenerated", "o5",
					"Ljava/lang/Object;");
			mv.visitTypeInsn(NEW, "java/lang/Integer");
			mv.visitInsn(DUP);
			mv.visitIntInsn(BIPUSH, 123);
			mv.visitMethodInsn(INVOKESPECIAL, "java/lang/Integer", "<init>",
					"(I)V", false);
			mv.visitMethodInsn(INVOKEVIRTUAL, "java/lang/Integer", "intValue",
					"()I", false);
			mv.visitFieldInsn(PUTSTATIC, "ConstantPoolGenerated", "i2", "I");
			mv.visitTypeInsn(NEW, "java/lang/Long");
			mv.visitInsn(DUP);
			mv.visitLdcInsn(new Long(12341L));
			mv.visitMethodInsn(INVOKESPECIAL, "java/lang/Long", "<init>",
					"(J)V", false);
			mv.visitMethodInsn(INVOKEVIRTUAL, "java/lang/Long", "longValue",
					"()J", false);
			mv.visitFieldInsn(PUTSTATIC, "ConstantPoolGenerated", "l3", "J");
			mv.visitTypeInsn(NEW, "java/lang/Double");
			mv.visitInsn(DUP);
			mv.visitLdcInsn(new Double("1234.123"));
			mv.visitMethodInsn(INVOKESPECIAL, "java/lang/Double", "<init>",
					"(D)V", false);
			mv.visitMethodInsn(INVOKEVIRTUAL, "java/lang/Double",
					"doubleValue", "()D", false);
			mv.visitFieldInsn(PUTSTATIC, "ConstantPoolGenerated", "d3", "D");
			mv.visitInsn(RETURN);
			mv.visitMaxs(0, 0);
			mv.visitEnd();
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
		cw.visitEnd();
	}

	protected String getTargetClass() {
		return "soot.asm.tests.targets.ConstantPool";
	}

}
