package dev.paedar.aoc.lvl17.opcode;

import dev.paedar.aoc.lvl17.Computer;

public final class ADV extends Divider {

    @Override
    void storeResult(int result, Computer computer) {
        computer.setRegisterA(result);
    }

}
