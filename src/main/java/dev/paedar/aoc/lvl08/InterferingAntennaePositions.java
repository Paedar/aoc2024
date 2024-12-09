package dev.paedar.aoc.lvl08;

import dev.paedar.aoc.util.GridInfo;
import dev.paedar.aoc.util.Position;
import dev.paedar.aoc.util.Util;

import java.util.ArrayList;
import java.util.stream.Stream;

public record InterferingAntennaePositions(Position first, Position second) {

    public Stream<Position> interferencePositions() {
        var dx = first.x() - second.x();
        var dy = first.y() - second.y();

        return Stream.of(first.plus(dx, dy), second.minus(dx, dy));
    }

    public Stream<Position> interferencePositionsAccountingForHarmonics(GridInfo gridInfo) {
        var dx = first.x() - second.x();
        var dy = first.y() - second.y();
        /*
        Reduce dy/dx using greatest common divisor
         */
        var gcd = Util.gcd(Math.abs(dx), Math.abs(dy));
        dx = dx / gcd;
        dy = dy / gcd;

        var positions = new ArrayList<Position>();
        var next = first;
        while (gridInfo.inbounds(next)) {
            positions.add(next);
            next = next.plus(dx, dy);
        }

        next = first;
        while (gridInfo.inbounds(next)) {
            positions.add(next);
            next = next.minus(dx, dy);
        }

        return positions.stream().distinct();
    }

}
