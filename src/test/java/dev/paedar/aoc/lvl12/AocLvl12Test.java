package dev.paedar.aoc.lvl12;

import dev.paedar.aoc.util.InputReader;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class AocLvl12Test {

    @Test
    void testSumAreaCostExampleSimple() {
        var lines = InputReader.readLines("example_12a.txt");

        var actual = AocLvl12.sumCostOfAreas(lines);
        var expected = 140;

        assertEquals(expected, actual);
    }

    @Test
    void testSumAreaCostExampleOandX() {
        var lines = InputReader.readLines("example_12b.txt");

        var actual = AocLvl12.sumCostOfAreas(lines);
        var expected = 772;

        assertEquals(expected, actual);
    }

    @Test
    void testSumAreaCostExample() {
        var lines = InputReader.readLines("example_12.txt");

        var actual = AocLvl12.sumCostOfAreas(lines);
        var expected = 1930;

        assertEquals(expected, actual);
    }
}