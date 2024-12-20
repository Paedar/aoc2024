package dev.paedar.aoc.lvl20;

import dev.paedar.aoc.util.Position;

import java.util.Map;

record DijkstraState(Map<Position, Long> costMap, Map<Position, Long> investigationQueue) {

    boolean positionReached(Position end) {
        return costMap.containsKey(end);
    }

    boolean queueEmpty() {
        return investigationQueue.isEmpty();
    }

}
