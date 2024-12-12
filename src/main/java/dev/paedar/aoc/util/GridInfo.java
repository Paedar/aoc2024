package dev.paedar.aoc.util;

import java.awt.*;
import java.util.List;
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
        if(outOfBounds(p)) {
            throw new IndexOutOfBoundsException();
        }
        return lines.get(p.y()).charAt(p.x());
    }

}