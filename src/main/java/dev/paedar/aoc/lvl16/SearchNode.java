package dev.paedar.aoc.lvl16;

import java.util.stream.Stream;

public record SearchNode(ReindeerState reindeerState, long cost) {

    private static final long COST_TO_MOVE = 1L;

    private static final long COST_TO_ROTATE = 1000L;

    public Stream<SearchNode> nextPossibleStates() {
        var heading = reindeerState.heading();
        var position = reindeerState.position();
        var rotations = heading.perpendicularDirections()
                               .map(h -> new ReindeerState(position, h))
                               .map(r -> new SearchNode(r, cost + COST_TO_ROTATE));

        var forward = Stream.of(new ReindeerState(heading.next(position), heading))
                            .map(r -> new SearchNode(r, cost + COST_TO_MOVE));

        return Stream.concat(rotations, forward);
    }

}
