package dev.paedar.aoc.lvl17.opcode;

import dev.paedar.aoc.lvl17.Computer;

public final class OUT extends ComboConsumer {

    public static final OUT INSTANCE = new OUT();

    @Override
    void execute(long comboOperand, Computer computer) {
        var out = (int) OpCode.mod8(comboOperand);
        // System.out.println("OUT " + comboOperand + " -> " + out);
        computer.output(out);
    }

}
