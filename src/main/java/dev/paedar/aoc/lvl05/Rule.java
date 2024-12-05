package dev.paedar.aoc.lvl05;

import java.util.List;

public record Rule(int first, int second) {

    public boolean appliesTo(List<Integer> printRun) {
        return printRun.contains(first) && printRun.contains(second);
    }

    public boolean validateOn(List<Integer> printRun) {
        var firstPosition = printRun.indexOf(first);
        var secondPosition = printRun.indexOf(second);
        if (firstPosition == -1 || secondPosition == -1) {
            throw new IllegalArgumentException("Rule should not apply!");
        }
        return firstPosition < secondPosition;
    }

}
