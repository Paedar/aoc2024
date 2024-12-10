package dev.paedar.aoc.lvl09;

import dev.paedar.aoc.lvl08.AocLvl08;
import dev.paedar.aoc.util.InputReader;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class AocLvl09Test {

    @Test
    void testCompactedFileSystemChecksumExample() {
        var content = InputReader.readContent("example_09.txt");

        var expected = 1928;
        var actual = AocLvl09.compactedFileSystemChecksum(content);

        assertEquals(expected, actual);
    }

    @Test
    void testNoDefragCompactedFileSystemChecksumExample() {
        var content = InputReader.readContent("example_09.txt");

        var expected = 2858;
        var actual = AocLvl09.noDefragCompactedFileSystemChecksum(content);

        assertEquals(expected, actual);
    }

}
