package dev.paedar.aoc.lvl07;

import dev.paedar.aoc.util.InputReader;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

import static dev.paedar.aoc.lvl07.Operator.ADDITION;
import static dev.paedar.aoc.lvl07.Operator.CONCATENATION;
import static dev.paedar.aoc.lvl07.Operator.MULTIPLICATION;

public class AocLvl07 {
    public static void main(String[] args) {
        var lines = InputReader.readLines("input_07.txt");

        var solvableEquationSum = sumSolvableEquations(lines);
        System.out.println("Sum of solvable equations, using ADDITION, MULTIPLICATION: " + solvableEquationSum);

        var solvableWithConcationEquationSum = sumSolvableEquationsWithConcatenationOperator(lines);
        System.out.println("Sum of solvable equations, using ADDITION, MULTIPLICATION, CONCATENATION: " + solvableWithConcationEquationSum);
    }

    public static long sumSolvableEquations(List<String> lines) {
        var equations = lines.stream()
                                .map(BlankEquation::of)
                                .toList();

        return equations.stream()
                       .filter(blankEquation -> isSolvable(blankEquation, List.of(MULTIPLICATION, ADDITION)))
                       .mapToLong(BlankEquation::solution)
                       .sum();
    }

    public static long sumSolvableEquationsWithConcatenationOperator(List<String> lines) {
        var equations = lines.stream()
                             .map(BlankEquation::of)
                             .toList();

        return equations.stream()
                        .filter(blankEquation -> isSolvable(blankEquation, List.of(MULTIPLICATION, ADDITION, CONCATENATION)))
                        .mapToLong(BlankEquation::solution)
                        .sum();
    }

    private static boolean isSolvable(BlankEquation blankEquation, List<Operator> withOperators) {
        var requiredNumberOfOperatorsForEquation = blankEquation.numbers().size() - 1;
        return permute(withOperators, requiredNumberOfOperatorsForEquation)
                       .anyMatch(blankEquation::evaluatesWith);
    }

    private static Stream<List<Operator>> permute(List<Operator> permuteWith, int times) {
        if(times <= 0) {
            throw new IllegalArgumentException("times must be greater than zero");
        }
        if (times == 1) {
            return permuteWith.stream().map(Arrays::asList);
        }
        return permute(permuteWith, times - 1)
                       .flatMap(base -> permute(base, permuteWith));
    }

    private static Stream<List<Operator>> permute(List<Operator> base, List<Operator> permutations) {
        return permutations.stream()
                       .map(operator -> {
                           var permutation = new ArrayList<>(base);
                           permutation.add(operator);
                           return permutation;
                       });
    }

}
