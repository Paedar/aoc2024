package dev.paedar.aoc.lvl01;

import dev.paedar.aoc.util.InputReader;
import org.junit.jupiter.api.Test;

import static dev.paedar.aoc.lvl01.AocLvl01.sortedAsInteger;
import static dev.paedar.aoc.lvl01.AocLvl01.takeColumn;
import static org.junit.jupiter.api.Assertions.assertEquals;

class AocLvl01Test {

    @Test
    void testExampleDistance() {
        var expected = 11;

        var tokens = InputReader.readTokens("" +
                                                    "example_01.txt");

        var leftList = sortedAsInteger(takeColumn(tokens, 0, 2));
        var rightList = sortedAsInteger(takeColumn(tokens, 1, 2));

        var actual = AocLvl01.columnDistance(leftList, rightList);
        assertEquals(expected, actual);
    }

    @Test
    void testExampleSimilarity() {
        var expected = 31;

        var tokens = InputReader.readTokens("" +
                                                    "example_01.txt");

        var leftList = sortedAsInteger(takeColumn(tokens, 0, 2));
        var rightList = sortedAsInteger(takeColumn(tokens, 1, 2));

        var actual = AocLvl01.columnSimilarity(leftList, rightList);
        assertEquals(expected, actual);
    }

}