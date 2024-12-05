package dev.paedar.aoc.lvl05;

import dev.paedar.aoc.util.InputReader;

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
