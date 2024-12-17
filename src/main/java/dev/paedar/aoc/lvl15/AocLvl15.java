package dev.paedar.aoc.lvl15;

import dev.paedar.aoc.util.Direction;
import dev.paedar.aoc.util.InputReader;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class AocLvl15 {

    private static final boolean VISUALIZE = false;

    public static void main(String[] args) {
        var lines = InputReader.readLines("input_15.txt");

        var sumOfGpsAfterRobotMovement = sumGpsAfterRobotMovement(lines);
        System.out.println("GPS sum after robot completes movement: " + sumOfGpsAfterRobotMovement);

        var largeWarehouseSumOfGpsAfterRobotMovement = largeWarehouseSumGpsAfterRobotMovement(lines);
        System.out.println("GPS sum after robot completes movement in the larger warehouse: " + largeWarehouseSumOfGpsAfterRobotMovement);
    }

    public static long largeWarehouseSumGpsAfterRobotMovement(List<String> lines) {
        var gridLines = getGridLines(lines)
                                .map(AocLvl15::adjustForLargeWarehouse);
        var robotMovement = getRobotMovementLines(lines);

        var adjustedLines = Stream.concat(gridLines, robotMovement).toList();
        return sumGpsAfterRobotMovement(adjustedLines);
    }

    private static String adjustForLargeWarehouse(String line) {
        return line.chars()
                   .mapToObj(c -> switch (c) {
                       case '#' -> "##";
                       case '.' -> "..";
                       case 'O' -> "[]";
                       case '@' -> "@.";
                       default -> throw new IllegalStateException("Unexpected value: " + c);
                   })
                   .collect(Collectors.joining());
    }

    public static long sumGpsAfterRobotMovement(List<String> lines) {
        var gridLines = getGridLines(lines)
                                .toList();

        var warehouse = Warehouse.ofLines(gridLines, 1, 100);

        if(VISUALIZE) {
            System.out.println("Initial warehouse:");
            warehouse.visualize();
        }

        getRobotMovementLines(lines)
                .flatMapToInt(String::chars)
                .mapToObj(Direction::ofCharacter)
                .forEach(towards -> {
                    warehouse.robotMove(towards);
                    if(VISUALIZE) {
                        System.out.println("Robot moves: " + towards);
                        warehouse.visualize();
                    }
                });

        return warehouse.gpsSum();
    }

    private static Stream<String> getRobotMovementLines(List<String> lines) {
        return lines.stream()
                    .filter(line -> Stream.of("<", "v", ">", "^")
                                          .anyMatch(line::startsWith));
    }

    private static Stream<String> getGridLines(List<String> lines) {
        return lines.stream()
                    .filter(line -> line.startsWith("#"));
    }

}
