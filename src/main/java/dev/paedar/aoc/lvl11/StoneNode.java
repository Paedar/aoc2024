package dev.paedar.aoc.lvl11;

public class StoneNode {

    public static final String ZERO = "0";

    private StoneNode left;

    private StoneNode right;

    private long value;

    private String valueAsString;

    private StoneList parent;

    private StoneNode(String value) {
        updateValue(value);
    }

    public static StoneNode of(String value) {
        return new StoneNode(value);
    }

    public StoneNode getLeft() {
        return left;
    }

    public void setLeft(StoneNode left) {
        this.left = left;
    }

    public StoneNode getRight() {
        return right;
    }

    public void setRight(StoneNode right) {
        this.right = right;
    }

    public long getValue() {
        return value;
    }

    public void setParent(StoneList parent) {
        this.parent = parent;
    }

    /**
     * update according to the rules, then return the next node
     *
     * @return
     */
    public StoneNode update() {
        var next = this.right;
        switch (valueAsString) {
            case ZERO -> setThisTo1();
            case String s when s.length() % 2 == 0 -> split();
            default -> multiplyThisBy2024();
        }
        return next;
    }

    private void multiplyThisBy2024() {
        updateValue(value * 2024);
    }

    private void setThisTo1() {
        updateValue(1L);
    }

    private void updateValue(long newValue) {
        value = newValue;
        valueAsString = Long.toString(value);
    }

    private void updateValue(String newValue) {
        value = Long.parseLong(newValue);
        if(value == 0L) {
            valueAsString = ZERO;
        } else {
            valueAsString = newValue;
        }
    }

    private void split() {
        var splitAt = valueAsString.length() / 2;
        var leftValue = valueAsString.substring(0, splitAt);
        var rightValue = valueAsString.substring(splitAt);
        var newNode = new StoneNode(leftValue);
        newNode.setLeft(this.left);
        newNode.setRight(this);
        newNode.setParent(this.parent);
        if (this.left != null) {
            this.left.setRight(newNode);
        }
        this.setLeft(newNode);
        this.updateValue(rightValue);
    }

}
