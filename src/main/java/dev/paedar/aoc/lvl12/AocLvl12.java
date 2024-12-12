package dev.paedar.aoc.lvl12;

import dev.paedar.aoc.util.GridInfo;
import dev.paedar.aoc.util.InputReader;
import dev.paedar.aoc.util.gatherers.MergingGatherer;

import java.util.List;
import java.util.function.ToLongFunction;

public class AocLvl12 {

    public static void main(String[] args) {
        var lines = InputReader.readLines("input_12.txt");

        var farmAreaCost = sumCostOfAreas(lines);
        System.out.println("Farm area cost: " + farmAreaCost);

        var farmAreaCostWithDiscount = sumCostOfAreasDiscounted(lines);
        System.out.println("Farm area cost with a discount: " + farmAreaCostWithDiscount);
    }

    public static long sumCostOfAreas(List<String> lines) {
        return fenceCost(lines, FencedArea::fenceCost);
    }

    public static long sumCostOfAreasDiscounted(List<String> lines) {
        return fenceCost(lines, FencedArea::discountFenceCost);
    }

    private static long fenceCost(List<String> lines, ToLongFunction<FencedArea> costFunction) {
        var gridInfo = GridInfo.of(lines);

        return gridInfo.allInboundsPositions()
                       .map(p -> FencedArea.of(gridInfo.charAt(p), p))
                       .gather(MergingGatherer.of(FencedArea::canMerge, FencedArea::merge))
                       .mapToLong(costFunction)
                       .sum();
    }

}
