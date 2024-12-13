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

    @Test
    void readTokensInFileWithEmptyLinesAndWeirdChars() {
        var expected = List.of(
        "Button", "A", "X", "94", "Y", "34",
        "Button", "B", "X", "22", "Y", "67",
        "Prize", "X", "8400", "Y", "5400",
        "Button", "A", "X", "26", "Y", "66",
        "Button", "B", "X", "67", "Y", "21",
        "Prize", "X", "12748", "Y", "12176",
        "Button", "A", "X", "17", "Y", "86",
        "Button", "B", "X", "84", "Y", "37",
        "Prize", "X", "7870", "Y", "6450",
        "Button", "A", "X", "69", "Y", "23",
        "Button", "B", "X", "27", "Y", "71",
        "Prize", "X", "18641", "Y", "10279"
        );

        var actual = InputReader.readTokens("example_13.txt");

        assertEquals(expected, actual);
    }
}