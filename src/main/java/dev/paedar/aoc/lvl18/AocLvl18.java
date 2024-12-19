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

    public static void main(String[] args) {
        var tokens = InputReader.readTokens("input_18.txt");

        var pathLength = pathLengthToExit(tokens, 71, 71, 1024);
        System.out.println("Found a path of length: " + pathLength);
    }

    public static long pathLengthToExit(List<String> tokens, int width, int height, int numMemoryBlocks) {
        var fallingBlockPositions = tokens.stream()
                                          .map(Integer::parseInt)
                                          .gather(Gatherers.windowFixed(2))
                                          .map(w -> new Position(w.getFirst(), w.getLast()))
                                          .toList();

        var gridInfo = simulateFallingBlocks(fallingBlockPositions, width, height, numMemoryBlocks);

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

        return costToReach.get(end);
    }

    private static GridInfo simulateFallingBlocks(List<Position> fallingBlockPositions, int width, int height, int numMemoryBlocks) {
        var firstN = fallingBlockPositions.subList(0, numMemoryBlocks);

        var gridInfo = new GridInfo(height, width);
        gridInfo.allInboundsPositions().forEach(p -> {
            if (firstN.contains(p)) {
                gridInfo.putAt(p, '#');
            } else {
                gridInfo.putAt(p, '.');
            }
        });

        return gridInfo;
    }

}
