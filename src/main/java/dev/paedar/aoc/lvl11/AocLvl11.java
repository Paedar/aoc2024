package dev.paedar.aoc.lvl11;

import dev.paedar.aoc.util.InputReader;

import java.util.List;

public class AocLvl11 {

    public static void main(String[] args) {
        var tokens = InputReader.readTokens("input_11.txt");

        var numberOfStonesAfter25Blinks = numStonesAfterNUpdates(tokens, 50);
        System.out.println("I blinked 25 times and now there's this many stones: " + numberOfStonesAfter25Blinks);
    }

    public static int numStonesAfterNUpdates(List<String> tokens, int n) {
        var stones = new StoneList();
        tokens.stream()
                .map(StoneNode::of)
                .forEach(stones::add);

//        System.out.println("Before:");
//        stones.display();
        for(int i = 0; i < n; ++i) {
            stones.update();
//            System.out.println("After iteration " + (i+1));
//            stones.display();
        }

        return stones.getSize();
    }

}
