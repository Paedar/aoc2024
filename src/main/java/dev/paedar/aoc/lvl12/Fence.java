package dev.paedar.aoc.lvl12;

import dev.paedar.aoc.util.Direction;
import dev.paedar.aoc.util.Position;

import java.util.HashSet;
import java.util.Set;

public record Fence(Direction direction, Set<Position> positions) {

    public boolean canMerge(Fence other) {
        return this.direction == other.direction && this.neighbours(other);
    }

    public Fence merge(Fence other) {
        if (!canMerge(other)) {
            throw new IllegalArgumentException("Can't merge fences");
        }
        var newPositions = new HashSet<Position>(positions);
        newPositions.addAll(other.positions);
        return new Fence(direction, newPositions);
    }

    public boolean neighbours(Position other) {
        return positions.stream().anyMatch(p -> p.neighboursD4(other));
    }

    public boolean neighbours(Fence other) {
        return other.positions.stream().anyMatch(this::neighbours);
    }

}
