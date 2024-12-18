package dev.paedar.aoc.lvl17.opcode;

import dev.paedar.aoc.lvl17.Computer;

public final class BST extends ComboConsumer {

    public static final BST INSTANCE = new BST();

    @Override
    void execute(long compoOperand, Computer computer) {
        computer.setRegisterB(OpCode.mod8(compoOperand));
    }

}
