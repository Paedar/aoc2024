package dev.paedar.aoc.lvl11;

import dev.paedar.aoc.util.InputReader;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class AocLvl11Test {

    @Test
    void testStoneLineCount25BlinksExample() {
        var tokens = InputReader.readTokens("example_11.txt");

        var actual = AocLvl11.numStonesAfterNUpdates(tokens, 25);
        var expected = 55312;

        assertEquals(expected, actual);
    }

    @Test
    @Disabled("performance reasons")
    void testStoneLineCount75BlinksExample() {
        var tokens = InputReader.readTokens("example_11.txt");

        var actual = AocLvl11.numStonesAfterNUpdates(tokens, 75);
        var expected = -1; // This value is incorrect, I have not fully run the whole shebang

        assertEquals(expected, actual);
    }
}
