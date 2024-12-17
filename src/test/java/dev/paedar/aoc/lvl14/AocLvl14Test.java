package dev.paedar.aoc.lvl14;

import dev.paedar.aoc.util.InputReader;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class AocLvl14Test {

    @Test
    void determineSafetyFactorExample() {
        var lines = InputReader.readLines("example_14.txt");

        var actual = AocLvl14.determineSafetyFactor(lines, 11, 7, 100);
        var expected = 12;

        assertEquals(expected, actual);
    }

    @Test
    void secondsToChristmasTreeExample() {
        var lines = InputReader.readLines("example_14.txt");

        var actual = AocLvl14.secondsToChristmasTree(lines, 11, 7);
        var expected = 6398; // There is no christmas tree in the example, but this is what a test would look like.

        assertEquals(expected, actual);
    }

}