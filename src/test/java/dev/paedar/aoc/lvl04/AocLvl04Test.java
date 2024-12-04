package dev.paedar.aoc.lvl04;

import dev.paedar.aoc.util.InputReader;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class AocLvl04Test {

    @Test
    void countWordsExample() {
        var lines = InputReader.readLines("example_04.txt");

        var expected = 18;
        var actual = AocLvl04.countWords("XMAS", lines);

        assertEquals(expected, actual);
    }

    @Test
    void countWordsInput() {
        var lines = InputReader.readLines("input_04.txt");

        var expected = 2571;
        var actual = AocLvl04.countWords("XMAS", lines);

        assertEquals(expected, actual);
    }
    @Test
    void countCrossWordsExample() {
        var lines = InputReader.readLines("example_04.txt");

        var expected = 9;
        var actual = AocLvl04.countCrossWords("MAS", lines);

        assertEquals(expected, actual);
    }

    @Test
    void countCrossWordsInput() {
        var lines = InputReader.readLines("input_04.txt");

        var expected = 1992;
        var actual = AocLvl04.countCrossWords("MAS", lines);

        assertEquals(expected, actual);
    }

}