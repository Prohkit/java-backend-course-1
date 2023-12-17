package edu.hw11.task3;

import net.bytebuddy.description.method.MethodDescription;
import net.bytebuddy.implementation.Implementation;
import net.bytebuddy.implementation.bytecode.ByteCodeAppender;
import net.bytebuddy.jar.asm.Label;
import net.bytebuddy.jar.asm.MethodVisitor;
import net.bytebuddy.jar.asm.Opcodes;
import org.jetbrains.annotations.NotNull;

public class FibonacciAppender implements ByteCodeAppender {
    @Override
    public @NotNull Size apply(
        MethodVisitor methodVisitor,
        Implementation.@NotNull Context context,
        MethodDescription methodDescription
    ) {
        methodVisitor.visitCode();

        methodVisitor.visitVarInsn(Opcodes.ILOAD, 1);

        Label ifLabel = new Label();
        methodVisitor.visitJumpInsn(Opcodes.IFLE, ifLabel);

        methodVisitor.visitInsn(Opcodes.LCONST_1);
        methodVisitor.visitInsn(Opcodes.LCONST_1);

        Label startLoop = new Label();
        methodVisitor.visitLabel(startLoop);
        methodVisitor.visitInsn(Opcodes.LADD);
        methodVisitor.visitVarInsn(Opcodes.IINC, 1);
        methodVisitor.visitVarInsn(Opcodes.ILOAD, 1);
        methodVisitor.visitJumpInsn(Opcodes.IFLE, startLoop);

        methodVisitor.visitLabel(ifLabel);
        methodVisitor.visitInsn(Opcodes.LRETURN);

        methodVisitor.visitMaxs(2, 2);
        methodVisitor.visitEnd();

        return new Size(2, methodDescription.getStackSize());
    }
}
