package dev.paedar.aoc.lvl07;

import dev.paedar.aoc.util.InputReader;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class AocLvl07Test {

    @Test
    void testSumSolvableEquationsExample() {
        var lines = InputReader.readLines("example_07.txt");

        var expected = 3749L;
        var actual = AocLvl07.sumSolvableEquations(lines);

        assertEquals(expected, actual);
    }

    @Test
    void testSumSolvableEquationsWithConcatenationOperatorExample() {
        var lines = InputReader.readLines("example_07.txt");

        var expected = 11387;
        var actual = AocLvl07.sumSolvableEquationsWithConcatenationOperator(lines);

        assertEquals(expected, actual);
    }
}
