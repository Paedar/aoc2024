package dev.paedar.aoc.lvl01;

import dev.paedar.aoc.util.InputReader;
import org.junit.jupiter.api.Test;

import static dev.paedar.aoc.lvl01.AocLvl01.sortedAsInteger;
import static dev.paedar.aoc.lvl01.AocLvl01.takeColumn;
import static org.junit.jupiter.api.Assertions.assertEquals;

class Input01Test {

    @Test
    void testInputDistance() {
        var expected = 1834060;

        var tokens = InputReader.readTokens("input_01.txt");

        var leftList = sortedAsInteger(takeColumn(tokens, 0, 2));
        var rightList = sortedAsInteger(takeColumn(tokens, 1, 2));

        var actual = AocLvl01.columnDistance(leftList, rightList);
        assertEquals(expected, actual);
    }

    @Test
    void testInputSimilarity() {
        var expected = 21607792;

        var tokens = InputReader.readTokens("input_01.txt");

        var leftList = sortedAsInteger(takeColumn(tokens, 0, 2));
        var rightList = sortedAsInteger(takeColumn(tokens, 1, 2));

        var actual = AocLvl01.columnSimilarity(leftList, rightList);
        assertEquals(expected, actual);
    }
}
