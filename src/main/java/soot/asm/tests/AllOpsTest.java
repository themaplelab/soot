package soot.asm.tests;

import org.objectweb.asm.FieldVisitor;
import org.objectweb.asm.Label;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.ClassWriter;

public class AllOpsTest extends AbstractCacheSrcTest{

    public static ClassWriter genExampleInput() {
	MethodVisitor mv;

        ClassWriter cw = new ClassWriter(0);
	

	cw.visit(V1_1, ACC_PUBLIC, "AllOpsTestGenerated", null, "java/lang/Object", null);


	mv = cw.visitMethod(ACC_PUBLIC, "<init>", "()V", null, null);
	mv.visitCode();

		
	mv.visitInsn(ACONST_NULL);
	mv.visitInsn(ICONST_M1);
	mv.visitInsn(ICONST_0);
	mv.visitInsn(ICONST_1);
	mv.visitInsn(ICONST_2);
	mv.visitInsn(ICONST_3);
	mv.visitInsn(ICONST_4);
	mv.visitInsn(ICONST_5);
	mv.visitInsn(LCONST_0);
	mv.visitInsn(LCONST_1);
	mv.visitInsn(FCONST_0);
	mv.visitInsn(FCONST_1);
	mv.visitInsn(FCONST_2);
	mv.visitInsn(DCONST_0);
	mv.visitInsn(DCONST_1);
	mv.visitIntInsn(BIPUSH, 17);
	mv.visitIntInsn(SIPUSH, 1000);
	mv.visitVarInsn(ILOAD, 1);
	mv.visitVarInsn(LLOAD, 1);
	mv.visitVarInsn(FLOAD, 1);
	mv.visitVarInsn(DLOAD, 1);
	mv.visitVarInsn(ALOAD, 0);








	mv.visitVarInsn(ISTORE, 0);
	mv.visitVarInsn(LSTORE, 1);
	mv.visitVarInsn(FSTORE, 0);
	mv.visitVarInsn(DSTORE, 1);
	mv.visitInsn(BASTORE);
	mv.visitInsn(IASTORE);
	mv.visitInsn(LASTORE);
	mv.visitInsn(FASTORE);
	mv.visitInsn(DASTORE);
	mv.visitInsn(AASTORE);
	mv.visitInsn(BASTORE);
	mv.visitInsn(CASTORE);
	mv.visitInsn(SASTORE);
	mv.visitInsn(POP);
	mv.visitInsn(DUP);
	mv.visitInsn(IADD);
	mv.visitInsn(LADD);
	mv.visitInsn(FADD);
	mv.visitInsn(DADD);
	mv.visitInsn(ISUB);
	mv.visitInsn(LSUB);
	mv.visitInsn(FSUB);
	mv.visitInsn(DSUB);
	mv.visitInsn(IMUL);
	mv.visitInsn(LMUL);
	mv.visitInsn(FMUL);
	mv.visitInsn(DMUL);
	mv.visitInsn(IDIV);
	mv.visitInsn(LDIV);
	mv.visitInsn(FDIV);
	mv.visitInsn(DDIV);
	mv.visitInsn(IREM);
	mv.visitInsn(LREM);
	mv.visitInsn(FREM);
	mv.visitInsn(DREM);
	mv.visitInsn(INEG);
	mv.visitInsn(FNEG);
	mv.visitInsn(DNEG);
	mv.visitInsn(ISHL);
	mv.visitInsn(LSHL);
	mv.visitInsn(ISHR);
	mv.visitInsn(LSHR);
	mv.visitInsn(IUSHR);
	mv.visitInsn(LUSHR);
	mv.visitInsn(IAND);
	mv.visitInsn(LAND);
	mv.visitInsn(IOR);
	mv.visitInsn(LOR);
	mv.visitInsn(IXOR);
	mv.visitInsn(LXOR);
	mv.visitInsn(I2L);
	mv.visitInsn(I2F);
	mv.visitInsn(I2D);
	mv.visitInsn(L2I);
	mv.visitInsn(L2F);
	mv.visitInsn(L2D);
	mv.visitInsn(F2I);
	mv.visitInsn(F2L);
	mv.visitInsn(F2D);
	mv.visitInsn(D2I);
	mv.visitInsn(D2L);
	mv.visitInsn(D2F);
	mv.visitInsn(I2B);
	mv.visitInsn(I2C);
	mv.visitInsn(I2S);
	mv.visitInsn(LCMP);
	mv.visitInsn(FCMPL);
	mv.visitInsn(FCMPG);
	mv.visitInsn(DCMPL);
	mv.visitInsn(DCMPG);

	Label l0 = new Label();
	Label l1 = new Label();
	mv.visitLabel(l0);
	mv.visitLabel(l1);
	
	mv.visitJumpInsn(IFEQ, l1);
	mv.visitJumpInsn(IFNE, l0);
	mv.visitJumpInsn(IFLT, l0);
	mv.visitJumpInsn(IFGE, l1);
	mv.visitJumpInsn(IFGT, l0);
	mv.visitJumpInsn(IFLE, l1);
	mv.visitJumpInsn(IF_ICMPEQ, l1);
	mv.visitJumpInsn(IF_ICMPNE, l0);
	mv.visitJumpInsn(IF_ICMPLT, l0);
	mv.visitJumpInsn(IF_ICMPGE, l1);
	mv.visitJumpInsn(IF_ICMPGT, l0);
	mv.visitJumpInsn(IF_ICMPLE, l1);
	mv.visitJumpInsn(GOTO, l0);







	mv.visitFieldInsn(GETSTATIC, "soot/asm/backend/targets/MyEnum", "JA", "Lsoot/asm/backend/targets/MyEnum;");
	mv.visitFieldInsn(PUTSTATIC, "soot/asm/backend/targets/ConstantPool", "s2","Ljava/lang/String;");
	mv.visitFieldInsn(GETFIELD, "soot/asm/backend/targets/ArithmeticLib", "rInt", "I");
	mv.visitFieldInsn(PUTFIELD, "soot/asm/backend/targets/ArithmeticLib", "cInt", "I");
	mv.visitMethodInsn(INVOKEVIRTUAL, "java/lang/Integer", "intValue","()I", false);
	mv.visitMethodInsn(INVOKESPECIAL, "java/lang/Object", "<init>", "()V", false);
	mv.visitMethodInsn(INVOKESTATIC, "java/lang/Integer", "valueOf","(I)Ljava/lang/Integer;", false);
	mv.visitMethodInsn(INVOKEINTERFACE, "java/util/List", "add", "(Ljava/lang/Object;)Z", true);

	mv.visitTypeInsn(ANEWARRAY, "java/lang/Object");
	mv.visitInsn(ARRAYLENGTH);
	mv.visitInsn(ATHROW);
	mv.visitTypeInsn(CHECKCAST, "soot/asm/backend/targets/MyEnum");
	mv.visitTypeInsn(INSTANCEOF, "[Lsoot/asm/backend/targets/Measurable;");
	mv.visitInsn(MONITORENTER);
	mv.visitInsn(MONITOREXIT);
	mv.visitJumpInsn(IFNULL, l1);
	mv.visitJumpInsn(IFNONNULL, l0);


	
	mv.visitMaxs(100, 100);
	mv.visitEnd();
	
	cw.visitEnd();
	return cw;
    }
}
