package dev.paedar.aoc.lvl06;

import dev.paedar.aoc.util.Direction;
import dev.paedar.aoc.util.GridInfo;
import dev.paedar.aoc.util.InputReader;
import dev.paedar.aoc.util.Position;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class AocLvl06 {

    public static final char OBSTRUCTION = '#';

    public static final String GUARD_START_POSITION = "^";

    public static void main(String[] args) {
        var lines = InputReader.readLines("input_06.txt");

        var guardPathPositionCount = countGuardPathPositions(lines);
        System.out.println("Amount of positions the guard passes through: " + guardPathPositionCount);

        var obstructionsLeadingToLoopingPathCount = countObstructionsLeadingToLoopingGuardPath(lines);
        System.out.println("Amount of positions that would lead to a looping guard path: " + obstructionsLeadingToLoopingPathCount);
    }

    public static int countGuardPathPositions(List<String> lines) {
        var obstructions = getObstructions(lines);
        var guardState = getStart(lines);
        var gridInfo = GridInfo.of(lines);

        var path = guardWalk(gridInfo, guardState, obstructions);

        return path.stream()
                   .map(GuardState::position)
                   .collect(Collectors.toSet())
                   .size();
    }

    public static long countObstructionsLeadingToLoopingGuardPath(List<String> lines) {
        var originalObstructions = getObstructions(lines);
        var guardState = getStart(lines);
        var gridInfo = GridInfo.of(lines);

        /*
        Any new obstruction should be on the path that the guard would walk with the original layout. If not, the guard would never
        encounter it. This step isn't strictly necessary, but vastly reduces the search space.
         */
        var noNewObstructionsPath = guardWalk(gridInfo, guardState, originalObstructions);

        return noNewObstructionsPath.stream()
                                    .map(GuardState::position)
                                    .distinct()
                                    .map(obstruction -> {
                                                                var newObstructions = new HashSet<>(originalObstructions);
                                                                newObstructions.add(obstruction);
                                                                return newObstructions;
                                                            })
                                    .filter(obstructions -> guardPathLoops(gridInfo, guardState, obstructions))
                                    .count();
    }

    private static List<GuardState> guardWalk(GridInfo gridInfo, GuardState guardState, Set<Position> obstructions) {
        var path = new ArrayList<GuardState>();
        var reachedStates = new HashSet<GuardState>(); // Searching in a HashSet is so much faster than in a List, that it's worth it to keep track of both
        while (gridInfo.inbounds(guardState.position())
                       && !reachedStates.contains(guardState) // This condition breaks the path before just a loop would happen
        ) {
            path.add(guardState);
            reachedStates.add(guardState);
            guardState = guardState.next(obstructions);
        }
        return Collections.unmodifiableList(path);
    }

    private static boolean guardPathLoops(GridInfo gridInfo, GuardState initialGuardState, Set<Position> obstructions) {
        var path = guardWalk(gridInfo, initialGuardState, obstructions);
        var nextStep = path.getLast().next(obstructions);
        return path.contains(nextStep);
    }

    private static GuardState getStart(List<String> lines) {
        var startLineNum = IntStream.range(0, lines.size())
                                    .filter(lineNum -> lines.get(lineNum).contains(GUARD_START_POSITION))
                                    .findFirst()
                                    .orElseThrow(() -> new IllegalArgumentException("No start position found"));
        var startLine = lines.get(startLineNum);
        var horizontalPosition = startLine.indexOf(GUARD_START_POSITION);
        var startPosition = new Position(horizontalPosition, startLineNum);
        return new GuardState(startPosition, Direction.NORTH);
    }

    private static Set<Position> getObstructions(List<String> lines) {
        return IntStream.range(0, lines.size())
                        .mapToObj(lineNum -> obstructionsOnLine(lines.get(lineNum), lineNum))
                        .flatMap(Function.identity())
                        .collect(Collectors.toSet());
    }

    private static Stream<Position> obstructionsOnLine(String line, int lineNum) {
        var skipFirst = 0;
        var nextObstructionIndex = line.indexOf(OBSTRUCTION, skipFirst);
        var streamBuilder = Stream.<Position>builder();
        while (nextObstructionIndex != -1) {
            streamBuilder.add(new Position(nextObstructionIndex, lineNum));
            nextObstructionIndex = line.indexOf(OBSTRUCTION, nextObstructionIndex + 1);
        }
        return streamBuilder.build();
    }

}
