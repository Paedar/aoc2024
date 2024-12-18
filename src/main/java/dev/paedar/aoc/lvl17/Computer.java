package dev.paedar.aoc.lvl17;

import dev.paedar.aoc.lvl17.opcode.OpCode;
import dev.paedar.aoc.util.Util;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.function.Predicate;

import static java.util.function.Predicate.not;

public class Computer {

    public static final Predicate<Computer> LAST_OUTPUT_DOES_NOT_MATCH_SAME_PROGRAM_INDEX = not(Computer::lastOutputMatchesSameProgramIndex);

    private int registerA;

    private int registerB;

    private int registerC;

    private final List<Integer> program;

    private final int programSize;

    private int pointer;

    private final List<Integer> output;

    private final int originalA;

    private final int originalB;

    private final int originalC;

    private boolean outputIsDirty;

    private Computer(int registerA, int registerB, int registerC, List<Integer> program, int programSize) {
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

        this.outputIsDirty = false;
    }

    public static Computer ofInput(List<String> lines) {
        var registerA = readRegisterValue(lines.get(0));
        var registerB = readRegisterValue(lines.get(1));
        var registerC = readRegisterValue(lines.get(2));

        var program = readProgram(lines.get(4));
        return new Computer(registerA, registerB, registerC, new ArrayList<>(program), program.size());
    }

    private static int readRegisterValue(String line) {
        return Integer.parseInt(Util.splitToTokens(line).get(2));
    }

    private static List<Integer> readProgram(String line) {
        return Util.splitToTokens(line).stream()
                   .skip(1)
                   .map(Integer::parseInt)
                   .toList();
    }

    public List<Integer> executeExpectingSelfReplication() {
        /*
        Apply early exit for the speeds
         */
        return executeProgram(LAST_OUTPUT_DOES_NOT_MATCH_SAME_PROGRAM_INDEX);
    }

    private boolean lastOutputMatchesSameProgramIndex() {
        if (outputIsDirty) {
            var outputSize = output.size();
            return !(outputSize > programSize || !unsafeLastOutputMatchesSameProgramIndex(outputSize));
        } else {
            return true;
        }
    }

    private boolean unsafeLastOutputMatchesSameProgramIndex(int outputSize) {
        return program.get(outputSize - 1).intValue() == output.getLast().intValue();
    }

    private List<Integer> executeProgram(Predicate<Computer> additionalBreakingCondition) {
        while (!isFinished() && !additionalBreakingCondition.test(this)) {
            executeInstruction();
        }
        return output;
    }

    public List<Integer> executeProgram() {
        return executeProgram(_ -> false);
    }

    private void executeInstruction() {
        /*
        It is the responsibility of the caller to ensure the program isn't already finished.
        The only caller is the computer itself, so it's a safe assumption.
         */
        var opCodeInt = readNextAndAdvance();
        var opCode = OpCode.of(opCodeInt);
        outputIsDirty = false;
        opCode.execute(this);
    }

    private Integer readNextAndAdvance() {
        var nextValue = program.get(pointer);
        advance();
        return nextValue;
    }

    public void reset() {
        pointer = 0;
        output.clear();
        registerA = originalA;
        registerB = originalB;
        registerC = originalC;

        outputIsDirty = false;
    }

    public void advance() {
        ++pointer;
    }

    public void jump(int to) {
        pointer = to;
    }

    public int readCombo() {
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
        outputIsDirty = true;
    }

    public boolean isFinished() {
        return pointer >= programSize;
    }

    public int getRegisterA() {
        return registerA;
    }

    public int getRegisterB() {
        return registerB;
    }

    public int getRegisterC() {
        return registerC;
    }

    public void setRegisterA(int registerA) {
        this.registerA = registerA;
    }

    public void setRegisterB(int registerB) {
        this.registerB = registerB;
    }

    public void setRegisterC(int registerC) {
        this.registerC = registerC;
    }

    public List<Integer> getProgramCopy() {
        return new ArrayList<>(program);
    }

    public Computer freshComputer() {
        return new Computer(originalA, originalB, originalC, program, programSize);
    }

}
