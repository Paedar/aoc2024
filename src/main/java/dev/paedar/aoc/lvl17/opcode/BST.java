package dev.paedar.aoc.lvl17.opcode;

import dev.paedar.aoc.lvl17.Computer;

public final class BST extends ComboConsumer {

    public static final BST INSTANCE = new BST();

    @Override
    void execute(long comboOperand, Computer computer) {
        var value = OpCode.mod8(comboOperand);
        // System.out.println("BST %d B=%d".formatted(comboOperand,value));
        computer.setRegisterB(value);
    }

}
