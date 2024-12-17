package dev.paedar.aoc.util;

import java.util.function.UnaryOperator;
import java.util.stream.Stream;

public enum Direction {
    NORTH_WEST(Position::northWest, Position::southEast),
    NORTH(Position::north, Position::south),
    NORTH_EAST(Position::northEast, Position::southWest),
    WEST(Position::west, Position::east),
    EAST(Position::east, Position::west),
    SOUTH_WEST(Position::southWest, Position::northEast),
    SOUTH(Position::south, Position::north),
    SOUTH_EAST(Position::southEast, Position::northWest);

    private final UnaryOperator<Position> nextOperator;
    private final UnaryOperator<Position> previousOperator;

    Direction(UnaryOperator<Position> next, UnaryOperator<Position>previous) {
        nextOperator = next;
        previousOperator = previous;
    }

    public static Direction ofCharacter(int character) {
        return switch(character) {
            case '^' -> NORTH;
            case 'v' -> SOUTH;
            case '<' -> WEST;
            case '>' -> EAST;
            default -> throw new IllegalArgumentException("Invalid direction: " + character);
        };
    }

    public Position next(Position position) {
        return nextOperator.apply(position);
    }

    public Position previous(Position position) {
        return previousOperator.apply(position);
    }

    public Direction turnClockWise90() {
        return switch (this) {
            case NORTH_WEST -> NORTH_EAST;
            case NORTH -> EAST;
            case NORTH_EAST -> SOUTH_EAST;
            case WEST -> NORTH;
            case EAST -> SOUTH;
            case SOUTH_WEST -> NORTH_WEST;
            case SOUTH -> WEST;
            case SOUTH_EAST -> SOUTH_WEST;
        };
    }

    public Stream<Direction> perpendicularDirections() {
        var nextCw = this.turnClockWise90();
        var nextCcw = nextCw.turnClockWise90().turnClockWise90();
        return Stream.of(nextCw, nextCcw);
    }

    public static Stream<Direction> cardinalDirections() {
        return Stream.of(Direction.NORTH, Direction.SOUTH, Direction.WEST, Direction.EAST);
    }
}
