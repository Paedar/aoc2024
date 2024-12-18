package dev.paedar.aoc.lvl17.opcode;

import dev.paedar.aoc.lvl17.Computer;

public final class BDV extends Divider {

    public static final BDV INSTANCE = new BDV();

    @Override
    void storeResult(long result, Computer computer) {
        // System.out.print("BDV B="+result);
        computer.setRegisterB(result);
    }

}
