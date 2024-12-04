package dev.paedar.aoc.lvl04;

import java.util.Map;
import java.util.Set;

record GridInfo(Set<Position> positions, Map<Character, Set<Position>> characterPositions, int height, int width) {}
