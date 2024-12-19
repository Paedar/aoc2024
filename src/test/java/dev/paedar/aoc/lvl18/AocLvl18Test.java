package dev.paedar.aoc.lvl18;

import dev.paedar.aoc.util.InputReader;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class AocLvl18Test {

    @Test
    void pathLengthToExitExample() {
        var tokens = InputReader.readTokens("example_18.txt");

        var actual = AocLvl18.pathLengthToExit(tokens, 7, 7, 12);
        var expected = 22L;

        assertEquals(expected, actual);
    }

}