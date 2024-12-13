package dev.paedar.aoc.lvl13;

import dev.paedar.aoc.util.InputReader;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Gatherers;

public class AocLvl13 {

    public static void main(String[] args) {
        var inputTokens = InputReader.readTokens("input_13.txt");

        var costToWinMost = costToWinMostMachines(inputTokens);
        System.out.println("Cost win all possible machines: " + costToWinMost);

        var costToWinMostWithOffset = costToWinMostMachinesWithOffset(inputTokens);
        System.out.println("Cost win all possible machines, applying offset on the prize location: " + costToWinMostWithOffset);
    }

    public static long costToWinMostMachines(List<String> inputTokens) {
        Function<List<String>, ClawMachine> clawMachineReader = ClawMachine::ofTokens;
        return calculateCostForWinningMachines(inputTokens, clawMachineReader);
    }

    public static long costToWinMostMachinesWithOffset(List<String> inputTokens) {
        return calculateCostForWinningMachines(inputTokens, t -> ClawMachine.ofTokens(t, 10000000000000L));
    }

    private static long calculateCostForWinningMachines(List<String> inputTokens, Function<List<String>, ClawMachine> clawMachineReader) {
        return inputTokens.stream()
                          .gather(Gatherers.windowFixed(ClawMachine.CLAW_MACHINE_DESCRIPTOR_TOKEN_COUNT))
                          .map(clawMachineReader)
                          .map(cm -> cm.costToWin(3, 1))
                          .mapToLong(optionalCost -> optionalCost.orElse(0L))
                          .sum();
    }

}
