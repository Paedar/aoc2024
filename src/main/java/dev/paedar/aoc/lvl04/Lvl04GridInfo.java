package dev.paedar.aoc.lvl04;

import dev.paedar.aoc.util.GridInfo;
import dev.paedar.aoc.util.Position;

import java.util.Map;
import java.util.Set;

public record Lvl04GridInfo(GridInfo basicGridInfo, Map<Character, Set<Position>> characterPositions) {
}
