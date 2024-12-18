package dev.paedar.aoc.lvl16;

import dev.paedar.aoc.util.GridInfo;
import dev.paedar.aoc.util.InputReader;
import dev.paedar.aoc.util.Position;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import static dev.paedar.aoc.util.Direction.EAST;

public class AocLvl16 {

    public static final Comparator<SearchNode> COMPARING_SEARCH_NODE_COST = Comparator.comparing(SearchNode::cost);

    public static void main(String[] args) {
        var lines = InputReader.readLines("input_16.txt");

        var cheapestReindeerPathCost = calculateCheapestPathCost(lines);
        System.out.println("Cost of cheapest path: " + cheapestReindeerPathCost);

        var goodSeatsCount = countPositionsAlongCheapestPaths(lines);
        System.out.println("Number of Positions along cheapest paths: " + goodSeatsCount);
    }

    public static long calculateCheapestPathCost(List<String> lines) {
        var gridInfo = GridInfo.of(lines);
        var reachablePositions = reachablePositions(gridInfo);
        var startState = getStartState(gridInfo);
        var finishPosition = getFinishPosition(gridInfo);

        var reversePaths = dijkstrasPathSearch(gridInfo, startState, reachablePositions, state -> finishReached(state.previous(), finishPosition));

        return reversePaths.keySet().stream()
                           .filter(s -> s.reindeerState().position().equals(finishPosition))
                           .findFirst()
                           .orElseThrow(() -> new IllegalStateException("No path to finish found!"))
                           .cost();
    }

    public static long countPositionsAlongCheapestPaths(List<String> lines) {
        var gridInfo = GridInfo.of(lines);
        var reachablePositions = reachablePositions(gridInfo);
        var startState = getStartState(gridInfo);
        var finishPosition = getFinishPosition(gridInfo);

        var reversePaths = dijkstrasPathSearch(gridInfo, startState, reachablePositions, state -> previousCostHigherThanFinishCost(state, finishPosition));

        var finishCost = reversePaths.keySet().stream()
                                     .filter(s -> s.reindeerState().position().equals(finishPosition))
                                     .min(Comparator.comparing(SearchNode::cost))
                                     .orElseThrow(() -> new IllegalStateException("No path to finish found!"))
                                     .cost();

        var finishStates = reversePaths.keySet().stream()
                                       .filter(s -> s.reindeerState().position().equals(finishPosition))
                                       .filter(s -> s.cost() == finishCost)
                                       .collect(Collectors.toSet());

        var passedStates = walkBack(finishStates, reversePaths);

        return passedStates.stream()
                           .map(SearchNode::reindeerState)
                           .map(ReindeerState::position)
                           .distinct()
                           .count();
    }

    private static Set<SearchNode> walkBack(Set<SearchNode> from, Map<SearchNode, List<SearchNode>> reversePaths) {
        var onPath = new HashSet<SearchNode>();
        Set<SearchNode> investigating = new HashSet<>(from);
        while (!investigating.isEmpty()) {
            onPath.addAll(investigating);
            investigating = investigating.stream()
                                         .filter(reversePaths::containsKey)
                                         .map(reversePaths::get)
                                         .flatMap(List::stream)
                                         .collect(Collectors.toSet());
        }
        return onPath;
    }

    private static boolean previousCostHigherThanFinishCost(DijkstrasState state, Position finishPosition) {
        /*
        Since Dijkstra's always investigates the cheapest first, we can stop searching once we investigate nodes with a higher cost than the finish.
        We cannot stop once we reach the finish, because it might be possible to reach the finish position from two different directions at the same cost!
        (upon inspection: this is the case in my particular input file!)
         */
        return state.reversePaths().keySet().stream()
                    .filter(s -> s.reindeerState().position().equals(finishPosition))
                    .min(COMPARING_SEARCH_NODE_COST)
                    .map(s -> s.cost() < state.previous().cost())
                    .orElse(false); // In case the finish has not been reached
    }

    private static Position getFinishPosition(GridInfo gridInfo) {
        return gridInfo.allInboundsPositions()
                       .filter(p -> {
                           var c = gridInfo.charAt(p);
                           return c == 'E';
                       })
                       .findFirst()
                       .orElseThrow(() -> new IllegalStateException("No finish position found"));
    }

    private static SearchNode getStartState(GridInfo gridInfo) {
        return gridInfo.allInboundsPositions()
                       .filter(p -> {
                           var c = gridInfo.charAt(p);
                           return c == 'S';
                       })
                       .findFirst()
                       .map(p -> new ReindeerState(p, EAST))
                       .map(r -> new SearchNode(r, 0L))
                       .orElseThrow(() -> new IllegalStateException("No start position found"));
    }

    private static Set<Position> reachablePositions(GridInfo gridInfo) {
        return gridInfo.allInboundsPositions()
                       .filter(p -> {
                           var c = gridInfo.charAt(p);
                           return c == '.' || c == 'E' || c == 'S';
                       })
                       .collect(Collectors.toSet());
    }

    private static HashMap<SearchNode, List<SearchNode>> dijkstrasPathSearch(GridInfo gridInfo, SearchNode startState, Set<Position> nonWallPositions, Predicate<DijkstrasState> finishCondition) {

        /*
        Attempt Dijkstra's algorithm
         */

        var reversePaths = new HashMap<SearchNode, List<SearchNode>>();
        var foundStates = new HashSet<ReindeerState>();
        var investigationQueue = new ArrayList<SearchNode>(gridInfo.width() * gridInfo.height());
        investigationQueue.add(startState);
        var investigationPrevious = new HashMap<SearchNode, List<SearchNode>>();

        foundStates.add(startState.reindeerState());
        SearchNode previous = null;

        var dijkstrasState = new DijkstrasState(previous, reversePaths, foundStates);

        while (!finishCondition.test(dijkstrasState)) {
            var nextStateToVisit = investigationQueue.stream()
                                                     .min(COMPARING_SEARCH_NODE_COST)
                                                     .orElseThrow(() -> new IllegalStateException("No more paths to investigate!"));

            investigationQueue.remove(nextStateToVisit);
            nextStateToVisit.nextPossibleStates()
                            .filter(s -> canWalkHere(s, nonWallPositions))
                            .filter(s -> !alreadyReached(s, foundStates) && !cheaperPathInQueue(s, investigationQueue))
                            .forEach(e -> {
                                investigationQueue.add(e);
                                var reversePathsForNextState = investigationPrevious.computeIfAbsent(e, _ -> new ArrayList<>());
                                reversePathsForNextState.add(nextStateToVisit);
                            });

            var reversePathsForNextState = reversePaths.computeIfAbsent(nextStateToVisit, _ -> new ArrayList<>());
            if(investigationPrevious.containsKey(nextStateToVisit) ) {
                reversePathsForNextState.addAll(investigationPrevious.get(nextStateToVisit));
            }
            foundStates.add(nextStateToVisit.reindeerState());

            previous = nextStateToVisit;
            dijkstrasState.setPrevious(previous);
        }
        return reversePaths;
    }

    private static boolean finishReached(SearchNode lastFoundState, Position finishPosition) {
        return Optional.ofNullable(lastFoundState)
                       .map(SearchNode::reindeerState)
                       .map(ReindeerState::position)
                       .filter(finishPosition::equals)
                       .isPresent();
    }

    private static boolean cheaperPathInQueue(SearchNode searchNode, Collection<SearchNode> investigationQueue) {
        return investigationQueue.stream()
                                 .filter(sn -> sn.reindeerState().equals(searchNode.reindeerState()))
                                 .anyMatch(sn -> sn.cost() < searchNode.cost());
    }

    private static boolean alreadyReached(SearchNode searchNode, Set<ReindeerState> foundStates) {
        return foundStates.contains(searchNode.reindeerState());
    }

    private static boolean canWalkHere(SearchNode searchNode, Set<Position> nonWallPositions) {
        return nonWallPositions.contains(searchNode.reindeerState().position());
    }

}
