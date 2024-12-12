package dev.paedar.aoc.lvl12;

import dev.paedar.aoc.util.Direction;
import dev.paedar.aoc.util.Position;

import java.util.HashSet;
import java.util.Set;

public class FencedArea {

    private final char areaType;

    private final Set<Position> positions;

    private FencedArea(char areaType, Set<Position> positions) {
        this.areaType = areaType;
        this.positions = new HashSet<>(positions);
    }

    public static FencedArea of(char areaType, Position position) {
        return new FencedArea(areaType, Set.of(position));
    }

    public boolean neighbours(Position other) {
        return positions.stream().anyMatch(p -> p.neighboursD4(other));
    }

    public boolean neighbours(FencedArea other) {
        return other.positions.stream().anyMatch(this::neighbours);
    }

    public boolean canMerge(FencedArea other) {
        return this.areaType == other.areaType
                       && this.neighbours(other);
    }

    public FencedArea merge(FencedArea other) {
        if (!canMerge(other)) {
            throw new IllegalArgumentException("Cannot merge fenced areas");
        }
        var mergedPositions = new HashSet<>(this.positions);
        mergedPositions.addAll(other.positions);
        return new FencedArea(areaType, mergedPositions);
    }

    public long fenceCost() {
        return area() * perimeter();
    }

    public long area() {
        return positions.size();
    }

    public long perimeter() {
        return positions.stream()
                        .mapToLong(this::perimeterFor)
                        .sum();
    }

    private long perimeterFor(Position position) {
        return 4 - Direction.cardinalDirections()
                            .map(d -> d.next(position))
                            .filter(positions::contains)
                            .count();
    }

}
