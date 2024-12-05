package dev.paedar.aoc.lvl05;

import dev.paedar.aoc.util.InputReader;
import org.junit.jupiter.api.Test;

import static dev.paedar.aoc.lvl05.AocLvl05.readPrintRuns;
import static dev.paedar.aoc.lvl05.AocLvl05.readRules;
import static org.junit.jupiter.api.Assertions.assertEquals;

class Input05Test {

    @Test
    void sumMiddlePageNumberOfCorrectPrintRunsInput() {
        var lines = InputReader.readLines("input_05.txt");
        var rules = readRules(lines);
        var printRuns = readPrintRuns(lines);

        var expected = 5129;
        var actual = AocLvl05.sumMiddlePageNumberOfCorrectPrintRuns(rules, printRuns);

        assertEquals(expected, actual);
    }

    @Test
    void sumMiddlePageNumberOfCorrectedPrintRunsInput() {
        var lines = InputReader.readLines("input_05.txt");
        var rules = readRules(lines);
        var printRuns = readPrintRuns(lines);

        var expected = 4077;
        var actual = AocLvl05.sumMiddlePageNumberOfCorrectedPrintRuns(rules, printRuns);

        assertEquals(expected, actual);
    }
}
