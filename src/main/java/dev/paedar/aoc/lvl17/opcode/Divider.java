package dev.paedar.aoc.lvl17.opcode;

import dev.paedar.aoc.lvl17.Computer;

public abstract sealed class Divider extends ComboConsumer permits ADV, BDV, CDV {

    abstract void storeResult(long result, Computer computer);

    @Override
    void execute(long comboOperand, Computer computer) {
        long numerator = computer.getRegisterA();
        long result = numerator >> comboOperand; /* bitshifting b to the right is the same as dividing by 2^b */
        storeResult(result, computer);
        // System.out.println("=A(%d) >> %d".formatted(numerator, comboOperand));
    }

}
