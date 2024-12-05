package dev.paedar.aoc.lvl03;

import dev.paedar.aoc.util.InputReader;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class Example03Test {

    @Test
    void sumValidMultiplicationsExample() {
        var content = InputReader.readContent("example_03.txt");

        var expected = 161;
        var actual = AocLvl03.sumValidMultiplications(content);

        assertEquals(expected, actual);
    }

    @Test
    void sumEnabledMultiplicationsExample() {
        var content = InputReader.readContent("example_03_B.txt");

        var expected = 48;
        var actual = AocLvl03.sumEnabledMultiplications(content);

        assertEquals(expected, actual);
    }

}