package dev.paedar.aoc.lvl17.opcode;

import dev.paedar.aoc.lvl17.Computer;

public final class JNZ extends LiteralConsumer {

    public static final JNZ INSTANCE = new JNZ();

    @Override
    void execute(long literalOperand, Computer computer) {
        // System.out.print("JNZ ");
        if (computer.getRegisterA() != 0) {
            // System.out.println(literalOperand);
            computer.jump((int) literalOperand);
        } else {
            // System.out.println("A==0");
        }
    }

}
