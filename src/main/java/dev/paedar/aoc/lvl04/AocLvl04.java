package dev.paedar.aoc.lvl04;

import dev.paedar.aoc.util.Direction;
import dev.paedar.aoc.util.GridInfo;
import dev.paedar.aoc.util.InputReader;
import dev.paedar.aoc.util.Position;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import static dev.paedar.aoc.util.Direction.BOTTOM_LEFT;
import static dev.paedar.aoc.util.Direction.BOTTOM_RIGHT;
import static dev.paedar.aoc.util.Direction.TOP_LEFT;
import static dev.paedar.aoc.util.Direction.TOP_RIGHT;
import static java.util.function.Function.identity;

public class AocLvl04 {

    public static void main(String[] args) {
        var lines = InputReader.readLines("input_04.txt");

        var wordToFind = "XMAS";
        var countXmas = countWords(wordToFind, lines);
        System.out.println("Amount of times word %s found: %d".formatted(wordToFind, countXmas));

        var crossWordToFind = "MAS";
        var countCrossMas = countCrossWords(crossWordToFind, lines);
        System.out.println("Amount of times word %s found in cross format: %d".formatted(crossWordToFind, countCrossMas));
    }

    public static long countCrossWords(String crossWordToFind, List<String> lines) {
        var wordToFindLength = crossWordToFind.length();
        if (wordToFindLength % 2 == 0) {
            throw new IllegalArgumentException("Cannot find middle of the word.");
        }

        var gridInfo = getGridInfo(crossWordToFind, lines);

        var pivotCharacterIndex = wordToFindLength / 2;
        var pivotCharacter = crossWordToFind.charAt(pivotCharacterIndex);
        var diagonals = List.of(List.of(TOP_LEFT, BOTTOM_RIGHT),
                                List.of(TOP_RIGHT, BOTTOM_LEFT));

        var investigatePositions = gridInfo.characterPositions().get(pivotCharacter);

        return investigatePositions.stream()
                                   .filter(position -> diagonals.stream()
                                                                .allMatch(diagonal -> diagonal.stream()
                                                                                              .map(direction -> wordWithMiddleAt(lines, position, direction, wordToFindLength))
                                                                                              .anyMatch(crossWordToFind::equals)))
                                   .count();
    }

    public static long countWords(String wordToFind, List<String> lines) {
        var gridInfo = getGridInfo(wordToFind, lines);

        return Stream.of(Direction.values())
                     .mapToLong(direction -> countWordsInDirection(wordToFind, direction, gridInfo))
                     .sum();
    }

    private static Lvl04GridInfo getGridInfo(String wordToFind, List<String> lines) {
        var basicGridInfo = GridInfo.of(lines);

        var allPositions = allGridPositions(basicGridInfo.height(), basicGridInfo.width());
        var characterPositions = getCharacterPositions(wordToFind, lines, allPositions);

        return new Lvl04GridInfo(basicGridInfo, characterPositions);
    }

    private static Map<Character, Set<Position>> getCharacterPositions(String wordToFind, List<String> lines, Set<Position> allPositions) {
        return wordToFind.chars()
                         .distinct()
                         .mapToObj(it -> (char) it)
                         .collect(Collectors.toMap(identity(), it -> characterPositions(it, lines, allPositions)));
    }

    private static Set<Position> allGridPositions(int height, int width) {
        return IntStream.range(0, height)
                        .mapToObj(i -> IntStream.range(0, width)
                                                .mapToObj(j -> new Position(i, j))
                                                .collect(Collectors.toSet())
                        )
                        .flatMap(Set::stream)
                        .collect(Collectors.toSet());
    }

    private static long countWordsInDirection(String wordToFind, Direction direction, Lvl04GridInfo gridInfo) {
        var wordLength = wordToFind.length();
        var characterPositions = gridInfo.characterPositions();
        var nextPositions = characterPositions.get(wordToFind.charAt(0));

        return nextPositions.stream()
                            .map(p -> wordAt(gridInfo.basicGridInfo().lines(), p, direction, wordLength))
                            .filter(wordToFind::equals)
                            .count();
    }

    private static Set<Position> characterPositions(char character, List<String> lines, Set<Position> allPositions) {
        return allPositions.stream()
                           .filter(p -> Character.valueOf(character).equals(getAt(lines, p)))
                           .collect(Collectors.toSet());
    }

    private static Character getAt(List<String> lines, Position p) {
        if (p.x() >= lines.size() || p.x() < 0) {
            return null;
        }
        var line = lines.get(p.x());
        if (p.y() >= line.length() || p.y() < 0) {
            return null;
        }
        return line.charAt(p.y());
    }

    private static String wordAt(List<String> lines, Position position, Direction direction, int wordLength) {
        var wordBuilder = new StringBuilder();
        var next = position;
        for (int i = 0; i < wordLength; i++) {
            var nextChar = getAt(lines, next);
            if (nextChar == null) {
                nextChar = ' ';
            }
            wordBuilder.append(nextChar);
            next = direction.next(next);
        }
        return wordBuilder.toString();
    }

    private static String wordWithMiddleAt(List<String> lines, Position position, Direction direction, int wordLength) {
        var offset = wordLength / 2;
        var startPosition = position;
        for (int i = 0; i < offset; i++) {
            startPosition = direction.previous(startPosition);
        }
        return wordAt(lines, startPosition, direction, wordLength);
    }

}
