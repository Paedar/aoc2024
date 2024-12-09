package dev.paedar.aoc.util.gatherers;

import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;
import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.assertEquals;

class GroupToListGathererTest {

    @Test
    void simpleGathererTest() {
        var someString = "AAPNOOTMIES";

        var gatherer = GroupToListGatherer.of(i -> someString.substring(i, i+1).toLowerCase(), someString::charAt);

        var grouped = IntStream.range(0, someString.length())
                              .mapToObj(i -> i)
                              .gather(gatherer)
                              .findFirst()
                              .orElseThrow(RuntimeException::new);

        var expected = Map.of("a", List.of('A', 'A'),
                              "p", List.of('P'),
                              "n", List.of('N'),
                              "o", List.of('O', 'O'),
                              "t", List.of('T'),
                              "m", List.of('M'),
                              "i", List.of('I'),
                              "e", List.of('E'),
                              "s", List.of('S')
                              );

        assertEquals(expected, grouped);
    }

}