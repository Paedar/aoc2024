package dev.paedar.aoc.lvl16;

import dev.paedar.aoc.util.GridInfo;
import dev.paedar.aoc.util.InputReader;
import dev.paedar.aoc.util.Position;

import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import static dev.paedar.aoc.util.Direction.EAST;

public class AocLvl16 {

    public static void main(String[] args) {
        var lines = InputReader.readLines("input_16.txt");

        var cheapestReindeerPathCost = calculateCheapestPathCost(lines);
        System.out.println("Cost of cheapest path: " + cheapestReindeerPathCost);
    }

    public static long calculateCheapestPathCost(List<String> lines) {
        var gridInfo = GridInfo.of(lines);

        var nonWallPositions = gridInfo.allInboundsPositions()
                                       .filter(p -> {
                                           var c = gridInfo.charAt(p);
                                           return c == '.' || c == 'E' || c == 'S';
                                       })
                                       .collect(Collectors.toSet());

        var startState = gridInfo.allInboundsPositions()
                                 .filter(p -> {
                                     var c = gridInfo.charAt(p);
                                     return c == 'S';
                                 })
                                 .findFirst()
                                 .map(p -> new ReindeerState(p, EAST))
                                 .map(r -> new SearchNode(r, 0L))
                                 .orElseThrow(() -> new IllegalStateException("No start position found"));

        var finishPosition = gridInfo.allInboundsPositions()
                                     .filter(p -> {
                                         var c = gridInfo.charAt(p);
                                         return c == 'E';
                                     })
                                     .findFirst()
                                     .orElseThrow(() -> new IllegalStateException("No finish position found"));

        /*
        Attempt Dijkstra's algorithm
         */

        var reversePath = new HashMap<SearchNode, SearchNode>();
        var investigationQueue = new HashSet<SearchNode>();
        investigationQueue.add(startState);

        SearchNode previous = startState;

        while (!finishReached(previous, finishPosition)) {
            var nextStateToVisit = investigationQueue.stream()
                                                     .min(Comparator.comparing(SearchNode::cost))
                                                     .orElseThrow(() -> new IllegalStateException("No path to finish found!"));

            investigationQueue.remove(nextStateToVisit);
            var newSearchNodes = nextStateToVisit.nextPossibleStates()
                                                 .filter(s -> canWalkHere(s, nonWallPositions))
                                                 .filter(s -> !alreadyReached(s, reversePath.keySet()) && !cheaperPathInQueue(s, investigationQueue))
                                                 .collect(Collectors.toSet());
            investigationQueue.addAll(newSearchNodes);
            reversePath.put(nextStateToVisit, previous);
            previous = nextStateToVisit;
        }

        return previous.cost();
    }

    private static boolean cheaperPathInQueue(SearchNode searchNode, HashSet<SearchNode> investigationQueue) {
        return investigationQueue.stream()
                                 .filter(sn -> sn.reindeerState().equals(searchNode.reindeerState()))
                                 .anyMatch(sn -> sn.cost() <= searchNode.cost());
    }

    private static boolean alreadyReached(SearchNode searchNode, Set<SearchNode> foundStates) {
        return foundStates.stream()
                          .map(SearchNode::reindeerState)
                          .anyMatch(r -> r.equals(searchNode.reindeerState()));
    }

    private static boolean canWalkHere(SearchNode searchNode, Set<Position> nonWallPositions) {
        return nonWallPositions.contains(searchNode.reindeerState().position());
    }

    private static boolean finishReached(SearchNode lastFoundState, Position finishPosition) {
        return Optional.ofNullable(lastFoundState)
                       .map(SearchNode::reindeerState)
                       .map(ReindeerState::position)
                       .filter(finishPosition::equals)
                       .isPresent();
    }

}
