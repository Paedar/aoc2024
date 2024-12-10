package dev.paedar.aoc.lvl09;

import dev.paedar.aoc.util.InputReader;

public class AocLvl09 {

    public static void main(String[] args) {
        var content = InputReader.readContent("input_09.txt");

        var checksum = compactedFileSystemChecksum(content);
        System.out.println("Checksum after compacting" + checksum);
    }

    public static long compactedFileSystemChecksum(String content) {
        var fileSystem = FileSystem.ofDiskmap(content);
        fileSystem.compact();
        return fileSystem.checksum();
    }

}
