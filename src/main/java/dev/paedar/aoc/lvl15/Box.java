package dev.paedar.aoc.lvl15;

public sealed interface Box extends Movable permits LeftPartOfBox, RightPartOfBox, SingleSizeBox {

}
