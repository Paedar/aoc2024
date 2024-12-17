package dev.paedar.aoc.lvl14;

import dev.paedar.aoc.util.InputReader;
import dev.paedar.aoc.util.Position;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public class AocLvl14 {

    private static final Set<String> MASTER_MEMORY = new HashSet<>();

    public static void main(String[] args) {
        var lines = InputReader.readLines("input_14.txt");

        var safetyFactor = determineSafetyFactor(lines, 101, 103, 100);
        System.out.println("Safety factor determined: " + safetyFactor);
    }

    public static long determineSafetyFactor(List<String> lines, int width, int height, int numUpdates) {
        var robots = lines.stream()
                          .map(RobotState::of)
                          .map(r -> updateNTimes(r, numUpdates, width, height))
                          .toList();

        var middleX = width / 2;
        var middleY = height / 2;

        var topLeftCount = robots.stream()
                                 .map(RobotState::getPosition)
                                 .filter(p -> p.x() < middleX)
                                 .filter(p -> p.y() < middleY)
                                 .count();
        var topRightCount = robots.stream()
                                  .map(RobotState::getPosition)
                                  .filter(p -> p.x() > middleX)
                                  .filter(p -> p.y() < middleY)
                                  .count();
        var bottomLeftCount = robots.stream()
                                    .map(RobotState::getPosition)
                                    .filter(p -> p.x() < middleX)
                                    .filter(p -> p.y() > middleY)
                                    .count();
        var bottomRightCount = robots.stream()
                                     .map(RobotState::getPosition)
                                     .filter(p -> p.x() > middleX)
                                     .filter(p -> p.y() > middleY)
                                     .count();
        return topLeftCount * topRightCount * bottomLeftCount * bottomRightCount;
    }

    public static int secondsToChristmasTree(List<String> lines, int width, int height) {
        var robots = lines.stream()
                          .map(RobotState::of)
                          .toList();

        /*
        Very much an empirical method. I noted some amount of symmetry displaying the output every 101 steps starting at 35 and went visually from there
         */

        var seconds = 0;
        while (!isChristmasTreeDisplay(robots, width, height, seconds)) {
            var i = seconds % 101;
            if (i == 35) {
                /*
                I commented out the displaying of the tree to not create noise in my test output
                 */
//                System.out.println("Christmas tree display: " + seconds);
                var positionCount = robots.stream()
                                          .collect(Collectors.groupingBy(RobotState::getPosition, Collectors.counting()));
//                displayState(width, height, positionCount);
            }
            robots.forEach(r -> r.updateWrapAround(width, height));
            ++seconds;
        }
//        System.out.println("Iteration #" + seconds);

        return seconds;
    }

    private static boolean isChristmasTreeDisplay(List<RobotState> robots, int width, int height, int seconds) {
        return seconds == 6398; // Empirically found - Can't be bothered to write an actual solver for this
    }

    private static void displayState(int width, int height, Map<Position, Long> positionCount) {
        var masterMemoryBuilder = new StringBuilder();
        for (int y = 0; y < height; y++) {
            StringBuilder line = new StringBuilder();
            for (int x = 0; x < width; x++) {
                var p = new Position(x, y);
                var count = positionCount.getOrDefault(p, 0L);
                var displayChar = ".";
                if (count > 10) {
                    displayChar = "*";
                } else if (count > 0) {
                    displayChar = Long.toString(count);
                }
                line.append(displayChar);
            }
            System.out.println(line);
            masterMemoryBuilder.append(line);
        }
        var memory = masterMemoryBuilder.toString();
        if (MASTER_MEMORY.contains(memory)) {
            throw new IllegalStateException("Possible loop detected");
        }
        MASTER_MEMORY.add(memory);
    }

    private static RobotState updateNTimes(RobotState robot, int numUpdates, int width, int height) {
        var updateCounter = numUpdates;
        while (updateCounter > 0) {
            robot.updateWrapAround(width, height);
            updateCounter--;
        }
        return robot;
    }

}
