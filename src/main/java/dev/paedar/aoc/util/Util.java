package dev.paedar.aoc.util;

import java.util.Arrays;
import java.util.List;

public class Util {

    private Util() {
        // Hide the constructor
    }

    public static List<String> splitToTokens(String content) {
        return Arrays.asList(content.split("\\W+"));
    }

}
