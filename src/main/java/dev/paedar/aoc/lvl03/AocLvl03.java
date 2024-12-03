package dev.paedar.aoc.lvl03;

import dev.paedar.aoc.util.InputReader;

import java.util.regex.Pattern;

public class AocLvl03 {

    private static final Pattern MULTIPLICATION_PATTERN = Pattern.compile("mul\\((\\d{1,3}),(\\d{1,3})\\)");

    public static void main(String[] args) {
        var content = InputReader.readContent("input_03.txt");

        var validMultiplicationSum = sumValidMultiplications(content);
        System.out.println("Sum of valid multiplications: " + validMultiplicationSum);
    }

    public static int sumValidMultiplications(String input) {
        var matcher = MULTIPLICATION_PATTERN.matcher(input);

        var total = 0;
        while (matcher.find()) {
            var firstNum = Integer.parseInt(matcher.group(1));
            var secondNum = Integer.parseInt(matcher.group(2));
            total += firstNum * secondNum;
        }
        return total;
    }
}
