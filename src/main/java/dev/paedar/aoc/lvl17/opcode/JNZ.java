package dev.paedar.aoc.lvl17.opcode;

import dev.paedar.aoc.lvl17.Computer;

public final class JNZ extends LiteralConsumer {

    public static final JNZ INSTANCE = new JNZ();

    @Override
    void execute(int literalOperand, Computer computer) {
        if (computer.getRegisterA() != 0) {
            computer.jump(literalOperand);
        }
    }

}
