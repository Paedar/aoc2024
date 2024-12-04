package dev.paedar.aoc.lvl04;

import java.util.List;
import java.util.Map;
import java.util.Set;

record GridInfo(List<String> lines, Map<Character, Set<Position>> characterPositions, int height, int width) {}
