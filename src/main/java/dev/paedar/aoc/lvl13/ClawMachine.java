package dev.paedar.aoc.lvl13;

import java.util.List;
import java.util.Optional;

public record ClawMachine(Vec2 aButtonDisplacement, Vec2 bButtonDisplacement, Vec2 prizeLocation) {

    public static int CLAW_MACHINE_DESCRIPTOR_TOKEN_COUNT = 17;

    public static ClawMachine ofTokens(List<String> tokens) {
        return ofTokens(tokens, 0);
    }

    public static ClawMachine ofTokens(List<String> tokens, long prizeLocationOffset) {
        if (tokens.size() != CLAW_MACHINE_DESCRIPTOR_TOKEN_COUNT) {
            throw new IllegalArgumentException("Expecting 17 tokens, got " + tokens.size());
        }
        var aButtonDisplacementX = Integer.parseInt(tokens.get(3));
        var aButtonDisplacementY = Integer.parseInt(tokens.get(5));
        var aButtonDisplacement = new Vec2(aButtonDisplacementX, aButtonDisplacementY);
        var bButtonDisplacementX = Integer.parseInt(tokens.get(9));
        var bButtonDisplacementY = Integer.parseInt(tokens.get(11));
        var bButtonDisplacement = new Vec2(bButtonDisplacementX, bButtonDisplacementY);
        var prizeLocationX = Integer.parseInt(tokens.get(14)) + prizeLocationOffset;
        var prizeLocationY = Integer.parseInt(tokens.get(16)) + prizeLocationOffset;
        var prizeLocation = new Vec2(prizeLocationX, prizeLocationY);

        return new ClawMachine(aButtonDisplacement, bButtonDisplacement, prizeLocation);
    }

    public Optional<Long> costToWin(int aButtonCost, int bButtonCost) {
        return findSolution().map(s -> aButtonCost * s.x() + bButtonCost * s.y());
    }

    public Optional<Vec2> findSolution() {
        var operationMatrix = new Matrix2x2(aButtonDisplacement.x(), bButtonDisplacement.x(), aButtonDisplacement.y(), bButtonDisplacement.y());
        return Optional.of(operationMatrix)
                       .filter(Matrix2x2::isInvertible)
                       .map(Matrix2x2::invert)
                       .map(m -> m.mult(prizeLocation))
                       .filter(solution -> operationMatrix.mult(solution).equals(prizeLocation));
                /*
                Note that it is necessary to verify the solution because matrix inversion involves floating point math, but we can only press
                buttons discrete amounts of times. Any non-integer solutions will be rounded and denied by this last verification
                 */
    }

}
