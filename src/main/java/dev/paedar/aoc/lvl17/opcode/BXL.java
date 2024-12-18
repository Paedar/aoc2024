package dev.paedar.aoc.lvl17.opcode;

import dev.paedar.aoc.lvl17.Computer;

public final class BXL extends LiteralConsumer {

    @Override
    void execute(int literalOperand, Computer computer) {
        var b = computer.getRegisterB();
        computer.setRegisterB(literalOperand ^ b);
    }

}
