package dev.paedar.aoc.lvl17.opcode;

import dev.paedar.aoc.lvl17.Computer;

public final class ADV extends Divider {

    public static final ADV INSTANCE = new ADV();

    @Override
    void storeResult(long result, Computer computer) {
        // System.out.print("ADV A="+result);
        computer.setRegisterA(result);
    }

}
