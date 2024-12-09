package dev.paedar.aoc.util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

public class Util {

    private Util() {
        // Hide the constructor
    }

    public static List<String> splitToTokens(String content) {
        return Arrays.asList(content.split("\\W+"));
    }

    public static <T> Stream<List<T>> permute(List<T> permuteWith, int times) {
        if (times <= 0) {
            throw new IllegalArgumentException("times must be greater than zero");
        }
        if (times == 1) {
            return permuteWith.stream().map(Arrays::asList);
        }
        return permute(permuteWith, times - 1)
                       .flatMap(base -> permute(base, permuteWith));
    }

    private static <T> Stream<List<T>> permute(List<T> base, List<T> permutations) {
        return permutations.stream()
                           .map(operator -> {
                               var permutation = new ArrayList<>(base);
                               permutation.add(operator);
                               return permutation;
                           });
    }

    public static int gcd(int a, int b) {
        /*
        https://en.wikipedia.org/wiki/Euclidean_algorithm
         */
        if(b == 0) return a;
        return gcd(b, a % b);
    }

}
