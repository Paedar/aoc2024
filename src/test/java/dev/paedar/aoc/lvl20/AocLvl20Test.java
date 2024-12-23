package dev.paedar.aoc.lvl20;

import dev.paedar.aoc.util.InputReader;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class AocLvl20Test {

    @Test
    void countCheatsSavingPicoSeconds() {
        var lines = InputReader.readLines("example_20.txt");

        var actual = AocLvl20.countCheatsSavingPicoSeconds(lines, 12L, 2L);
        var expected = 8L;

        assertEquals(expected, actual);
    }

    @Test
    void countCheatsSavingPicoSecondsWithLongerCheats() {
        var lines = InputReader.readLines("example_20.txt");

        var actual = AocLvl20.countCheatsSavingPicoSeconds(lines, 50, 20L);
        var expected = 285L;

        assertEquals(expected, actual);
    }

}