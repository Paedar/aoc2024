package dev.paedar.aoc.util;

import java.util.function.UnaryOperator;

public enum Direction {
    TOP_LEFT(Position::topLeft, Position::bottomRight),
    TOP(Position::top, Position::bottom),
    TOP_RIGHT(Position::topRight, Position::bottomLeft),
    LEFT(Position::left, Position::right),
    RIGHT(Position::right, Position::left),
    BOTTOM_LEFT(Position::bottomLeft, Position::topRight),
    BOTTOM(Position::bottom, Position::top),
    BOTTOM_RIGHT(Position::bottomRight, Position::topLeft);

    private final UnaryOperator<Position> nextOperator;
    private final UnaryOperator<Position> previousOperator;

    Direction(UnaryOperator<Position> next, UnaryOperator<Position>previous) {
        nextOperator = next;
        previousOperator = previous;
    }

    public Position next(Position position) {
        return nextOperator.apply(position);
    }

    public Position previous(Position position) {
        return previousOperator.apply(position);
    }

    public Direction turnClockWise90() {
        return switch (this) {
            case TOP_LEFT -> TOP_RIGHT;
            case TOP -> RIGHT;
            case TOP_RIGHT -> BOTTOM_RIGHT;
            case LEFT -> TOP;
            case RIGHT -> BOTTOM;
            case BOTTOM_LEFT -> TOP_LEFT;
            case BOTTOM -> LEFT;
            case BOTTOM_RIGHT -> BOTTOM_LEFT;
        };
    }
}
