package dev.paedar.aoc.lvl19;

import dev.paedar.aoc.util.InputReader;
import dev.paedar.aoc.util.Util;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AocLvl19 {

    public static void main(String[] args) {
        var lines = InputReader.readLines("input_19.txt");

        var possiblePatternCount = countPossibleTowelDesigns(lines);
        System.out.println("Possible towel patterns: " + possiblePatternCount);

        var combinationCount = countAllPossibleTowelDesignsForAllPatterns(lines);
        System.out.println("Possible combinations that lead to a valid towel pattern: " + combinationCount);
    }

    public static long countPossibleTowelDesigns(List<String> lines) {
        var availablePatterns = Util.splitToTokens(lines.getFirst());
        /*
        Skip over empty line in input
         */
        var desiredTowelPatterns = lines.subList(2, lines.size());

        /*
        Since we're writing a recursive solution, we apply memoization to reduce the amount of recursive calls
        We can initialize the memoization with the available patterns in this case
         */
        Map<String, Boolean> memoizedPatterns = new HashMap<>();
        availablePatterns.forEach(availablePattern -> memoizedPatterns.put(availablePattern, true));

        return desiredTowelPatterns.stream()
                                   .filter(towel -> isPossible(towel, availablePatterns, memoizedPatterns))
                                   .count();
    }

    public static long countAllPossibleTowelDesignsForAllPatterns(List<String> lines) {
        var availablePatterns = Util.splitToTokens(lines.getFirst());
        /*
        Skip over empty line in input
         */
        var desiredTowelPatterns = lines.subList(2, lines.size());

        /*
        Since we're writing a recursive solution, we apply memoization to reduce the amount of recursive calls
        We cannot initialize the memoization with the available patterns in this case, because an available pattern may be composed out of other patterns
        In the example, this is true: the patterns 'rb' and 'br' can be composed using 'r' and 'b'.
         */
        Map<String, Long> memoizedDesignCounts = new HashMap<>();

        return desiredTowelPatterns.stream()
                                   .mapToLong(towel -> countPossible(towel, availablePatterns, memoizedDesignCounts))
                                   .sum();
    }

    private static boolean isPossible(String desiredTowelPattern, List<String> availablePatterns, Map<String, Boolean> memoizedPatterns) {
        if (memoizedPatterns.containsKey(desiredTowelPattern)) {
            return memoizedPatterns.get(desiredTowelPattern);
        }

        var isPossible = availablePatterns.stream()
                                          .filter(desiredTowelPattern::startsWith)
                                          .anyMatch(p -> isPossible(desiredTowelPattern.substring(p.length()), availablePatterns, memoizedPatterns));
        memoizedPatterns.put(desiredTowelPattern, isPossible);
        return isPossible;
    }

    private static long countPossible(String desiredTowelPattern, List<String> availablePatterns, Map<String, Long> memoizedCounts) {
        if (memoizedCounts.containsKey(desiredTowelPattern)) {
            return memoizedCounts.get(desiredTowelPattern);
        }

        long calculated;
        if (desiredTowelPattern.isEmpty()) {
            calculated = 1L;
        } else {
            calculated = availablePatterns.stream()
                                              .filter(desiredTowelPattern::startsWith)
                                              .mapToLong(p -> countPossible(desiredTowelPattern.substring(p.length()), availablePatterns, memoizedCounts))
                                              .sum();
        }
        memoizedCounts.put(desiredTowelPattern, calculated);
        return calculated;
    }

}
