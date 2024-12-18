package dev.paedar.aoc.lvl17.opcode;

import dev.paedar.aoc.lvl17.Computer;

public abstract sealed class LiteralConsumer implements OpCode permits BXL, JNZ {

    abstract void execute(long literalOperand, Computer computer);

    @Override
    public void execute(Computer computer) {
        execute(computer.readLiteral(), computer);
    }

}
