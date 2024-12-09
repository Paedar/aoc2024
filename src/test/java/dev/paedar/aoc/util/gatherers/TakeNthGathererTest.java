package dev.paedar.aoc.util.gatherers;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class TakeNthGathererTest {

    @Test
    void testTakeColumn() {
        var input = List.of("a", "b",
                            "c", "d");
        var column0 = input.stream()
                           .gather(new TakeNthGatherer(2, 0))
                           .toList();
        var column1 = input.stream()
                           .gather(new TakeNthGatherer(2, 1))
                           .toList();

        assertEquals(List.of("a", "c"), column0);
        assertEquals(List.of("b", "d"), column1);
    }
}