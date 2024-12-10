package dev.paedar.aoc.lvl09;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.IntStream;
import java.util.stream.LongStream;

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

}
