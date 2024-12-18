package dev.paedar.aoc.lvl17.opcode;

import dev.paedar.aoc.lvl17.Computer;

public final class CDV extends Divider {

    @Override
    void storeResult(int result, Computer computer) {
        computer.setRegisterC(result);
    }
}
