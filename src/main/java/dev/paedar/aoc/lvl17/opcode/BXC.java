package dev.paedar.aoc.lvl17.opcode;

import dev.paedar.aoc.lvl17.Computer;

public final class BXC implements OpCode {

    @Override
    public void execute(Computer computer) {
        computer.advance(); // Legacy reasons

        var b = computer.getRegisterB();
        var c = computer.getRegisterC();

        var result = b ^ c;
        computer.setRegisterB(result);
    }

}
