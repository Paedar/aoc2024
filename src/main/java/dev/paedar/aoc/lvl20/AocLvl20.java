package dev.paedar.aoc.lvl20;

import dev.paedar.aoc.util.Direction;
import dev.paedar.aoc.util.GridInfo;
import dev.paedar.aoc.util.InputReader;
import dev.paedar.aoc.util.Position;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import static java.util.function.Predicate.not;

public class AocLvl20 {

    public static void main(String[] args) {
        var lines = InputReader.readLines("input_20.txt");

//        var goodShortCheats = countCheatsSavingPicoSeconds(lines, 100, 2L);
//        System.out.println("Number of short cheats that save at least 100 picoseconds: " + goodShortCheats);

        var goodLongCheats = countCheatsSavingPicoSeconds(lines, 100, 20L);
        System.out.println("Number of slightly longer paths that save at least 100 picoseconds: " + goodLongCheats);
    }

    public static long countCheatsSavingPicoSeconds(List<String> lines, long picoSeconds, long maxCheatDuration) {
        var gridInfo = GridInfo.of(lines);
        var start = gridInfo.allInboundsPositions()
                            .filter(p -> gridInfo.charAt(p) == 'S')
                            .findFirst()
                            .orElseThrow(() -> new IllegalStateException("No start position found."));
        var end = gridInfo.allInboundsPositions()
                          .filter(p -> gridInfo.charAt(p) == 'E')
                          .findFirst()
                          .orElseThrow(() -> new IllegalStateException("No end position found."));

        var exploredMapFromStart = exploreFullMap(gridInfo, start);
        var exploredMapFromEnd = exploreFullMap(gridInfo, end);
        var timeToFinish = Optional.of(exploredMapFromStart)
                                   .map(m -> m.get(end))
                                   .orElseThrow(() -> new IllegalStateException("No path to finish found."));

        var cheatStarts = exploredMapFromStart.keySet().stream()
                                              .filter(p -> gridInfo.charAt(p) != '#')
                                              .collect(Collectors.toSet());

        var possibleCheatEnds = gridInfo.allInboundsPositions()
                                        .filter(p -> gridInfo.charAt(p) != '#')
                                        .collect(Collectors.toSet());

        return cheatStarts.stream()
                          .flatMap(cheatStart -> possibleCheatEnds.stream()
                                                                  .filter(cheatEnd -> cheatStart.manhattanDistance(cheatEnd) <= maxCheatDuration)
                                                                  .map(cheatEnd -> new Cheat(cheatStart, cheatEnd))
                          )
                          .map(c -> findShortestPathApplyingCheat(c, exploredMapFromStart, exploredMapFromEnd))
                          .filter(cheatedTime -> timeToFinish - cheatedTime >= picoSeconds)
                          .count();
    }

    private static long findShortestPathApplyingCheat(Cheat cheat, Map<Position, Long> exploredMapFromStart, Map<Position, Long> exploredMapFromEnd) {
        var startToCheatPosition = exploredMapFromStart.get(cheat.start());
        var cheatStartToCheatEnd = cheat.start().manhattanDistance(cheat.end());
        var cheatEndToEnd = exploredMapFromEnd.get(cheat.end());
        return startToCheatPosition + cheatStartToCheatEnd + cheatEndToEnd;
    }

    private static Predicate<DijkstraState> positionReached(Position end) {
        return state -> state.positionReached(end);
    }

    private static Predicate<Position> notAWall(GridInfo gridInfo) {
        return p -> gridInfo.charAt(p) != '#';
    }

    private static Map<Position, Long> exploreFullMap(GridInfo gridInfo, Position start) {
        /*
        Returns a map of how fast each position can be reached from the start. Includes walls that can be reached directly off the regular path
         */
        return dijkstraExplore(gridInfo, start, _ -> true, notAWall(gridInfo), _ -> false);
    }

    private static Map<Position, Long> dijkstraExplore(GridInfo gridInfo, Position start, Predicate<Position> shouldAcceptForQueue, Predicate<Position> shouldPropagate, Predicate<DijkstraState> endCondition) {
        /*
        Dijkstra's again?!
         */
        var costMap = new HashMap<Position, Long>();
        var investigationQueue = new HashMap<Position, Long>();
        investigationQueue.put(start, 0L);

        var state = new DijkstraState(costMap, investigationQueue);

        while (!state.queueEmpty() && !endCondition.test(state)) {
            var nextPosition = investigationQueue.entrySet().stream()
                                                 .min(Map.Entry.comparingByValue())
                                                 .orElseThrow(() -> new IllegalStateException("Investigation queue is empty."));
            var position = nextPosition.getKey();
            var cost = nextPosition.getValue();
            var nextCost = cost + 1;
            costMap.put(position, cost);
            investigationQueue.remove(position);
            if (shouldPropagate.test(position)) {
                Direction.cardinalDirections()
                         .map(d -> d.next(position))
                         .filter(not(costMap::containsKey))
                         .filter(p -> !investigationQueue.containsKey(p) || investigationQueue.get(p) > nextCost)
                         .filter(gridInfo::inbounds)
                         .filter(shouldAcceptForQueue)
                         .forEach(p -> investigationQueue.put(p, nextCost));
            }
        }

        return costMap;
    }

}
