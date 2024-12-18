package dev.paedar.aoc.lvl17.opcode;

import dev.paedar.aoc.lvl17.Computer;

public final class BST extends ComboConsumer {

    @Override
    void execute(int compoOperand, Computer computer) {
        computer.setRegisterB(compoOperand % 8);
    }

}
