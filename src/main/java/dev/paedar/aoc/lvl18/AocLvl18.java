package dev.paedar.aoc.lvl18;

import dev.paedar.aoc.util.Direction;
import dev.paedar.aoc.util.GridInfo;
import dev.paedar.aoc.util.InputReader;
import dev.paedar.aoc.util.Position;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Predicate;
import java.util.stream.Gatherers;

public class AocLvl18 {

    public static final long NO_PATH_FOUND = -1L;

    public static void main(String[] args) {
        var tokens = InputReader.readTokens("input_18.txt");

        var pathLength = pathLengthToExit(tokens, 71, 71, 1024);
        System.out.println("Found a path of length: " + pathLength);

        var blockingByte = firstBlockingByte(tokens, 71, 71);
        System.out.println("First byte that would block the path: " + blockingByte);
    }

    public static long pathLengthToExit(List<String> tokens, int width, int height, int numMemoryBlocks) {
        var fallingBlockPositions = readFallingBlockPositions(tokens);
        var obstructions = fallingBlockPositions.subList(0, numMemoryBlocks);

        return findPathLengthThroughObstructions(obstructions, width, height);
    }

    private static Long findPathLengthThroughObstructions(List<Position> obstructions, int width, int height) {
        var gridInfo = simulateFallingBlocks(obstructions, width, height);

        /*
        Dijkstra's again?!
         */

        var start = new Position(0, 0);
        var end = new Position(width - 1, height - 1);
        var costToReach = new HashMap<Position, Long>();
        var investigationQueue = new HashMap<Position, Long>();
        investigationQueue.put(start, 0L);

        while (!investigationQueue.isEmpty() && !costToReach.containsKey(end)) {
            var nextPosition = investigationQueue.entrySet().stream()
                                                 .min(Map.Entry.comparingByValue())
                                                 .orElseThrow(() -> new IllegalStateException("Investigation queue is empty."));
            var position = nextPosition.getKey();
            var cost = nextPosition.getValue();
            var nextCost = cost + 1;
            costToReach.put(position, cost);
            investigationQueue.remove(position);
            Direction.cardinalDirections()
                     .map(d -> d.next(position))
                     .filter(Predicate.not(costToReach::containsKey))
                     .filter(p -> !investigationQueue.containsKey(p) || investigationQueue.get(p) > nextCost)
                     .filter(gridInfo::inbounds)
                     .filter(p -> gridInfo.charAt(p) != '#')
                     .forEach(p -> investigationQueue.put(p, nextCost));
        }

        return costToReach.getOrDefault(end, NO_PATH_FOUND);
    }

    private static List<Position> readFallingBlockPositions(List<String> tokens) {
        return tokens.stream()
                     .map(Integer::parseInt)
                     .gather(Gatherers.windowFixed(2))
                     .map(w -> new Position(w.getFirst(), w.getLast()))
                     .toList();
    }

    private static GridInfo simulateFallingBlocks(List<Position> fallingBlockPositions, int width, int height) {
        var gridInfo = new GridInfo(height, width);
        gridInfo.allInboundsPositions().forEach(p -> {
            if (fallingBlockPositions.contains(p)) {
                gridInfo.putAt(p, '#');
            } else {
                gridInfo.putAt(p, '.');
            }
        });

        return gridInfo;
    }

    public static String firstBlockingByte(List<String> tokens, int width, int height) {
        var fallingBlockPositions = readFallingBlockPositions(tokens);

        /*
        Binary search for the lowest first byte that results in an unsolvable maze
         */

        var min = 0;
        var max = tokens.size() / 2;
        var lastSearchWasBlocked = true;
        while (min < max) {
            var searchIndex = (min + max) / 2;

            var obstructions = fallingBlockPositions.subList(0, searchIndex);
            var pathLengthToEnd = findPathLengthThroughObstructions(obstructions, width, height);

            lastSearchWasBlocked = pathLengthToEnd == NO_PATH_FOUND;
            if(lastSearchWasBlocked) {
                max = searchIndex - 1; /* No path was found, shift the upper bound down */
            } else {
                min = searchIndex + 1; /* A path was found, shift the lower bound up */
            }
        }

        var correction = lastSearchWasBlocked ? 0 : -1;
        var theOneThatBlockedItAll = fallingBlockPositions.get(min + correction);

        return "%s,%s".formatted(theOneThatBlockedItAll.x(), theOneThatBlockedItAll.y());
    }

}
