package dev.paedar.aoc.util;

import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.stream.Collectors;

import static dev.paedar.aoc.util.Util.gcd;
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

    @Test
    void gcdTest() {
        assertEquals(5, gcd(5,10));
        assertEquals(3, gcd(3,27));
        assertEquals(10, gcd(20,50));
        /*
        The following shows this method is not very consistent for negative values.
         */
        assertEquals(2, gcd(-2,2));
        assertEquals(-5, gcd(-5,10));
        assertEquals(-5, gcd(10,-5));
        assertEquals(-2, gcd(-2,-2));
        assertEquals(-5, gcd(-5, -10));
    }

}