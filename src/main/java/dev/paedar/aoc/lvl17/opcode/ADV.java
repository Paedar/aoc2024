package dev.paedar.aoc.lvl17.opcode;

import dev.paedar.aoc.lvl17.Computer;

public final class ADV extends Divider {

    public static final ADV INSTANCE = new ADV();

    @Override
    void storeResult(int result, Computer computer) {
        computer.setRegisterA(result);
    }

}
