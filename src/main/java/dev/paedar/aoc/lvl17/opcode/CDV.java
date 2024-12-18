package dev.paedar.aoc.lvl17.opcode;

import dev.paedar.aoc.lvl17.Computer;

public final class CDV extends Divider {

    public static final CDV INSTANCE = new CDV();

    @Override
    void storeResult(long result, Computer computer) {
        computer.setRegisterC(result);
    }

}
