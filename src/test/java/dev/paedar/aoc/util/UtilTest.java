package dev.paedar.aoc.util;

import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class UtilTest {

    @Test
    void permuteTest() {
        var letters = List.of("A", "B");

        var permutations = Util.permute(letters, 3)
                               .map(it -> String.join("", it))
                               .toList();

        var expected = List.of("AAA",
                               "AAB",
                               "ABA",
                               "ABB",
                               "BAA",
                               "BAB",
                               "BBA",
                               "BBB");
        assertEquals(expected.size(), permutations.size());
        assertTrue(expected.containsAll(permutations));
    }

}