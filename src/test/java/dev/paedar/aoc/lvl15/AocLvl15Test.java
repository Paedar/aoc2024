package dev.paedar.aoc.lvl15;

import dev.paedar.aoc.util.InputReader;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class AocLvl15Test {

    @Test
    void sumGpsAfterRobotMovementExampleSmall() {
        var lines = InputReader.readLines("example_15_small.txt");

        var actual = AocLvl15.sumGpsAfterRobotMovement(lines);
        var expected = 2028;

        assertEquals(expected, actual);
    }

    @Test
    void sumGpsAfterRobotMovementExampleLarge() {
        var lines = InputReader.readLines("example_15_large.txt");

        var actual = AocLvl15.sumGpsAfterRobotMovement(lines);
        var expected = 10092;

        assertEquals(expected, actual);
    }

}