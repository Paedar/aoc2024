package dev.paedar.aoc.lvl10;

import dev.paedar.aoc.util.InputReader;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class AocLvl10Test {

    @Test
    void testSumOfTrailheadScoresExample() {
        var lines = InputReader.readLines("example_10.txt");

        var actual = AocLvl10.sumOfTrailheadScores(lines);
        var expected = 36;

        assertEquals(expected, actual);
    }

    @Test
    void testSumOfTrailheadRatingsExample() {
        var lines = InputReader.readLines("example_10.txt");

        var actual = AocLvl10.sumOfTrailheadRatings(lines);
        var expected = 81;

        assertEquals(expected, actual);
    }
}