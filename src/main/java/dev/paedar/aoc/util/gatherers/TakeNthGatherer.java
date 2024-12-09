package dev.paedar.aoc.util.gatherers;

import java.util.function.BinaryOperator;
import java.util.function.Supplier;
import java.util.stream.Gatherer;

public record TakeNthGatherer(int numCols, int colNum) implements Gatherer<String, TakeNthGatherer.Counter, String> {

    @Override
    public Integrator<Counter, String, String> integrator() {
        return (counter, s, downstream) -> {
            if (counter.getCount() % numCols == colNum) {
                downstream.push(s);
            }
            counter.increment();
            return true;
        };
    }

    @Override
    public Supplier<Counter> initializer() {
        return Counter::new;
    }

    @Override
    public BinaryOperator<Counter> combiner() {
        return Counter::new;
    }

    public static class Counter {

        public Counter() {
            this(0);
        }

        public Counter(int initial) {
            count = initial;
        }

        public Counter(Counter a, Counter b) {
            this(a.count + b.count);
        }

        private int count;

        public int getCount() {
            return count;
        }

        public void increment() {
            count++;
        }

    }

}
