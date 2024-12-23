package dev.paedar.aoc.lvl02;

import dev.paedar.aoc.util.InputReader;
import org.junit.jupiter.api.Test;

import static dev.paedar.aoc.lvl02.AocLvl02.toReports;
import static org.junit.jupiter.api.Assertions.assertEquals;

class Example02Test {

    @Test
    void countSafeReportsExample() {
        var expected = 2;
        var lines = InputReader.readLines("example_02.txt");
        var levelsReport = toReports(lines);

        var actual = AocLvl02.countSafeReports(levelsReport);
        assertEquals(expected, actual);
    }
    @Test
    void countSafeDampenedReportsExample() {
        var expected = 4;
        var lines = InputReader.readLines("example_02.txt");
        var levelsReport = toReports(lines);

        var actual = AocLvl02.countSafeDampenedReports(levelsReport);
        assertEquals(expected, actual);
    }


}