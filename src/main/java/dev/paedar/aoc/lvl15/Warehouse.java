package dev.paedar.aoc.lvl15;

import dev.paedar.aoc.util.Direction;
import dev.paedar.aoc.util.GridInfo;
import dev.paedar.aoc.util.Position;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Warehouse {

    private final GridInfo gridInfo;

    private final Map<Position, CellContent> grid;

    private Position robotPosition;

    private Warehouse(GridInfo gridInfo, Map<Position, CellContent> initialGrid, Position robotPosition) {
        this.gridInfo = gridInfo;
        this.grid = initialGrid;
        this.robotPosition = robotPosition;
    }

    public static Warehouse ofLines(List<String> lines) {
        var gridInfo = GridInfo.of(lines);
        var grid = new HashMap<Position, CellContent>();
        gridInfo.allInboundsPositions()
                .forEach(p -> {
                    var gridCharacter = gridInfo.charAt(p);
                    var content = switch (gridCharacter) {
                        case '#' -> new Wall();
                        case 'O' -> new Box();
                        case '.' -> new Empty();
                        case '@' -> new Robot();
                        default -> throw new IllegalStateException("Unexpected value: " + gridCharacter);
                    };
                    grid.put(p, content);
                });

        var robotPosition = grid.entrySet().stream()
                                .filter(e -> e.getValue() instanceof Robot)
                                .map(Map.Entry::getKey)
                                .findFirst()
                                .orElseThrow(IllegalStateException::new);

        return new Warehouse(gridInfo, grid, robotPosition);
    }

    public void robotMove(Direction towards) {
        if(tryMove(robotPosition, towards)) {
            this.robotPosition = towards.next(robotPosition);
        }
    }

    private boolean tryMove(Position from, Direction towards) {
        var destination = towards.next(from);
        if (!grid.containsKey(destination)) {
            throw new IllegalArgumentException("Invalid destination: " + destination);
        }
        if (!grid.containsKey(from)) {
            throw new IllegalArgumentException("Invalid origin: " + destination);
        }
        var contentAtDestination = grid.get(destination);
        var contentAtOrigin = grid.get(from);
        var canMove = switch (contentAtDestination) {
            case Empty _ -> true;
            case Movable _ -> tryMove(destination, towards);
            case Wall _ -> false;
        };
        if (canMove) {
            grid.put(destination, contentAtOrigin);
            grid.put(from, new Empty());
        }
        return canMove;
    }

    private long goodsPositioningSystem(Position position) {
        var cellContent = grid.getOrDefault(position, new Empty());
        return switch (cellContent) {
            case Empty _, Wall _, Robot _ -> 0;
            case Box _ -> position.x() + position.y() * 100L;
        };
    }

    public long gpsSum() {
        return gridInfo.allInboundsPositions()
                       .mapToLong(this::goodsPositioningSystem)
                       .sum();
    }

    public void visualize() {
        for(int y = 0; y < gridInfo.height(); y++) {
            var sb = new StringBuilder();
            for(int x = 0; x < gridInfo.width(); x++) {
                var p = new Position(x, y);
                var representation = switch(grid.get(p)) {
                    case Empty _ -> '.';
                    case Wall _ -> '#';
                    case Box _ -> 'O';
                    case Robot _ -> '@';
                };
                sb.append(representation);
            }
            System.out.println(sb);
        }
    }

}
