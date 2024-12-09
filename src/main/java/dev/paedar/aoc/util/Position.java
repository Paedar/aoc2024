package dev.paedar.aoc.util;

public record Position(int x, int y) {

    Position topLeft() {
        return new Position(x - 1, y - 1);
    }

    Position top() {
        return new Position(x, y - 1);
    }

    Position topRight() {
        return new Position(x + 1, y - 1);
    }

    Position left() {
        return new Position(x - 1, y);
    }

    Position right() {
        return new Position(x + 1, y);
    }

    Position bottomLeft() {
        return new Position(x - 1, y + 1);
    }

    Position bottom() {
        return new Position(x, y + 1);
    }

    Position bottomRight() {
        return new Position(x + 1, y + 1);
    }

    public Position plus(int dx, int dy) {
        return new Position(x + dx, y + dy);
    }

    public Position minus(int dx, int dy) {
        return plus(-dx, -dy);
    }

}
