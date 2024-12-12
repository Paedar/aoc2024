package dev.paedar.aoc.lvl12;

import dev.paedar.aoc.util.GridInfo;
import dev.paedar.aoc.util.InputReader;
import dev.paedar.aoc.util.gatherers.MergingGatherer;

import java.util.List;

public class AocLvl12 {

    public static void main(String[] args) {
        var lines = InputReader.readLines("input_12.txt");

        var farmAreaCost = sumCostOfAreas(lines);
        System.out.println("Farm area cost: " + farmAreaCost);
    }

    public static long sumCostOfAreas(List<String> lines) {
        var gridInfo = GridInfo.of(lines);

        return gridInfo.allInboundsPositions()
                       .map(p -> FencedArea.of(gridInfo.charAt(p), p))
                       .gather(MergingGatherer.of(FencedArea::canMerge, FencedArea::merge))
                       .mapToLong(FencedArea::fenceCost)
                       .sum();
    }

}
