package dev.paedar.aoc.lvl05;

import dev.paedar.aoc.util.InputReader;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class AocLvl05 {

    public static void main(String[] args) {
        var lines = InputReader.readLines("input_05.txt");
        var rules = readRules(lines);
        var printRuns = readPrintRuns(lines);

        var middlePageOfCorrectPrintRunsSum = sumMiddlePageNumberOfCorrectPrintRuns(rules, printRuns);
        System.out.println("Sum of middle pages of correct print runs: " + middlePageOfCorrectPrintRunsSum);

        var middlePageOfCorrectedPrintRunsSum = sumMiddlePageNumberOfCorrectedPrintRuns(rules, printRuns);
        System.out.println("Sum of middle pages of corrected print runs: " + middlePageOfCorrectedPrintRunsSum);
    }

    public static int sumMiddlePageNumberOfCorrectedPrintRuns(Set<Rule> rules, Set<List<Integer>> printRuns) {
        return printRuns.stream()
                        .filter(it -> !isValidPrintRun(it, rules))
                        .map(it -> correctAccordingTo(it, rules))
                        .mapToInt(AocLvl05::toMiddlePage)
                        .sum();
    }

    private static List<Integer> correctAccordingTo(List<Integer> printRun, Set<Rule> rules) {
        var applicableRules = rules.stream()
                                   .filter(rule -> rule.appliesTo(printRun))
                                   .collect(Collectors.toUnmodifiableSet());

        /*
        Let's assert that the print run is actually uniquely correctable.
         */
        var sortedNumbersInRules = applicableRules.stream()
                                                  .flatMap(it -> Stream.of(it.first(), it.second()))
                                                  .distinct()
                                                  .sorted()
                                                  .toList();

        if (!sortedNumbersInRules.equals(printRun.stream().sorted().toList())) {
            throw new IllegalArgumentException("I don't think there's a correct solution to this problem.");
        }

        /*
        Algorithm:
        Swap all numbers according to violated rules, until no rules are violated.
        If the end result would violate any rules, it would mean there is a circular dependency in the rules and there would not be any valid solution.
        Note: It could also be possible that the applicable rules form multiple unconnected graphs. If that were the case, there would not be a
        uniquely definable solution to this problem either. Let's assume this is not the case.
         */
        var corrected = new ArrayList<>(printRun);
        var violations = applicableRules.stream()
                                        .filter(rule -> !rule.validateOn(corrected))
                                        .toList();
        while (!violations.isEmpty()) {
            /*
            Note: it is unclear whether or not performing the swaps of all current violations is faster than simply applying a single swap.
            Since there can't be any cyclic rules (since that would lead to no possible solutions),
            either way leads to eventually finding the solution
             */
            var violation = violations.getFirst();
            // Perform the actual swap
            var firstIndex = corrected.indexOf(violation.first());
            var secondIndex = corrected.indexOf(violation.second());
            Collections.swap(corrected, firstIndex, secondIndex);

            /*
            We do determine all violations again repeatedly. Our swap could have violated rules that weren't violated before!
             */
            violations = applicableRules.stream()
                                        .filter(rule -> !rule.validateOn(corrected))
                                        .toList();
        }

        return corrected;
    }

    public static int sumMiddlePageNumberOfCorrectPrintRuns(Set<Rule> rules, Set<List<Integer>> printRuns) {
        return printRuns.stream()
                        .filter(it -> isValidPrintRun(it, rules))
                        .mapToInt(AocLvl05::toMiddlePage)
                        .sum();
    }

    private static boolean isValidPrintRun(List<Integer> printRun, Set<Rule> rules) {
        return rules.stream()
                    .filter(rule -> rule.appliesTo(printRun))
                    .allMatch(rule -> rule.validateOn(printRun));
    }

    private static int toMiddlePage(List<Integer> printRun) {
        var pageCount = printRun.size();
        if (pageCount % 2 == 0) {
            throw new IllegalArgumentException("Middle page can't be determined for even amount of pages.");
        }

        return printRun.get(pageCount / 2);
    }

    public static Set<List<Integer>> readPrintRuns(List<String> lines) {
        return lines.stream()
                    .filter(it -> it.contains(","))
                    .map(AocLvl05::toPrintRun)
                    .collect(Collectors.toUnmodifiableSet());
    }

    private static List<Integer> toPrintRun(String line) {
        return Stream.of(line.split(","))
                     .map(Integer::parseInt)
                     .toList();
    }

    public static Set<Rule> readRules(List<String> lines) {
        return lines.stream()
                    .filter(it -> it.contains("|"))
                    .map(AocLvl05::toRule)
                    .collect(Collectors.toUnmodifiableSet());
    }

    private static Rule toRule(String line) {
        var split = line.split("\\|");
        if (split.length != 2) {
            throw new IllegalArgumentException("Invalid rule: " + line);
        }
        return new Rule(Integer.parseInt(split[0]), Integer.parseInt(split[1]));
    }

}
