package dev.paedar.aoc.util.gatherers;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class AllCombinationsGathererTest {

    private static final List<String> ABC = List.of("A", "B", "C");

    private record Tuple<F, S>(F first, S second) {}

    @Test
    void bothDirectionsDontCombineSelf() {
        var actual = ABC.stream()
                        .gather(AllCombinationsGatherer.combining(Tuple::new, true, false))
                        .toList();

        var expected = List.of(
                new Tuple<>("A", "B"),
                new Tuple<>("B", "A"),
                new Tuple<>("A", "C"),
                new Tuple<>("C", "A"),
                new Tuple<>("B", "C"),
                new Tuple<>("C", "B")
        );

        assertEquals(expected.size(), actual.size());
        assertTrue(expected.containsAll(actual));
    }

    @Test
    void oneWayDontCombineSelf() {
        var actual = ABC.stream()
                        .gather(AllCombinationsGatherer.combining(Tuple::new, false, false))
                        .toList();

        var expected = List.of(
                new Tuple<>("A", "B"),
                new Tuple<>("A", "C"),
                new Tuple<>("B", "C")
        );

        assertEquals(expected.size(), actual.size());
        assertTrue(expected.containsAll(actual));
    }
    @Test
    void bothDirectionsCombineSelf() {
        var actual = ABC.stream()
                        .gather(AllCombinationsGatherer.combining(Tuple::new, true, true))
                        .toList();

        var expected = List.of(
                new Tuple<>("A", "B"),
                new Tuple<>("B", "A"),
                new Tuple<>("A", "C"),
                new Tuple<>("C", "A"),
                new Tuple<>("B", "C"),
                new Tuple<>("C", "B"),
                new Tuple<>("A", "A"),
                new Tuple<>("B", "B"),
                new Tuple<>("C", "C")
        );

        assertEquals(expected.size(), actual.size());
        assertTrue(expected.containsAll(actual));
    }

    @Test
    void oneWayCombineSelf() {
        var actual = ABC.stream()
                        .gather(AllCombinationsGatherer.combining(Tuple::new, false, true))
                        .toList();

        var expected = List.of(
                new Tuple<>("A", "B"),
                new Tuple<>("A", "C"),
                new Tuple<>("B", "C"),
                new Tuple<>("A", "A"),
                new Tuple<>("B", "B"),
                new Tuple<>("C", "C")
        );

        assertEquals(expected.size(), actual.size());
        assertTrue(expected.containsAll(actual));
    }

}
