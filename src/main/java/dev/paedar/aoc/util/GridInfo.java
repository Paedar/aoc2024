package dev.paedar.aoc.util;

import java.awt.*;
import java.util.List;

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

}