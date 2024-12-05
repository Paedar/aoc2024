package dev.paedar.aoc.lvl03;

import dev.paedar.aoc.util.InputReader;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class Input03Test {

    @Test
    void sumValidMultiplicationsInput() {
        var content = InputReader.readContent("input_03.txt");

        var expected = 166357705;
        var actual = AocLvl03.sumValidMultiplications(content);

        assertEquals(expected, actual);
    }

    @Test
    void sumEnabledMultiplicationsInput() {
        var content = InputReader.readContent("input_03.txt");

        var expected = 88811886;
        var actual = AocLvl03.sumEnabledMultiplications(content);

        assertEquals(expected, actual);
    }
}
