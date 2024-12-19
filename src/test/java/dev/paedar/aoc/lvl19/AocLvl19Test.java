package dev.paedar.aoc.lvl19;

import dev.paedar.aoc.util.InputReader;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class AocLvl19Test {

    @Test
    void countPossiblePatternsExample() {
        var lines = InputReader.readLines("example_19.txt");

        var actual = AocLvl19.countPossibleTowelPatterns(lines);
        var expected = 6L;

        assertEquals(expected, actual);
    }

}