package dev.paedar.aoc.lvl11;

import dev.paedar.aoc.util.InputReader;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class AocLvl11Test {

    @Test
    void testStoneLineCount25BlinksExample() {
        var tokens = InputReader.readTokens("example_11.txt");

        var actual = AocLvl11.numStonesAfterNUpdates(tokens, 25);
        var expected = 55312L;

        assertEquals(expected, actual);
    }

    @Test
    void testStoneLineCount75BlinksExample() {
        var tokens = InputReader.readTokens("example_11.txt");

        var actual = AocLvl11.numStonesAfterNUpdates(tokens, 75);
        var expected = 65601038650482L;

        assertEquals(expected, actual);
    }

}
