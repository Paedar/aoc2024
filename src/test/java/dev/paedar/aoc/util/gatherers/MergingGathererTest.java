package dev.paedar.aoc.util.gatherers;

import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;

class MergingGathererTest {

    @Test
    void testGathererToSumItems() {
        var sum = Stream.of(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20)
                        .gather(MergingGatherer.of(Integer::sum))
                        .toList();
        var expected = List.of(210);
        assertEquals(expected, sum);
    }

    @Test
    void testSumEqualTerms() {
        var mergedIntegers = Stream.of(1, 1, 1, 2, 2, 2, 1, 1, 3, 7, 18)
                                   .gather(MergingGatherer.of((i, j) -> Objects.equals(i, j), Integer::sum))
                                   .collect(Collectors.toSet());

        var expected = Set.of(1, 2, 3, 4, 7, 18);
        assertEquals(expected, mergedIntegers);
    }

    @Test
    void testSumMultipleTermsDoesNotCascade() {
        var mergedIntegers = Stream.of(1, 13, 3, 5, 4)
                                   .gather(MergingGatherer.of((i, j) -> Math.abs(i - j) < 2, Integer::sum))
                                   .collect(Collectors.toSet());

        var expected = Set.of(1, 12, 13); // Note that we don't expect merges to cascade using this gatherer. Maybe we should?
        assertEquals(expected, mergedIntegers);
    }

}