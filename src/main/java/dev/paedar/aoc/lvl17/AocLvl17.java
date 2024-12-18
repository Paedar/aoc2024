package dev.paedar.aoc.lvl17;

import dev.paedar.aoc.util.InputReader;

import java.util.List;

public class AocLvl17 {
    public static void main(String[] args) {
        var lines = InputReader.readLines("input_17.txt");

        var output = executeComputerProgram(lines);
        System.out.println("Output: " + output);
    }

    public static String executeComputerProgram(List<String> lines) {
        var computer = Computer.ofInput(lines);
        return computer.executeProgram();
    }

}
