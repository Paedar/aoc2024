package dev.paedar.aoc.lvl08;

import dev.paedar.aoc.util.GridInfo;
import dev.paedar.aoc.util.InputReader;
import dev.paedar.aoc.util.Position;
import dev.paedar.aoc.util.gatherers.AllCombinationsGatherer;
import dev.paedar.aoc.util.gatherers.GroupToListGatherer;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Stream;

public class AocLvl08 {

    private static final char BACKGROUND = '.';

    public static void main(String[] args) {
        var lines = InputReader.readLines("input_08.txt");

        var antiNodeLocationCount = countInboundAntiNodeLocations(lines);
        System.out.println("Number of antiNodeLocations: " + antiNodeLocationCount);

        var antiNodeLocationCountAccountingForHarmonics = countInboundAntiNodeLocationsAccountingForHarmonics(lines);
        System.out.println("Number of antiNodeLocations, accounting for harmonics: " + antiNodeLocationCountAccountingForHarmonics);
    }

    public static long countInboundAntiNodeLocations(List<String> lines) {
        var gridInfo = GridInfo.of(lines);
        var antennaePositionsGroupedByType = getAntennaePositionsGroupedByType(lines, gridInfo);

        return antennaePositionsGroupedByType.entrySet()
                                             .stream()
                                             .map(Map.Entry::getValue)
                                             .flatMap(AocLvl08::interferencePositions)
                                             .distinct()
                                             .filter(gridInfo::inbounds)
                                             .count();
    }

    public static long countInboundAntiNodeLocationsAccountingForHarmonics(List<String> lines) {
        var gridInfo = GridInfo.of(lines);
        var antennaePositionsGroupedByType = getAntennaePositionsGroupedByType(lines, gridInfo);

        return antennaePositionsGroupedByType.entrySet()
                                             .stream()
                                             .map(Map.Entry::getValue)
                                             .flatMap(positions -> interferencePositionsAccountingForHarmonics(positions, gridInfo))
                                             .distinct()
                                             .count();
    }

    private static Map<Character, List<Position>> getAntennaePositionsGroupedByType(List<String> lines, GridInfo gridInfo) {
        return gridInfo.allInboundsPositions()
                       .filter(p -> lines.get(p.y()).charAt(p.x()) != BACKGROUND)
                       .gather(GroupToListGatherer.of(p -> lines.get(p.y()).charAt(p.x()), Function.identity()))
                       .findFirst()
                       .orElseThrow();
    }

    private static Stream<Position> interferencePositions(List<Position> positions) {
        return positions.stream()
                        .gather(AllCombinationsGatherer.combining(InterferingAntennaePositions::new, false, false))
                        .flatMap(InterferingAntennaePositions::interferencePositions);
    }

    private static Stream<Position> interferencePositionsAccountingForHarmonics(List<Position> positions, GridInfo gridInfo) {
        return positions.stream()
                        .gather(AllCombinationsGatherer.combining(InterferingAntennaePositions::new, false, false))
                        .flatMap(interferingAntennaePositions -> interferingAntennaePositions.interferencePositionsAccountingForHarmonics(gridInfo));
    }

}
