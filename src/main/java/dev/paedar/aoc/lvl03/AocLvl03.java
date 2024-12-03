package dev.paedar.aoc.lvl03;

import dev.paedar.aoc.util.InputReader;

import java.util.regex.Pattern;

public class AocLvl03 {

    private static final Pattern MULTIPLICATION_PATTERN = Pattern.compile("mul\\((?<first>\\d{1,3}),(?<second>\\d{1,3})\\)");

    private static final Pattern ENABLED_MULTIPLICATION_PATTERN = Pattern.compile("(?<identifier>do(?:n't)?|mul)(?:(?<=do(?:n't)?)\\(\\)|(?<=mul)\\((?<first>\\d{1,3}),(?<second>\\d{1,3})\\))");

    public static void main(String[] args) {
        var content = InputReader.readContent("input_03.txt");

        var validMultiplicationSum = sumValidMultiplications(content);
        System.out.println("Sum of valid multiplications: " + validMultiplicationSum);

        var enabledMultiplicationSum = sumEnabledMultiplications(content);
        System.out.println("Sum of enabled multiplications: " + enabledMultiplicationSum);
    }

    public static int sumValidMultiplications(String input) {
        var matcher = MULTIPLICATION_PATTERN.matcher(input);

        var total = 0;
        while (matcher.find()) {
            var firstNum = Integer.parseInt(matcher.group("first"));
            var secondNum = Integer.parseInt(matcher.group("second"));
            total += firstNum * secondNum;
        }
        return total;
    }

    public static int sumEnabledMultiplications(String input) {
        var matcher = ENABLED_MULTIPLICATION_PATTERN.matcher(input);

        var total = 0;
        var enabled = true;
        while (matcher.find()) {
            var action = matcher.group("identifier");
            switch (action) {
                case "do":
                    enabled = true;
                    break;
                case "don't":
                    enabled = false;
                    break;
                case "mul":
                    if (enabled) {
                        var firstNum = Integer.parseInt(matcher.group("first"));
                        var secondNum = Integer.parseInt(matcher.group("second"));
                        total += firstNum * secondNum;
                    }
                    break;
                default:
                    throw new IllegalStateException("Unexpected value: " + action);
            }
        }
        return total;
    }

}
