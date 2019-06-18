package soot.asm.tests;

import org.objectweb.asm.FieldVisitor;
import org.objectweb.asm.Label;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.ClassWriter;

public class AllOpsTest{

    public static ClassWriter genExampleInput() {
	MethodVisitor mv;

        ClassWriter visitor = new ClassWriter(0);
	
	visitor.visit(Opcodes.V1_1, Opcodes.ACC_PUBLIC + Opcodes.ACC_SUPER,
                                "AllOpsTestGenerated", null, "java/lang/Object", null);



	mv = visitor.visitMethod(Opcodes.ACC_PUBLIC, "<init>", "()V", null, null);
	mv.visitCode();

		
	mv.visitInsn(Opcodes.ACONST_NULL);
	mv.visitInsn(Opcodes.ICONST_M1);
	mv.visitInsn(Opcodes.ICONST_0);
	mv.visitInsn(Opcodes.ICONST_1);
	mv.visitInsn(Opcodes.ICONST_2);
	mv.visitInsn(Opcodes.ICONST_3);
	mv.visitInsn(Opcodes.ICONST_4);
	mv.visitInsn(Opcodes.ICONST_5);
	mv.visitInsn(Opcodes.LCONST_0);
	mv.visitInsn(Opcodes.LCONST_1);
	mv.visitInsn(Opcodes.FCONST_0);
	mv.visitInsn(Opcodes.FCONST_1);
	mv.visitInsn(Opcodes.FCONST_2);
	mv.visitInsn(Opcodes.DCONST_0);
	mv.visitInsn(Opcodes.DCONST_1);
	mv.visitIntInsn(Opcodes.BIPUSH, 17);
	mv.visitIntInsn(Opcodes.SIPUSH, 1000);
	mv.visitVarInsn(Opcodes.ILOAD, 1);
	mv.visitVarInsn(Opcodes.LLOAD, 1);
	mv.visitVarInsn(Opcodes.FLOAD, 1);
	mv.visitVarInsn(Opcodes.DLOAD, 1);
	mv.visitVarInsn(Opcodes.ALOAD, 0);
	mv.visitInsn(Opcodes.IALOAD);
	mv.visitInsn(Opcodes.LALOAD);
	mv.visitInsn(Opcodes.FALOAD);
	mv.visitInsn(Opcodes.DALOAD);
	mv.visitInsn(Opcodes.AALOAD);
	mv.visitInsn(Opcodes.BALOAD);
	mv.visitInsn(Opcodes.CALOAD);
	mv.visitInsn(Opcodes.SALOAD);
	mv.visitVarInsn(Opcodes.ISTORE, 0);
	mv.visitVarInsn(Opcodes.LSTORE, 1);
	mv.visitVarInsn(Opcodes.FSTORE, 0);
	mv.visitVarInsn(Opcodes.DSTORE, 1);
	mv.visitInsn(Opcodes.BASTORE);
	mv.visitInsn(Opcodes.IASTORE);
	mv.visitInsn(Opcodes.LASTORE);
	mv.visitInsn(Opcodes.FASTORE);
	mv.visitInsn(Opcodes.DASTORE);
	mv.visitInsn(Opcodes.AASTORE);
	mv.visitInsn(Opcodes.BASTORE);
	mv.visitInsn(Opcodes.CASTORE);
	mv.visitInsn(Opcodes.SASTORE);
	mv.visitInsn(Opcodes.POP);
	mv.visitInsn(Opcodes.DUP);
	mv.visitInsn(Opcodes.IADD);
	mv.visitInsn(Opcodes.LADD);
	mv.visitInsn(Opcodes.FADD);
	mv.visitInsn(Opcodes.DADD);
	mv.visitInsn(Opcodes.ISUB);
	mv.visitInsn(Opcodes.LSUB);
	mv.visitInsn(Opcodes.FSUB);
	mv.visitInsn(Opcodes.DSUB);
	mv.visitInsn(Opcodes.IMUL);
	mv.visitInsn(Opcodes.LMUL);
	mv.visitInsn(Opcodes.FMUL);
	mv.visitInsn(Opcodes.DMUL);
	mv.visitInsn(Opcodes.IDIV);
	mv.visitInsn(Opcodes.LDIV);
	mv.visitInsn(Opcodes.FDIV);
	mv.visitInsn(Opcodes.DDIV);
	mv.visitInsn(Opcodes.IREM);
	mv.visitInsn(Opcodes.LREM);
	mv.visitInsn(Opcodes.FREM);
	mv.visitInsn(Opcodes.DREM);
	mv.visitInsn(Opcodes.INEG);
	mv.visitInsn(Opcodes.FNEG);
	mv.visitInsn(Opcodes.DNEG);
	mv.visitInsn(Opcodes.ISHL);
	mv.visitInsn(Opcodes.LSHL);
	mv.visitInsn(Opcodes.ISHR);
	mv.visitInsn(Opcodes.LSHR);
	mv.visitInsn(Opcodes.IUSHR);
	mv.visitInsn(Opcodes.LUSHR);
	mv.visitInsn(Opcodes.IAND);
	mv.visitInsn(Opcodes.LAND);
	mv.visitInsn(Opcodes.IOR);
	mv.visitInsn(Opcodes.LOR);
	mv.visitInsn(Opcodes.IXOR);
	mv.visitInsn(Opcodes.LXOR);
	mv.visitInsn(Opcodes.I2L);
	mv.visitInsn(Opcodes.I2F);
	mv.visitInsn(Opcodes.I2D);
	mv.visitInsn(Opcodes.L2I);
	mv.visitInsn(Opcodes.L2F);
	mv.visitInsn(Opcodes.L2D);
	mv.visitInsn(Opcodes.F2I);
	mv.visitInsn(Opcodes.F2L);
	mv.visitInsn(Opcodes.F2D);
	mv.visitInsn(Opcodes.D2I);
	mv.visitInsn(Opcodes.D2L);
	mv.visitInsn(Opcodes.D2F);
	mv.visitInsn(Opcodes.I2B);
	mv.visitInsn(Opcodes.I2C);
	mv.visitInsn(Opcodes.I2S);
	mv.visitInsn(Opcodes.LCMP);
	mv.visitInsn(Opcodes.FCMPL);
	mv.visitInsn(Opcodes.FCMPG);
	mv.visitInsn(Opcodes.DCMPL);
	mv.visitInsn(Opcodes.DCMPG);

	Label l0 = new Label();
	Label l1 = new Label();
	mv.visitLabel(l0);
	mv.visitLabel(l1);
	
	mv.visitJumpInsn(Opcodes.IFEQ, l1);
	mv.visitJumpInsn(Opcodes.IFNE, l0);
	mv.visitJumpInsn(Opcodes.IFLT, l0);
	mv.visitJumpInsn(Opcodes.IFGE, l1);
	mv.visitJumpInsn(Opcodes.IFGT, l0);
	mv.visitJumpInsn(Opcodes.IFLE, l1);
	mv.visitJumpInsn(Opcodes.IF_ICMPEQ, l1);
	mv.visitJumpInsn(Opcodes.IF_ICMPNE, l0);
	mv.visitJumpInsn(Opcodes.IF_ICMPLT, l0);
	mv.visitJumpInsn(Opcodes.IF_ICMPGE, l1);
	mv.visitJumpInsn(Opcodes.IF_ICMPGT, l0);
	mv.visitJumpInsn(Opcodes.IF_ICMPLE, l1);
	mv.visitJumpInsn(Opcodes.GOTO, l0);
	mv.visitInsn(Opcodes.RETURN);
	mv.visitInsn(Opcodes.IRETURN);
	mv.visitInsn(Opcodes.LRETURN);
	mv.visitInsn(Opcodes.FRETURN);
	mv.visitInsn(Opcodes.DRETURN);
	mv.visitInsn(Opcodes.ARETURN);
	mv.visitInsn(Opcodes.RETURN);
	mv.visitFieldInsn(Opcodes.GETSTATIC, "soot/asm/backend/targets/MyEnum", "JA", "Lsoot/asm/backend/targets/MyEnum;");
	mv.visitFieldInsn(Opcodes.PUTSTATIC, "soot/asm/backend/targets/ConstantPool", "s2","Ljava/lang/String;");
	mv.visitFieldInsn(Opcodes.GETFIELD, "soot/asm/backend/targets/ArithmeticLib", "rInt", "I");
	mv.visitFieldInsn(Opcodes.PUTFIELD, "soot/asm/backend/targets/ArithmeticLib", "cInt", "I");
	mv.visitMethodInsn(Opcodes.INVOKEVIRTUAL, "java/lang/Integer", "intValue","(Opcodes.)I", false);
	mv.visitMethodInsn(Opcodes.INVOKESPECIAL, "java/lang/Object", "<init>", "(Opcodes.)V", false);
	mv.visitMethodInsn(Opcodes.INVOKESTATIC, "java/lang/Integer", "valueOf","(Opcodes.I)Ljava/lang/Integer;", false);
	mv.visitMethodInsn(Opcodes.INVOKEINTERFACE, "java/util/List", "add", "(Opcodes.Ljava/lang/Object;)Z", true);
	mv.visitIntInsn(Opcodes.NEWARRAY, Opcodes.T_BOOLEAN);
	mv.visitTypeInsn(Opcodes.ANEWARRAY, "java/lang/Object");
	mv.visitInsn(Opcodes.ARRAYLENGTH);
	mv.visitInsn(Opcodes.ATHROW);
	mv.visitTypeInsn(Opcodes.CHECKCAST, "soot/asm/backend/targets/MyEnum");
	mv.visitTypeInsn(Opcodes.INSTANCEOF, "[Lsoot/asm/backend/targets/Measurable;");
	mv.visitInsn(Opcodes.MONITORENTER);
	mv.visitInsn(Opcodes.MONITOREXIT);
	mv.visitJumpInsn(Opcodes.IFNULL, l1);
	mv.visitJumpInsn(Opcodes.IFNONNULL, l0);

	mv.visitMaxs(100, 100);
	mv.visitEnd();
	
	visitor.visitEnd();
	return visitor;
    }
}
