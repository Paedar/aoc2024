package dev.paedar.aoc.lvl08;

import dev.paedar.aoc.util.InputReader;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class AocLvl08Test {

    @Test
    void testCountInboundAntiNodeLocationsExample() {
        var lines = InputReader.readLines("example_08.txt");

        var expected = 14;
        var actual = AocLvl08.countInboundAntiNodeLocations(lines);

        assertEquals(expected, actual);
    }

    @Test
    void testCountInboundAntiNodeLocationsAccountingForHarmonicsExample() {
        var lines = InputReader.readLines("example_08.txt");

        var expected = 34;
        var actual = AocLvl08.countInboundAntiNodeLocationsAccountingForHarmonics(lines);

        assertEquals(expected, actual);
    }

}
