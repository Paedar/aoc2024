package dev.paedar.aoc.lvl11;

public class StoneList {
    private StoneNode first;
    private StoneNode last;

    private static int countAfter(StoneNode next) {
        var count = 0;
        while(next != null) {
            next = next.getRight();
            count++;
        }
        return count;
    }

    public int getSize() {
        return countAfter(first);
    }

    public void add(StoneNode node) {
        if (first == null) {
            first = node;
            last = node;
        } else {
            last.setRight(node);
            node.setLeft(last);
            last = node;
        }
        node.setParent(this);
    }

    public void update() {
        var next = first;
        while (next != null) {
            next = next.update();
        }
        // Find new first by going left
        if(first != null) {
            while(first.getLeft() != null) {
                first = first.getLeft();
            }
        }
    }

    public void display() {
        var next = first;
        var sb = new StringBuilder();
        sb.append("[size=");
        sb.append(countAfter(first));
        sb.append("]");
        while (next != null) {
            sb.append(" ");
            sb.append(next.getValue());
            next = next.getRight();
        }
        System.out.println(sb);
    }

}
