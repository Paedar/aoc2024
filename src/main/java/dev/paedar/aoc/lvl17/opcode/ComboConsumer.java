package dev.paedar.aoc.lvl17.opcode;

import dev.paedar.aoc.lvl17.Computer;

public abstract sealed class ComboConsumer implements OpCode permits BST, OUT, Divider {

    abstract void execute(int compoOperand, Computer computer);

    @Override
    public void execute(Computer computer) {
        execute(computer.readCombo(), computer);
    }

}
