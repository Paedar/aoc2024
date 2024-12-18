package dev.paedar.aoc.lvl17.opcode;

import dev.paedar.aoc.lvl17.Computer;

public sealed interface OpCode permits LiteralConsumer, ComboConsumer, BXC {

    void execute(Computer computer);

    static OpCode of(int op) {
        return switch (op) {
            case 0 -> ADV.INSTANCE;
            case 1 -> BXL.INSTANCE;
            case 2 -> BST.INSTANCE;
            case 3 -> JNZ.INSTANCE;
            case 4 -> BXC.INSTANCE;
            case 5 -> OUT.INSTANCE;
            case 6 -> BDV.INSTANCE;
            case 7 -> CDV.INSTANCE;
            default -> throw new IllegalArgumentException("Invalid op code: " + op);
        };
    }

    static int mod8(int compoOperand) {
        return compoOperand & 7; /* Faster modulus calculation possible for mod(2^3) */
    }

}
