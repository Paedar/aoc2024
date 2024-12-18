package dev.paedar.aoc.lvl17;

import dev.paedar.aoc.util.InputReader;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.LongStream;
import java.util.stream.Stream;

public class AocLvl17 {

    public static void main(String[] args) {
        var lines = InputReader.readLines("input_17.txt");

        var output = executeComputerProgram(lines);
         System.out.println("Output: " + output);

        var registryA = findLowestRegistryAValueToSelfReplicate(lines);
         System.out.println("Lowest registry A value for self replication: " + registryA);
    }

    public static String executeComputerProgram(List<String> lines) {
        var computer = Computer.ofInput(lines);
        return asString(computer.executeProgram());
    }

    private static String asString(List<Integer> intList) {
        return intList.stream().map(String::valueOf).collect(Collectors.joining(","));
    }

    public static long findLowestRegistryAValueToSelfReplicate(List<String> lines) {
        var computer = Computer.ofInput(lines);
        var program = computer.getProgramCopy();

        /*
        From observing program execution behavior:
        - Length of the output grows every power of 8
        - The end of the output seems to move towards consistent behavior
        So we start our search with a small A value, up to 8. For each which the output matches the end of our program,
        we shift a 3 bits to the left and try another set of 8 candidates from there.
         */

        Set<Long> candidates = new HashSet<>();
        Set<Long> solutions = new HashSet<>();
        candidates.add(0L);

        while (solutions.isEmpty()) {
            candidates = candidates.stream()
                                   .flatMap(aCandidate -> findCandidates(aCandidate, computer, solutions))
                                   .collect(Collectors.toSet());
        }

        return solutions.stream()
                        .mapToLong(i -> i)
                        .min()
                        .orElseThrow(() -> new IllegalStateException("No Solution found"));
    }

    private static Stream<Long> findCandidates(long candidate, Computer computer, Set<Long> solutions) {
        var expectedOutput = computer.getProgramCopy();
        var toTheLeft = candidate << 3;
        return LongStream.range(0, 8)
                         .map(n -> n + toTheLeft)
                         .filter(i -> {
                                     var localComputer = computer.freshComputer();
                                     localComputer.setRegisterA(i);
                                     // System.out.println("================");
                                     // System.out.println("Register A start value: " + i);
                                     var output = localComputer.executeProgram();
                                     // System.out.println("Output: " + output);
                                     var outputMatchesEndOfProgram = output.equals(expectedOutput.reversed().subList(0, output.size()).reversed());
                                     if (outputMatchesEndOfProgram && output.size() == expectedOutput.size()) {
                                         solutions.add(i);
                                     }
                                     return outputMatchesEndOfProgram;
                                 }
                         )
                         .boxed();
    }

}
