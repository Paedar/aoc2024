package dev.paedar.aoc.util;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import static java.nio.file.Files.readAllLines;

public class InputReader {

    private InputReader() {
        // Hide constructor
    }

    public static List<String> readLines(String inputFilename) {
        try {
            var path = fromClasspath(inputFilename);
            return readAllLines(path);
        } catch (IOException | URISyntaxException e) {
            throw new RuntimeException(e);
        }
    }

    public static List<String> readTokens(String inputFilename) {
        var content = readContent(inputFilename);
        return Util.splitToTokens(content);
    }

    public static String readContent(String inputFilename) {
        try {
            var path = fromClasspath(inputFilename);
            return Files.readString(path);
        } catch (IOException | URISyntaxException e) {
            throw new RuntimeException(e);
        }
    }

    private static Path fromClasspath(String inputFilename) throws URISyntaxException {
        return Paths.get(ClassLoader.getSystemResource(inputFilename).toURI());
    }

}
