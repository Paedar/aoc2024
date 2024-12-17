package dev.paedar.aoc.lvl15;

import dev.paedar.aoc.util.InputReader;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

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

    @Test
    void largeWarehouseSumGpsAfterRobotMovementExample() {
        var lines = InputReader.readLines("example_15_part2.txt");

        var actual = AocLvl15.largeWarehouseSumGpsAfterRobotMovement(lines);
        var expected = 618; /* Empirically found, assignment doesn't give a value */

        assertEquals(expected, actual);
    }

    @Test
    void largeWarehouseSumGpsAfterRobotMovementLargeExample() {
        var lines = InputReader.readLines("example_15_large.txt");

        var actual = AocLvl15.largeWarehouseSumGpsAfterRobotMovement(lines);
        var expected = 9021;

        assertEquals(expected, actual);
    }

}