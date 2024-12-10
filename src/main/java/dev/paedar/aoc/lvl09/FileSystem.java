package dev.paedar.aoc.lvl09;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.OptionalInt;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class FileSystem {

    public static final int CHARACTER_CODE_0 = 48;

    private final List<Block> blocks;

    public static FileSystem ofDiskmap(String diskMap) {
        var isFileDescriptor = true;
        var fileId = 0;
        var blocks = new ArrayList<Block>();
        for (int i = 0; i < diskMap.length(); i++) {
            var charCode = diskMap.charAt(i);
            var blockLength = charCode - CHARACTER_CODE_0;
            Block block;
            if (isFileDescriptor) {
                block = new FileBlock(fileId);
                fileId++;
            } else {
                block = new EmptyBlock();
            }
            IntStream.range(0, blockLength)
                     .forEach(_ -> blocks.add(block));

            isFileDescriptor = !isFileDescriptor;
        }
        return new FileSystem(blocks);
    }

    private FileSystem(List<Block> blocks) {
        this.blocks = new ArrayList<>(blocks);
    }

    public void compact() {
        var firstEmptyBlockIndex = firstEmptyBlockIndex();
        var lastFileBlockIndex = lastFileBlockIndex();
        while (firstEmptyBlockIndex < lastFileBlockIndex) {
            Collections.swap(blocks, firstEmptyBlockIndex(), lastFileBlockIndex);
            firstEmptyBlockIndex = firstEmptyBlockIndex();
            lastFileBlockIndex = lastFileBlockIndex();
        }
    }

    public long checksum() {
        return IntStream.range(0, blocks.size())
                        .mapToLong(i -> switch (blocks.get(i)) {
                            case FileBlock(int fileIndex) -> (long) fileIndex * i;
                            case EmptyBlock _ -> 0;
                        })
                        .sum();
    }

    private int firstEmptyBlockIndex() {
        var emptyBlockIndex = blocks.indexOf(new EmptyBlock());
        if (emptyBlockIndex == -1) {
            throw new IllegalStateException("Empty block not found");
        }
        return emptyBlockIndex;
    }

    private int lastFileBlockIndex() {
        var reversedBlocks = blocks.reversed();
        return blocks.size() - 1 - IntStream.range(0, reversedBlocks.size())
                                            .filter(i -> reversedBlocks.get(i) instanceof FileBlock)
                                            .findFirst()
                                            .orElseThrow(() -> new IllegalStateException("No fileblock found"));
    }

    public void compactNoDefrag() {
        var fileIdsDecreasing = blocks.stream()
                                      .filter(FileBlock.class::isInstance)
                                      .map(FileBlock.class::cast)
                                      .map(FileBlock::id)
                                      .distinct()
                                      .sorted()
                                      .toList()
                                      .reversed();

        fileIdsDecreasing.forEach(this::moveAsFarLeftAsPossible);
    }

    private void moveAsFarLeftAsPossible(int fileId) {
        var fileSize = (int) blocks.stream()
                                   .filter(i -> i instanceof FileBlock(int id) && id == fileId)
                                   .count();
        var fileStart = blocks.indexOf(new FileBlock(fileId));

        var leftMostEmptySpanOfSizeIndex = findFirstEmptySpan(fileSize);
        leftMostEmptySpanOfSizeIndex.ifPresent(start -> {
            if(start < fileStart)
                swapEmptySpanAndFileWithId(start, fileStart, fileSize);
        });
    }

    private void swapEmptySpanAndFileWithId(int start, int fileStart, int fileSize) {
        for (int i = 0; i < fileSize; i++) {
            Collections.swap(blocks, start + i, fileStart + i);
        }
    }

    private OptionalInt findFirstEmptySpan(int fileSize) {
        return IntStream.range(0, blocks.size())
                        .filter(index -> isStartOfEmptySpanWithMinimumSize(index, fileSize))
                        .findFirst();
    }

    private boolean isStartOfEmptySpanWithMinimumSize(int index, int fileSize) {
        return fileSize <= blocks.stream()
                                 .skip(index)
                                 .takeWhile(EmptyBlock.class::isInstance)
                                 .count();
    }

    public String blockRepresentation() {
        return blocks.stream()
                       .map(b -> switch(b) {
                           case EmptyBlock _ -> ".";
                           case FileBlock(int id) -> Integer.toString(id);
                       })
                       .collect(Collectors.joining());
    }

}
