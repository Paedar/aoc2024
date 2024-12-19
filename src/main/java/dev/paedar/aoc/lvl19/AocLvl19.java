package dev.paedar.aoc.lvl19;

import dev.paedar.aoc.util.InputReader;
import dev.paedar.aoc.util.Util;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AocLvl19 {

    public static void main(String[] args) {
        var lines = InputReader.readLines("input_19.txt");

        var possiblePatternCount = countPossibleTowelPatterns(lines);
        System.out.println("Possible towel patterns: " + possiblePatternCount);
    }

    public static long countPossibleTowelPatterns(List<String> lines) {
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

        return desiredTowelPatterns.stream().filter(towel -> isPossible(towel, availablePatterns, memoizedPatterns)).count();
    }

    private static boolean isPossible(String desiredTowelPattern, List<String> availablePatterns, Map<String, Boolean> memoizedPatterns) {
        if (memoizedPatterns.containsKey(desiredTowelPattern)) {
            return memoizedPatterns.get(desiredTowelPattern);
        }

        var desiredPatternLength = desiredTowelPattern.length();
        var isPossible = availablePatterns.stream()
                                          .filter(desiredTowelPattern::startsWith)
                                          .anyMatch(p -> isPossible(desiredTowelPattern.substring(p.length(), desiredPatternLength), availablePatterns, memoizedPatterns));
        memoizedPatterns.put(desiredTowelPattern, isPossible);
        return isPossible;
    }

}
