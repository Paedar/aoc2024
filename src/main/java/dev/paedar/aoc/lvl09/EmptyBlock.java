package dev.paedar.aoc.lvl09;

public final class EmptyBlock implements Block {

    @Override
    public boolean equals(Object other) {
        return other instanceof EmptyBlock;
    }

}
