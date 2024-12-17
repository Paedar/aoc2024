package dev.paedar.aoc.lvl15;

import dev.paedar.aoc.util.Direction;
import dev.paedar.aoc.util.GridInfo;
import dev.paedar.aoc.util.Position;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static dev.paedar.aoc.util.Direction.EAST;
import static dev.paedar.aoc.util.Direction.WEST;

public class Warehouse {

    private final GridInfo gridInfo;

    private final Map<Position, CellContent> grid;

    private final long xGpsWeight;

    private final long yGpsWeight;

    private Position robotPosition;

    private Warehouse(GridInfo gridInfo, Map<Position, CellContent> initialGrid, Position robotPosition, int xGpsWeight, int yGpsWeight) {
        this.gridInfo = gridInfo;
        this.grid = initialGrid;
        this.robotPosition = robotPosition;
        this.xGpsWeight = xGpsWeight;
        this.yGpsWeight = yGpsWeight;
    }

    public static Warehouse ofLines(List<String> lines, int xGpsWeight, int yGpsWeight) {
        var gridInfo = GridInfo.of(lines);
        var grid = new HashMap<Position, CellContent>();
        gridInfo.allInboundsPositions()
                .forEach(p -> {
                    var gridCharacter = gridInfo.charAt(p);
                    var content = switch (gridCharacter) {
                        case '#' -> new Wall();
                        case '[' -> new LeftPartOfBox();
                        case ']' -> new RightPartOfBox();
                        case 'O' -> new SingleSizeBox();
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

        return new Warehouse(gridInfo, grid, robotPosition, xGpsWeight, yGpsWeight);
    }

    public void robotMove(Direction towards) {
        if (tryMove(robotPosition, towards)) {
            this.robotPosition = towards.next(robotPosition);
        }
    }

    private boolean tryMove(Position from, Direction towards) {
        var positionsToMove = new HashSet<Position>();
        var nextPositions = Set.of(from);
        while (!nextPositions.isEmpty()) {
            positionsToMove.addAll(nextPositions);
            nextPositions = nextPositions.stream()
                                           .flatMap(p -> findNextMovingPositions(p, towards))
                                           .collect(Collectors.toSet());
        }

        var allCanMove = positionsToMove.stream()
                                        .map(towards::next)
                                        .map(grid::get)
                                        .noneMatch(Wall.class::isInstance);

        record MoveInstruction(Position newPosition, CellContent content) {}

        if(allCanMove) {
            var instructions = positionsToMove.stream()
                    .map(p -> new MoveInstruction(towards.next(p), grid.get(p)))
                    .collect(Collectors.toSet());
            positionsToMove.forEach(p -> grid.put(p, new Empty()));
            instructions.forEach(i -> grid.put(i.newPosition(), i.content()));
        }

        return allCanMove;
    }

    private Stream<Position> findNextMovingPositions(Position from, Direction towards) {
        var destination = towards.next(from);
        var contentAtDestination = grid.get(destination);
        return switch (towards) {
            case EAST, WEST -> switch (contentAtDestination) {
                case Empty _, Wall _ -> Stream.empty();
                case Movable _ -> Stream.of(destination);
            };
            case NORTH, SOUTH -> switch (contentAtDestination) {
                case Empty _, Wall _ -> Stream.empty();
                case Robot _, SingleSizeBox _ -> Stream.of(destination);
                case LeftPartOfBox _ -> Stream.of(destination, EAST.next(destination));
                case RightPartOfBox _ -> Stream.of(destination, WEST.next(destination));
            };

            default -> Stream.empty(); /* Direction not supported */
        };
    }

    private long goodsPositioningSystem(Position position) {
        var cellContent = grid.getOrDefault(position, new Empty());
        return switch (cellContent) {
            case Empty _, Wall _, Robot _, RightPartOfBox _ -> 0;
            case SingleSizeBox _, LeftPartOfBox _ -> position.x() * xGpsWeight + position.y() * yGpsWeight;
        };
    }

    public long gpsSum() {
        return gridInfo.allInboundsPositions()
                       .mapToLong(this::goodsPositioningSystem)
                       .sum();
    }

    public void visualize() {
        for (int y = 0; y < gridInfo.height(); y++) {
            var sb = new StringBuilder();
            for (int x = 0; x < gridInfo.width(); x++) {
                var p = new Position(x, y);
                var representation = switch (grid.get(p)) {
                    case Empty _ -> '.';
                    case Wall _ -> '#';
                    case SingleSizeBox _ -> 'O';
                    case LeftPartOfBox _ -> '[';
                    case RightPartOfBox _ -> ']';
                    case Robot _ -> '@';
                };
                sb.append(representation);
            }
            System.out.println(sb);
        }
    }

}
