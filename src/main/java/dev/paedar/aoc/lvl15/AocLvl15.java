package dev.paedar.aoc.lvl15;

import dev.paedar.aoc.util.Direction;
import dev.paedar.aoc.util.InputReader;

import java.util.List;
import java.util.stream.Stream;

public class AocLvl15 {

    public static void main(String[] args) {
        var lines = InputReader.readLines("input_15.txt");

        var sumOfGpsAfterRobotMovement = sumGpsAfterRobotMovement(lines);
        System.out.println("GPS sum after robot completes movement: " + sumOfGpsAfterRobotMovement);
    }

    public static long sumGpsAfterRobotMovement(List<String> lines) {
        var gridLines = lines.stream()
                             .filter(line -> line.startsWith("#"))
                             .toList();

        var warehouse = Warehouse.ofLines(gridLines);

        System.out.println("Initial warehouse:");
//        warehouse.visualize();

        lines.stream()
             .filter(line -> Stream.of("<", "v", ">", "^")
                                   .anyMatch(line::startsWith))
             .flatMapToInt(String::chars)
             .mapToObj(Direction::ofCharacter)
             .forEach(towards -> {
                 warehouse.robotMove(towards);
//                 System.out.println("Robot moves: " + towards);
//                 warehouse.visualize();
             });

        return warehouse.gpsSum();
    }

}
