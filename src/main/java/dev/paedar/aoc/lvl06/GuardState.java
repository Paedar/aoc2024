package dev.paedar.aoc.lvl06;

import dev.paedar.aoc.util.Direction;
import dev.paedar.aoc.util.Position;

import java.util.Set;

public record GuardState(Position position, Direction heading) {

    GuardState next(Set<Position> obstructions) {
        var nextPosition = heading.next(position);
        if(obstructions.contains(nextPosition)) {
            var nextDirection = heading.turnClockWise90();
            return new GuardState(position, nextDirection);
        } else {
            return new GuardState(nextPosition, heading);
        }
    }
}
