package dev.paedar.aoc.util;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class InputReaderTest {

    @Test
    void readExampleFileContent() {
        var expected = """
                       3   4
                       4   3
                       2   5
                       1   3
                       3   9
                       3   3""";

        var actual = InputReader.readContent("example_01.txt");

        assertEquals(expected, actual);
    }

    @Test
    void readExampleFileLines() {
        var expected = List.of("3   4",
                               "4   3",
                               "2   5",
                               "1   3",
                               "3   9",
                               "3   3");

        var actual = InputReader.readLines("example_01.txt");

        assertEquals(expected, actual);
    }

    @Test
    void readExampleFileTokens() {
        var expected = List.of("3", "4",
                               "4", "3",
                               "2", "5",
                               "1", "3",
                               "3", "9",
                               "3", "3");

        var actual = InputReader.readTokens("example_01.txt");

        assertEquals(expected, actual);
    }
}