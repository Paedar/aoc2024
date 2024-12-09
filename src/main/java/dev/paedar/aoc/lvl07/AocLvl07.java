package dev.paedar.aoc.lvl07;

import dev.paedar.aoc.util.InputReader;

import java.util.List;

import static dev.paedar.aoc.lvl07.Operator.ADDITION;
import static dev.paedar.aoc.lvl07.Operator.CONCATENATION;
import static dev.paedar.aoc.lvl07.Operator.MULTIPLICATION;
import static dev.paedar.aoc.util.Util.permute;

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

}
