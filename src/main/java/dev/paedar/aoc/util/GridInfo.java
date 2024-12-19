package dev.paedar.aoc.util;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public record GridInfo(List<String> lines, int height, int width) {

    public static GridInfo of(List<String> lines) {
        var height = lines.size();
        var width = lines.stream()
                         .mapToInt(String::length)
                         .max()
                         .orElse(0);
        return new GridInfo(lines, height, width);
    }

    public GridInfo(int height, int width) {
        this(new ArrayList<>(height), height, width);

        var defaultString = " ".repeat(width);
        IntStream.range(0, height)
                .forEach(i -> lines.add(i, defaultString));

    }

    public boolean inbounds(Position p) {
        return p.x() >= 0 && p.x() < width && p.y() >= 0 && p.y() < height;
    }

    public boolean outOfBounds(Position p) {
        return !inbounds(p);
    }

    public Stream<Position> allInboundsPositions() {
        return IntStream.range(0, this.height())
                        .mapToObj(y -> IntStream.range(0, this.width())
                                                .mapToObj(x -> new Position(x, y)))
                        .flatMap(Function.identity());
    }

    public char charAt(Position p) {
        if (outOfBounds(p)) {
            throw new IndexOutOfBoundsException();
        }
        return lines.get(p.y()).charAt(p.x());
    }

    public void putAt(Position p, char c) {
        var line = Optional.of(lines)
                           .map(l -> l.get(p.y()))
                           .orElseThrow(() -> new IllegalArgumentException("Line not found"));
        var sb = new StringBuilder(line);
        sb.setCharAt(p.x(), c);
        lines.set(p.y(), sb.toString());
    }
}