package dev.paedar.aoc.lvl17.opcode;

import dev.paedar.aoc.lvl17.Computer;

public sealed interface OpCode permits LiteralConsumer, ComboConsumer, BXC {

    void execute(Computer computer);

    static OpCode of(int op) {
        return switch (op) {
            case 0 -> new ADV();
            case 1 -> new BXL();
            case 2 -> new BST();
            case 3 -> new JNZ();
            case 4 -> new BXC();
            case 5 -> new OUT();
            case 6 -> new BDV();
            case 7 -> new CDV();
            default -> throw new IllegalArgumentException("Invalid op code: " + op);
        };
    }

    static int mod8(int compoOperand) {
        return compoOperand & 7; /* Faster modulus calculation possible for mod(2^3) */
    }
}
