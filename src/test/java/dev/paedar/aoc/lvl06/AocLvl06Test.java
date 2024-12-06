package dev.paedar.aoc.lvl06;

import dev.paedar.aoc.util.InputReader;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class AocLvl06Test {

    @Test
    void countGuardPathPositionsExample() {
        var lines = InputReader.readLines("example_06.txt");

        var expected = 41;
        var actual = AocLvl06.countGuardPathPositions(lines);

        assertEquals(expected, actual);
    }
}
