package dev.paedar.aoc.lvl10;

import dev.paedar.aoc.util.GridInfo;
import dev.paedar.aoc.util.InputReader;
import dev.paedar.aoc.util.Position;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static dev.paedar.aoc.util.Direction.cardinalDirections;

public class AocLvl10 {

    public static void main(String[] args) {
        var lines = InputReader.readLines("input_10.txt");

        var score = sumOfTrailheadScores(lines);
        System.out.println("Sum of trailhead scores: " + score);

        var rating = sumOfTrailheadRatings(lines);
        System.out.println("Sum of trailhead ratings: " + rating);
    }

    public static long sumOfTrailheadScores(List<String> lines) {
        var gridInfo = GridInfo.of(lines);
        var heightMap = gridInfo.allInboundsPositions()
                                .collect(Collectors.toMap(Function.identity(), p -> Integer.parseInt(lines.get(p.y())
                                                                                                          .substring(p.x(), p.x() + 1))));

        return heightMap.entrySet().stream()
                        .filter(p -> p.getValue() == 0)
                        .map(Map.Entry::getKey)
                        .map(trailHead -> recursiveTrailEndFinder(trailHead, heightMap, gridInfo))
                        .mapToLong(trailEndStream -> trailEndStream.distinct().count())
                        .sum();
    }

    public static long sumOfTrailheadRatings(List<String> lines) {
        var gridInfo = GridInfo.of(lines);
        var heightMap = gridInfo.allInboundsPositions()
                                .collect(Collectors.toMap(Function.identity(), p -> Integer.parseInt(lines.get(p.y())
                                                                                                          .substring(p.x(), p.x() + 1))));

        return heightMap.entrySet().stream()
                        .filter(p -> p.getValue() == 0)
                        .map(Map.Entry::getKey)
                        .mapToLong(trailHead -> recursiveTrailHeadRating(trailHead, heightMap, gridInfo))
                        .sum();
    }

    private static Stream<Position> recursiveTrailEndFinder(Position position, Map<Position, Integer> heightMap, GridInfo gridInfo) {
        var height = heightMap.get(position);
        if (Objects.isNull(height)) {
            throw new IllegalArgumentException("No height found for position: " + position);
        }
        if (height == 9) {
            return Stream.of(position);
        } else {
            return cardinalDirections()
                         .map(d -> d.next(position))
                         .filter(gridInfo::inbounds)
                         .filter(p -> heightMap.getOrDefault(p, -1) == height + 1) // Gradual ascent
                         .flatMap(p -> recursiveTrailEndFinder(p, heightMap, gridInfo));
        }
    }

    private static long recursiveTrailHeadRating(Position position, Map<Position, Integer> heightMap, GridInfo gridInfo) {
        var height = heightMap.get(position);
        if (Objects.isNull(height)) {
            throw new IllegalArgumentException("No height found for position: " + position);
        }
        if (height == 9) {
            return 1;
        } else {
            return cardinalDirections()
                         .map(d -> d.next(position))
                         .filter(gridInfo::inbounds)
                         .filter(p -> heightMap.getOrDefault(p, -1) == height + 1) // Gradual ascent
                         .mapToLong(p -> recursiveTrailHeadRating(p, heightMap, gridInfo))
                         .sum();
        }
    }

}
