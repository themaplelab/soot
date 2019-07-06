package soot.asm.tests;

/*-
 * #%L
 * Soot - a J*va Optimization Framework
 * %%
 * Copyright (C) 1997 - 2019
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
import org.objectweb.asm.Handle;
import java.lang.invoke.CallSite;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.lang.invoke.MethodHandle;
import java.lang.invoke.ConstantCallSite;

/**
 * Test for invokedynamic
 * taken from https://stackoverflow.com/questions/18971830/generating-working-invokedynamic-instruction-with-asm
 * Qauthor: arshajii 
 * Aauthor: Antimony
 *
 * @author Kristen Newbury
 */
public class InvokeDynamicTest extends AbstractCacheSrcTest{

 	public static void genExampleInput(ClassWriter visitor) {
		FieldVisitor fv;
		MethodVisitor mv;

		visitor.visit(Opcodes.V1_8, Opcodes.ACC_PUBLIC + Opcodes.ACC_SUPER,
			      "InvokeDynamicTestGenerated", null, "java/lang/Object", null);

		visitor.visitSource("InvokeDynamic.java", null);
		{
		    mv = visitor.visitMethod(ACC_PUBLIC + ACC_STATIC, "main",
					"([Ljava/lang/String;)V", null, null);
		    mv.visitCode();

		    mv.visitFieldInsn(GETSTATIC, "java/lang/System", "out",
				      "Ljava/io/PrintStream;");
		    
		    mv.visitIntInsn(BIPUSH, 42);
		    mv.visitIntInsn(BIPUSH, 24);
		    
		    
		    MethodType mt = MethodType.methodType(CallSite.class,
							  MethodHandles.Lookup.class, String.class, MethodType.class);

		    Handle bootstrap = new Handle(Opcodes.H_INVOKESTATIC, "Test2",
						  "bootstrap", mt.toMethodDescriptorString());
		    
		    mv.visitInvokeDynamicInsn("plus", "(II)I", bootstrap, new Object[0]);


		    mv.visitIntInsn(BIPUSH, 42);
                    mv.visitIntInsn(BIPUSH, 24);

		    Handle bootstrap0 = new Handle(Opcodes.H_INVOKESTATIC, "Test0",
                                                  "bootstrap0", mt.toMethodDescriptorString());

                    mv.visitInvokeDynamicInsn("minus", "(II)I", bootstrap0, new Object[0]);
		    
		    
		    mv.visitMethodInsn(INVOKEVIRTUAL, "java/io/PrintStream", "println",
				       "(I)V");
		    
		    mv.visitInsn(RETURN);
		    mv.visitMaxs(0, 0);
		    mv.visitEnd();
		}
		
		visitor.visitEnd();

	}

}
