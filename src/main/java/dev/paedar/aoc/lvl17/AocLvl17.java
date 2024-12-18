package dev.paedar.aoc.lvl17;

import dev.paedar.aoc.util.InputReader;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

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

    public static int findLowestRegistryAValueToSelfReplicate(List<String> lines) {
        var computer = Computer.ofInput(lines);
        var program = computer.getProgramCopy();

        return IntStream.range(0, Integer.MAX_VALUE)
//                        .parallel()
                        .filter(i -> {
                            var localComputer = computer.freshComputer();
                            localComputer.setRegisterA(i);
                            var output = localComputer.executeExpectingSelfReplication();
                            return output.equals(program);
                        })
                        .findFirst()
                        .orElseThrow(() -> new RuntimeException("No self replicating value for registry A found."));
    }

}
