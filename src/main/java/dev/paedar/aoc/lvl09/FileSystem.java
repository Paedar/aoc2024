package dev.paedar.aoc.lvl09;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.OptionalInt;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class FileSystem {

    public static final int CHARACTER_CODE_0 = 48;

    private final List<Block> blocks;

    private final Map<Integer, Integer> originalFileStarts;

    private final Map<Integer, Integer> fileLengths;

    private final Map<Integer, Integer> emptyBlockLengths;

    public static FileSystem ofDiskmap(String diskMap) {
        var isFileDescriptor = true;
        var fileId = 0;
        var blocks = new ArrayList<Block>();
        var startIndex = 0;
        var fileLengths = new HashMap<Integer, Integer>();
        var fileStarts = new HashMap<Integer, Integer>();
        var emptyBlocks = new HashMap<Integer, Integer>();
        for (int i = 0; i < diskMap.length(); i++) {
            var charCode = diskMap.charAt(i);
            var blockLength = charCode - CHARACTER_CODE_0;
            Block block;
            if (isFileDescriptor) {
                block = new FileBlock(fileId);
                fileLengths.put(fileId, blockLength);
                fileStarts.put(fileId, startIndex);
                fileId++;
            } else {
                block = new EmptyBlock();
                if(blockLength != 0) {
                    emptyBlocks.put(startIndex, blockLength);
                }
            }
            IntStream.range(0, blockLength)
                     .forEach(_ -> blocks.add(block));
            startIndex += blockLength;

            isFileDescriptor = !isFileDescriptor;
        }
        return new FileSystem(blocks, fileStarts, fileLengths, emptyBlocks);
    }

    private FileSystem(List<Block> blocks, Map<Integer, Integer> fileStarts, Map<Integer, Integer> fileLengths, Map<Integer, Integer> emptyBlockLengths) {
        this.blocks = new ArrayList<>(blocks);
        this.originalFileStarts = new HashMap<>(fileStarts);
        this.fileLengths = new HashMap<>(fileLengths);
        this.emptyBlockLengths = emptyBlockLengths;
    }

    public void compact() {
        var emptyBlockIndex = firstEmptyBlockIndex();
        var fileBlockIndex = lastFileBlockIndex();
        while (emptyBlockIndex < fileBlockIndex) {
            swapAtIndex(emptyBlockIndex, fileBlockIndex);
            while (blocks.get(emptyBlockIndex) instanceof FileBlock) {
                emptyBlockIndex++;
            }
            while (blocks.get(fileBlockIndex) instanceof EmptyBlock) {
                fileBlockIndex--;
            }
        }
    }

    private void swapAtIndex(int emptyBlockIndex, int fileBlockIndex) {
        Collections.swap(blocks, emptyBlockIndex, fileBlockIndex);
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
        var fileSize = fileLengths.get(fileId);
        var fileStart = originalFileStarts.get(fileId);

        var leftMostEmptySpanOfSizeIndex = findFirstEmptySpanThatFits(fileSize);
        leftMostEmptySpanOfSizeIndex.ifPresent(start -> {
            if (start < fileStart) {
                swapEmptyBlockWithDataBlock(start, fileStart, fileSize);
            }
        });
    }

    private void swapEmptyBlockWithDataBlock(int firstSpanStart, int secondSpanStart, int length) {
        for (int i = 0; i < length; i++) {
            swapAtIndex(firstSpanStart + i, secondSpanStart + i);
        }
        // Update empty block map
        var emptyBlockLength = emptyBlockLengths.get(firstSpanStart);
        emptyBlockLengths.remove(firstSpanStart);
        if(emptyBlockLength > length) {
            emptyBlockLengths.put(firstSpanStart + length, emptyBlockLength - length);
        }
        emptyBlockLengths.put(secondSpanStart, length);
        /*
         Note that we should technically also consolidate this new empty block with the block after it if that's empty,
         but since we're checking files from right to left that doesn't affect the result and today I'm lazy as sin.
         */
    }

    private OptionalInt findFirstEmptySpanThatFits(int fileSize) {
        return emptyBlockLengths.entrySet().stream()
                                .filter(entry -> entry.getValue() >= fileSize)
                                .mapToInt(Map.Entry::getKey)
                                .min();
    }

    public String blockRepresentation() {
        return blocks.stream()
                     .map(b -> switch (b) {
                         case EmptyBlock _ -> ".";
                         case FileBlock(int id) -> Integer.toString(id);
                     })
                     .collect(Collectors.joining());
    }

}
