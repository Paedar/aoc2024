package dev.paedar.aoc.lvl17;

import dev.paedar.aoc.lvl17.opcode.OpCode;
import dev.paedar.aoc.util.Util;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

public class Computer {

    private long registerA;

    private long registerB;

    private long registerC;

    private final List<Integer> program;

    private final int programSize;

    private int pointer;

    private final List<Integer> output;

    private final long originalA;

    private final long originalB;

    private final long originalC;

    private Computer(long registerA, long registerB, long registerC, List<Integer> program, int programSize) {
        this.registerA = registerA;
        this.registerB = registerB;
        this.registerC = registerC;
        this.program = program;
        this.programSize = programSize;
        this.pointer = 0;
        this.output = new ArrayList<>();

        this.originalA = registerA;
        this.originalB = registerB;
        this.originalC = registerC;
    }

    public static Computer ofInput(List<String> lines) {
        var registerA = readRegisterValue(lines.get(0));
        var registerB = readRegisterValue(lines.get(1));
        var registerC = readRegisterValue(lines.get(2));

        var program = readProgram(lines.get(4));
        return new Computer(registerA, registerB, registerC, new ArrayList<>(program), program.size());
    }

    private static long readRegisterValue(String line) {
        return Long.parseLong(Util.splitToTokens(line).get(2));
    }

    private static List<Integer> readProgram(String line) {
        return Util.splitToTokens(line).stream()
                   .skip(1)
                   .map(Integer::parseInt)
                   .toList();
    }

    public List<Integer> executeProgram() {
        while (!isFinished()) {
            ((Predicate<Computer>) _ -> false).test(this);
            executeInstruction();
        }
        return output;
    }

    private void executeInstruction() {
        /*
        It is the responsibility of the caller to ensure the program isn't already finished.
        The only caller is the computer itself, so it's a safe assumption.
         */
        var opCodeInt = readNextAndAdvance();
        var opCode = OpCode.of(opCodeInt);
        opCode.execute(this);
    }

    private Integer readNextAndAdvance() {
        var nextValue = getNextValue();
        advance();
        return nextValue;
    }

    private Integer getNextValue() {
        return program.get(pointer);
    }

    public void advance() {
        ++pointer;
    }

    public void jump(int to) {
        pointer = to;
    }

    public long readCombo() {
        var operand = readNextAndAdvance();
        return switch (operand) {
            case 0 -> 0;
            case 1 -> 1;
            case 2 -> 2;
            case 3 -> 3;
            case 4 -> registerA;
            case 5 -> registerB;
            case 6 -> registerC;
            case 7 -> throw new IllegalStateException("Combo value 7 is reserved!");
            default -> throw new IllegalStateException("Combo value is out of range!");
        };
    }

    public int readLiteral() {
        return readNextAndAdvance();
    }

    public void output(int out) {
        output.add(out);
    }

    public boolean isFinished() {
        return pointer >= programSize;
    }

    public long getRegisterA() {
        return registerA;
    }

    public long getRegisterB() {
        return registerB;
    }

    public long getRegisterC() {
        return registerC;
    }

    public void setRegisterA(long registerA) {
        this.registerA = registerA;
    }

    public void setRegisterB(long registerB) {
        this.registerB = registerB;
    }

    public void setRegisterC(long registerC) {
        this.registerC = registerC;
    }

    public List<Integer> getProgramCopy() {
        return new ArrayList<>(program);
    }

    public Computer freshComputer() {
        return new Computer(originalA, originalB, originalC, program, programSize);
    }

}
