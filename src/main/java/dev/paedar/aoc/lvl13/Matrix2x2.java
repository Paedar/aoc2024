package dev.paedar.aoc.lvl13;

public record Matrix2x2(long a, long b, long c, long d, long divisor) {

    public Matrix2x2(long a, long b, long c, long d) {
        this(a, b, c, d, 1);
    }

    public long determinant() {
        return a * d - b * c;
    }

    public boolean isInvertible() {
        return determinant() != 0;
    }

    public Matrix2x2 invert() {
        var det = determinant();
        var newA = d;
        var newB = -b;
        var newC = -c;
        var newD = a;
        return new Matrix2x2(newA, newB, newC, newD, divisor * det);
    }

    public Vec2 mult(Vec2 p) {
        var x = (a * p.x() + b * p.y()) / divisor;
        var y = (c * p.x() + d * p.y()) / divisor;

        /*
        Note that the longeger division here performs rounding, making this inaccurate if the actual result would not be composed of just longegers
         */

        return new Vec2(x, y);
    }

}
