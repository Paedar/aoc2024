package dev.paedar.aoc.lvl06;

import dev.paedar.aoc.util.Direction;
import dev.paedar.aoc.util.GridInfo;
import dev.paedar.aoc.util.InputReader;
import dev.paedar.aoc.util.Position;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class AocLvl06 {

    public static final char OBSTACLE = '#';

    public static final String GUARD_START_POSITION = "^";

    public static void main(String[] args) {
        var lines = InputReader.readLines("input_06.txt");

        var guardPathPositionCount = countGuardPathPositions(lines);
        System.out.println("Amount of positions the guard passes through: " + guardPathPositionCount);
    }

    public static int countGuardPathPositions(List<String> lines) {
        var obstacles = getObstacles(lines);
        var guardPosition = getStartPosition(lines);
        var guardDirection = Direction.TOP;
        var gridInfo = GridInfo.of(lines);

        var pathPositions = new HashSet<Position>();
        while(gridInfo.inbounds(guardPosition)) {
            pathPositions.add(guardPosition);
            var nextPosition = guardDirection.next(guardPosition);
            if(obstacles.contains(nextPosition)) {
                guardDirection = guardDirection.turnClockWise90();
            } else {
                guardPosition = nextPosition;
            }
        }

        return pathPositions.size();
    }

    private static Position getStartPosition(List<String> lines) {
        var startLineNum = IntStream.range(0, lines.size())
                                 .filter(lineNum -> lines.get(lineNum).contains(GUARD_START_POSITION))
                                 .findFirst()
                                 .orElseThrow(() -> new IllegalArgumentException("No start position found"));
        var startLine = lines.get(startLineNum);
        var horizontalPosition = startLine.indexOf(GUARD_START_POSITION);
        return new Position(horizontalPosition, startLineNum);
    }

    private static Set<Position> getObstacles(List<String> lines) {
        return IntStream.range(0, lines.size())
                        .mapToObj(lineNum -> obstaclesOnLine(lines.get(lineNum), lineNum))
                        .flatMap(Function.identity())
                        .collect(Collectors.toSet());
    }

    private static Stream<Position> obstaclesOnLine(String line, int lineNum) {
        var skipFirst = 0;
        var nextObstacleIndex = line.indexOf(OBSTACLE, skipFirst);
        var streamBuilder = Stream.<Position>builder();
        while (nextObstacleIndex != -1) {
            streamBuilder.add(new Position(nextObstacleIndex, lineNum));
            nextObstacleIndex = line.indexOf(OBSTACLE, nextObstacleIndex + 1);
        }
        return streamBuilder.build();
    }

}
