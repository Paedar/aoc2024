package dev.paedar.aoc.lvl17.opcode;

import dev.paedar.aoc.lvl17.Computer;

public final class BDV extends Divider {

    public static final BDV INSTANCE = new BDV();

    @Override
    void storeResult(int result, Computer computer) {
        computer.setRegisterB(result);
    }

}
