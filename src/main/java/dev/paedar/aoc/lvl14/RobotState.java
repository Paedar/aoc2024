package dev.paedar.aoc.lvl14;

import dev.paedar.aoc.util.Position;

import java.util.regex.Pattern;

public class RobotState {

    private static final Pattern PATTERN = Pattern.compile("^p=(?<px>-?\\d+),(?<py>-?\\d+) v=(?<vx>-?\\d+),(?<vy>-?\\d+)$");

    private Position position;
    private final Position velocity;

    private RobotState(Position position, Position velocity) {
        this.position = position;
        this.velocity = velocity;
    }

    public static RobotState of(String line) {
        var matcher = PATTERN.matcher(line);
        if (!matcher.matches()) {
            throw new IllegalArgumentException("Invalid line format");
        }
        var px = Integer.parseInt(matcher.group("px"));
        var py = Integer.parseInt(matcher.group("py"));
        var vx = Integer.parseInt(matcher.group("vx"));
        var vy = Integer.parseInt(matcher.group("vy"));

        var position = new Position(px, py);
        var velocity = new Position(vx, vy);

        return new RobotState(position, velocity);
    }

    public void updateWrapAround(int width, int height) {
        var newX = updateWithWrapAround(position.x(), velocity.x(), width);
        var newY = updateWithWrapAround(position.y(), velocity.y(), height);
        this.position = new Position(newX, newY);
    }

    private int updateWithWrapAround(int start, int step, int boundary) {
        var end = start + step;
        while(end < 0) {
            end += boundary;
        }
        return end % boundary;
    }

    public Position getPosition() {
        return position;
    }

}
