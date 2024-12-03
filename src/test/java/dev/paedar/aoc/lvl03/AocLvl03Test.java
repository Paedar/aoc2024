package dev.paedar.aoc.lvl03;

import dev.paedar.aoc.util.InputReader;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class AocLvl03Test {

    @Test
    void sumValidMultiplicationsExample() {
        var content = InputReader.readContent("example_03.txt");

        var expected = 161;
        var actual = AocLvl03.sumValidMultiplications(content);

        assertEquals(expected, actual);
    }

    @Test
    void sumValidMultiplicationsInput() {
        var content = InputReader.readContent("input_03.txt");

        var expected = 166357705;
        var actual = AocLvl03.sumValidMultiplications(content);

        assertEquals(expected, actual);
    }
}