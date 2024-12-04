package dev.paedar.aoc.lvl04;

import java.util.function.UnaryOperator;

enum Direction {
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

    Position next(Position position) {
        return nextOperator.apply(position);
    }

    Position previous(Position position) {
        return previousOperator.apply(position);
    }
}
