package dev.paedar.aoc.lvl17.opcode;

import dev.paedar.aoc.lvl17.Computer;

public final class BXL extends LiteralConsumer {

    public static final BXL INSTANCE = new BXL();

    @Override
    void execute(long literalOperand, Computer computer) {
        long b = computer.getRegisterB();

        var result = literalOperand ^ b;
        // System.out.println("BXL B = b(%d) ^ l(%d) = %d".formatted(b, literalOperand, result));
        computer.setRegisterB(result);
    }

}
