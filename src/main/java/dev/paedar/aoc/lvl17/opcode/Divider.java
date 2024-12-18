package dev.paedar.aoc.lvl17.opcode;

import dev.paedar.aoc.lvl17.Computer;

public abstract sealed class Divider extends ComboConsumer permits ADV, BDV, CDV {

    abstract void storeResult(int result, Computer computer);

    @Override
    void execute(int compoOperand, Computer computer) {
        var numerator = computer.getRegisterA();
        var result = numerator >> compoOperand; /* bitshifting b to the right is the same as dividing by 2^b */
        storeResult(result, computer);
    }

}
