package dev.paedar.aoc.lvl19;

import dev.paedar.aoc.util.InputReader;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class AocLvl19Test {

    @Test
    void countPossibleDesignsExample() {
        var lines = InputReader.readLines("example_19.txt");

        var actual = AocLvl19.countPossibleTowelDesigns(lines);
        var expected = 6L;

        assertEquals(expected, actual);
    }

    @Test
    void countAllPossibleTowelDesignsForAllPatternsExample() {
        var lines = InputReader.readLines("example_19.txt");

        var actual = AocLvl19.countAllPossibleTowelDesignsForAllPatterns(lines);
        var expected = 16L;

        assertEquals(expected, actual);
    }

}