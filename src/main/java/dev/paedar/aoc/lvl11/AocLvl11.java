package dev.paedar.aoc.lvl11;

import dev.paedar.aoc.util.InputReader;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.LongStream;
import java.util.stream.Stream;

public class AocLvl11 {

    private record LookupKey(long number, int splits) {}

    private static final Map<LookupKey, Long> LOOKUP_TABLE = new HashMap<>();

    public static void main(String[] args) {
        var tokens = InputReader.readTokens("input_11.txt");

        var numberOfStonesAfter25Blinks = numStonesAfterNUpdates(tokens, 25);
        System.out.println("I blinked 25 times and now there's this many stones: " + numberOfStonesAfter25Blinks);

        var numberOfStonesAfter75Blinks = numStonesAfterNUpdates(tokens, 75);
        System.out.println("I blinked 75 times and now there's this many stones: " + numberOfStonesAfter75Blinks);
    }

    public static long numStonesAfterNUpdates(List<String> tokens, int n) {
        return tokens.stream()
                     .mapToLong(Long::parseLong)
                     .map(stoneValue -> numStonesAfterNBlinks(stoneValue, n))
                     .sum();
    }

    private static long numStonesAfterNBlinks(long stoneValue, int n) {
        if (n == 0) {
            return 1;
        }
        var something = new LookupKey(stoneValue, n);
        var knownValue = LOOKUP_TABLE.get(something);
        if (knownValue == null) {
            var calculatedValue = blinkOnce(stoneValue)
                                          .map(it -> numStonesAfterNBlinks(it, n - 1))
                                          .sum();
            LOOKUP_TABLE.put(something, calculatedValue);
            return calculatedValue;
        }
        return knownValue;
    }

    private static LongStream blinkOnce(long stoneValue) {
        if (stoneValue == 0) {
            return LongStream.of(1);
        } else if (Long.toString(stoneValue).length() % 2 == 0) {
            var stringRep = Long.toString(stoneValue);
            var splitAt = stringRep.length() / 2;
            return Stream.of(stringRep.substring(0, splitAt), stringRep.substring(splitAt))
                         .mapToLong(Long::parseLong);
        } else {
            return LongStream.of(stoneValue * 2024);
        }
    }

}
