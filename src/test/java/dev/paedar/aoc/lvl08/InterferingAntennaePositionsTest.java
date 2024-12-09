package dev.paedar.aoc.lvl08;

import dev.paedar.aoc.util.Position;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class InterferingAntennaePositionsTest {

    @Test
    void interferencePositions() {
        var first = new Position(1,2);
        var second = new Position(4,3);

        var interferencePositions = new InterferingAntennaePositions(first, second).interferencePositions().toList();

        assertEquals(2, interferencePositions.size());
        assertTrue(interferencePositions.contains(new Position(7,4)));
        assertTrue(interferencePositions.contains(new Position(-2,1)));
    }

}