package dev.paedar.aoc.lvl17.opcode;

import dev.paedar.aoc.lvl17.Computer;

public final class OUT extends ComboConsumer {

    @Override
    void execute(int compoOperand, Computer computer) {
        computer.output(OpCode.mod8(compoOperand));
    }


}
