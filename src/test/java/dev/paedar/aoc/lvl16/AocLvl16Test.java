package dev.paedar.aoc.lvl16;

import dev.paedar.aoc.util.InputReader;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class AocLvl16Test {

    @Test
    void calculateCheapestPathCostExampleA() {
        var lines = InputReader.readLines("example_16a.txt");

        var actual = AocLvl16.calculateCheapestPathCost(lines);
        var expected = 7036L;

        assertEquals(expected, actual);
    }

    @Test
    void calculateCheapestPathCostExampleB() {
        var lines = InputReader.readLines("example_16b.txt");

        var actual = AocLvl16.calculateCheapestPathCost(lines);
        var expected = 11048L;

        assertEquals(expected, actual);
    }
    @Test
    void countPositionsAlongCheapestPathsExampleA() {
        var lines = InputReader.readLines("example_16a.txt");

        var actual = AocLvl16.countPositionsAlongCheapestPaths(lines);
        var expected = 45L;

        assertEquals(expected, actual);
    }

    @Test
    void countPositionsAlongCheapestPathsExampleB() {
        var lines = InputReader.readLines("example_16b.txt");

        var actual = AocLvl16.countPositionsAlongCheapestPaths(lines);
        var expected = 64L;

        assertEquals(expected, actual);
    }

}