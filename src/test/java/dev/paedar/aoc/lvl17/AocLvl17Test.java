package dev.paedar.aoc.lvl17;

import dev.paedar.aoc.util.InputReader;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class AocLvl17Test {

    @Test
    void executeComputerProgramExample() {
        var lines = InputReader.readLines("example_17.txt");

        var actual = AocLvl17.executeComputerProgram(lines);
        var expected = "4,6,3,5,6,3,5,2,1,0";
        assertEquals(expected, actual);
    }

    @Test
    void findLowestRegistryAValueToSelfReplicateExample() {
        var lines = InputReader.readLines("example_17b.txt");

        var actual = AocLvl17.findLowestRegistryAValueToSelfReplicate(lines);
        var expected = 117440;

        assertEquals(expected, actual);
    }

}