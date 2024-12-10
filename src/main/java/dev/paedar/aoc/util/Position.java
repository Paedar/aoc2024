package dev.paedar.aoc.util;

public record Position(int x, int y) {

    Position northWest() {
        return new Position(x - 1, y - 1);
    }

    Position north() {
        return new Position(x, y - 1);
    }

    Position northEast() {
        return new Position(x + 1, y - 1);
    }

    Position west() {
        return new Position(x - 1, y);
    }

    Position east() {
        return new Position(x + 1, y);
    }

    Position southWest() {
        return new Position(x - 1, y + 1);
    }

    Position south() {
        return new Position(x, y + 1);
    }

    Position southEast() {
        return new Position(x + 1, y + 1);
    }

    public Position plus(int dx, int dy) {
        return new Position(x + dx, y + dy);
    }

    public Position minus(int dx, int dy) {
        return plus(-dx, -dy);
    }

}
