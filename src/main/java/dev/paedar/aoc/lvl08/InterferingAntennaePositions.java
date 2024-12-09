package dev.paedar.aoc.lvl08;

import dev.paedar.aoc.util.Position;

import java.util.stream.Stream;

public record InterferingAntennaePositions (Position first, Position second) {

    public Stream<Position> interferencePositions() {
        var dx = first.x() - second.x();
        var dy = first.y() - second.y();

        return Stream.of(first.plus(dx, dy), second.minus(dx, dy));
    }
}
