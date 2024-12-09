package dev.paedar.aoc.lvl07;

public enum Operator {
    ADDITION,
    MULTIPLICATION,
    CONCATENATION;

    public long apply(long a, long b) {
        return switch(this) {
            case ADDITION -> a + b;
            case MULTIPLICATION -> a * b;
            case CONCATENATION -> Long.parseLong(Long.toString(a) + Long.toString(b));
        };
    }
}
