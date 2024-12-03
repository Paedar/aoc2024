package dev.paedar.aoc.lvl01;

import dev.paedar.aoc.util.InputReader;
import dev.paedar.aoc.util.TakeNthGatherer;

import java.util.List;

import static java.util.stream.Collectors.counting;
import static java.util.stream.Collectors.groupingBy;

public class AocLvl01 {

    public static void main(String[] args) {
        var tokens = InputReader.readTokens("input_01.txt");

        var leftList = sortedAsInteger(takeColumn(tokens, 0, 2));
        var rightList = sortedAsInteger(takeColumn(tokens, 1, 2));

        var distanceScore = columnDistance(leftList, rightList);
        System.out.println("Distance score: " + distanceScore);

        var similarityScore = columnSimilarity(leftList, rightList);
        System.out.println("Similarity score: " + similarityScore);
    }

    public static long columnSimilarity(List<Integer> leftList, List<Integer> rightList) {
        var leftCount = leftList.stream().collect(groupingBy(it -> it, counting()));
        var rightCount = rightList.stream().collect(groupingBy(it -> it, counting()));

        return leftCount.entrySet().stream()
                        .mapToLong(entry -> entry.getKey() * entry.getValue() * rightCount.getOrDefault(entry.getKey(), 0L))
                        .sum();
    }

    public static int columnDistance(List<Integer> leftList, List<Integer> rightList) {
        var totalDiff = 0;
        for (int i = 0; i < leftList.size(); i++) {
            var left = leftList.get(i);
            var right = rightList.get(i);
            var diff = Math.abs(left - right);
            totalDiff += diff;
        }

        return totalDiff;
    }

    public static List<Integer> sortedAsInteger(List<String> in) {
        return in.stream()
                 .mapToInt(Integer::parseInt)
                 .sorted()
                 .boxed()
                 .toList();
    }

    public static List<String> takeColumn(List<String> tokens, int colNum, int numCols) {
        return tokens.stream()
                     .gather(new TakeNthGatherer(numCols, colNum))
                     .toList();
    }

}
