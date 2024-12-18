package dev.paedar.aoc.lvl17.opcode;

import dev.paedar.aoc.lvl17.Computer;

public abstract sealed class Divider extends ComboConsumer permits ADV, BDV, CDV {

    abstract void storeResult(int result, Computer computer);

    @Override
    void execute(int compoOperand, Computer computer) {
        var numerator = computer.getRegisterA();
        var denominator = Math.pow(2, compoOperand);
        var result = numerator / denominator;
        storeResult((int) result, computer);
    }
}
