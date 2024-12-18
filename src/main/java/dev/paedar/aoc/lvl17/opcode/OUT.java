package dev.paedar.aoc.lvl17.opcode;

import dev.paedar.aoc.lvl17.Computer;

public final class OUT extends ComboConsumer {

    public static final OUT INSTANCE = new OUT();

    @Override
    void execute(long compoOperand, Computer computer) {
        computer.output((int) OpCode.mod8(compoOperand));
    }

}
