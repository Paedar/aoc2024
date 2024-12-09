package dev.paedar.aoc.lvl07;

import dev.paedar.aoc.util.Util;

import java.util.List;

public record BlankEquation(long solution, List<Long> numbers) {

    public static BlankEquation of(String line) {
        var tokens = Util.splitToTokens(line);
        var solution = Long.parseLong(tokens.getFirst());
        var numbers = tokens.stream()
                            .skip(1)
                            .map(Long::parseLong)
                            .toList();
        return new BlankEquation(solution, numbers);
    }

    public boolean evaluatesWith(List<Operator> operators) {
        if(operators.size() != numbers.size() - 1) {
            throw new IllegalArgumentException("Number of operators does not fit expectations");
        }

        var tally = numbers.getFirst();
        for(int i = 0; i < operators.size(); i++) {
            var operator = operators.get(i);
            var nextNumber = numbers.get(i + 1);
            tally = operator.apply(tally, nextNumber);
        }

        return tally == solution;
    }

}
