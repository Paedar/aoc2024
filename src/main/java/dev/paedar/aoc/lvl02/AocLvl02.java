package dev.paedar.aoc.lvl02;

import dev.paedar.aoc.util.InputReader;
import dev.paedar.aoc.util.Util;

import java.util.List;
import java.util.stream.Gatherers;

public class AocLvl02 {

    public static void main(String[] args) {
        var lines = InputReader.readLines("input_02.txt");
        var levelsReport = toReports(lines);

        var safeReports = countSafeReports(levelsReport);
        System.out.println(safeReports);
    }

    public static List<List<Integer>> toReports(List<String> lines) {
        return lines.stream()
                    .map(Util::splitToTokens)
                    .map(it -> it.stream()
                                 .map(Integer::parseInt)
                                 .toList())
                    .toList();
    }

    public static long countSafeReports(List<List<Integer>> levelsReport) {
        return levelsReport.stream()
                           .filter(AocLvl02::isSafeReport)
                           .count();
    }

    public static boolean isSafeReport(List<Integer> report) {
        var differences = report.stream()
                                .gather(Gatherers.windowSliding(2))
                                .map(AocLvl02::getDifference)
                                .toList();

        var allIncreasing = differences.stream()
                                       .allMatch(it -> it < 0);
        var allDecreasing = differences.stream()
                                       .allMatch(it -> it > 0);
        var allBoundedIncreaseOrDecrease = differences.stream()
                                                      .map(it -> Math.abs(it))
                                                      .allMatch(it -> it >= 1 && it <= 3);
        return allBoundedIncreaseOrDecrease && (allIncreasing || allDecreasing);
    }

    private static int getDifference(List<Integer> integers) {
        if (integers.size() != 2) {
            throw new IllegalArgumentException();
        }
        return integers.get(0) - integers.get(1);
    }

}
