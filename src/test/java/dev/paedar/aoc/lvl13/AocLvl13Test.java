package dev.paedar.aoc.lvl13;

import dev.paedar.aoc.util.InputReader;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class AocLvl13Test {

    @Test
    void costToWinMostMachinesExample() {
        var inputTokens = InputReader.readTokens("example_13.txt");

        var actual = AocLvl13.costToWinMostMachines(inputTokens);
        var expected = 480;
        assertEquals(expected, actual);
    }

    @Test
    void costToWinMostMachinesWithOffsetExample() {
        var inputTokens = InputReader.readTokens("example_13.txt");

        var actual = AocLvl13.costToWinMostMachinesWithOffset(inputTokens);
        var expected = 875318608908L;
        assertEquals(expected, actual);
    }

}