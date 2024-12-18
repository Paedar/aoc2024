package dev.paedar.aoc.lvl17.opcode;

import dev.paedar.aoc.lvl17.Computer;

public final class BXC implements OpCode {

    public static final BXC INSTANCE = new BXC();

    @Override
    public void execute(Computer computer) {
        computer.advance(); // Legacy reasons

        long b = computer.getRegisterB();
        long c = computer.getRegisterC();

        long result = b ^ c;

        // System.out.println("BXC B = b(%d) ^ c(%d) = %d".formatted(b, c, result));
        computer.setRegisterB(result);
    }

}
