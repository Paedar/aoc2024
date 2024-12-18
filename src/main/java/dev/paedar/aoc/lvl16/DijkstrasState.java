package dev.paedar.aoc.lvl16;

import java.util.List;
import java.util.Map;
import java.util.Set;

final class DijkstrasState {

    private SearchNode previous;

    private final Map<SearchNode, List<SearchNode>> reversePaths;

    private final Set<ReindeerState> foundStates;

    DijkstrasState(SearchNode previous, Map<SearchNode, List<SearchNode>> reversePaths, Set<ReindeerState> foundStates) {
        this.previous = previous;
        this.reversePaths = reversePaths;
        this.foundStates = foundStates;
    }

    public SearchNode previous() {
        return previous;
    }

    public Map<SearchNode, List<SearchNode>> reversePaths() {
        return reversePaths;
    }

    public Set<ReindeerState> foundStates() {
        return foundStates;
    }

    public void setPrevious(SearchNode previous) {
        this.previous = previous;
    }

}
