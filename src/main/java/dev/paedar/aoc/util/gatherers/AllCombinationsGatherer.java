package dev.paedar.aoc.util.gatherers;

import java.util.ArrayList;
import java.util.List;
import java.util.function.BiFunction;
import java.util.function.Supplier;
import java.util.stream.Gatherer;
import java.util.stream.Stream;

public class AllCombinationsGatherer<ElementType, ReturnType> implements Gatherer<ElementType, List<ElementType>, ReturnType> {

    private final boolean bothDirections;
    private final boolean combineSelf;
    private final BiFunction<ElementType, ElementType, ReturnType> combiner;

    private AllCombinationsGatherer(boolean bothDirections, boolean combineSelf, BiFunction<ElementType, ElementType, ReturnType> combiner) {
        this.bothDirections = bothDirections;
        this.combineSelf = combineSelf;
        this.combiner = combiner;
    }

    public static <E, R> AllCombinationsGatherer<E, R> combining(BiFunction<E, E, R> combiner, boolean twoWay, boolean combineSelf) {
        return new AllCombinationsGatherer<>(twoWay, combineSelf, combiner);
    }

    @Override
    public Integrator<List<ElementType>, ElementType, ReturnType> integrator() {
        return (state, element, downstream) -> {
            if (bothDirections) {
                state.stream()
                     .flatMap(existingElement -> Stream.of(combiner.apply(existingElement, element), combiner.apply(element, existingElement)))
                     .forEach(downstream::push);
            } else {
                state.stream()
                     .map(existingElement -> combiner.apply(existingElement, element))
                     .forEach(downstream::push);
            }

            if(combineSelf) {
                downstream.push(combiner.apply(element, element));
            }

            state.add(element);

            return true;
        };
    }

    @Override
    public Supplier<List<ElementType>> initializer() {
        return ArrayList::new;
    }

}
