package dev.paedar.aoc.util.gatherers;

import java.util.HashSet;
import java.util.Set;
import java.util.function.BiConsumer;
import java.util.function.BiPredicate;
import java.util.function.BinaryOperator;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.Gatherer;

public class MergingGatherer<ElementType> implements Gatherer<ElementType, Set<ElementType>, ElementType> {

    private final BiPredicate<ElementType, ElementType> canMerge;

    private final BinaryOperator<ElementType> mergeFunction;

    private MergingGatherer(BiPredicate<ElementType, ElementType> canMerge, BinaryOperator<ElementType> mergeFunction) {
        this.canMerge = canMerge;
        this.mergeFunction = mergeFunction;
    }

    public static <E> MergingGatherer<E> of(BiPredicate<E, E> canMerge, BinaryOperator<E> mergeFunction) {
        return new MergingGatherer<>(canMerge, mergeFunction);
    }

    @Override
    public Supplier<Set<ElementType>> initializer() {
        return HashSet::new;
    }

    @Override
    public Integrator<Set<ElementType>, ElementType, ElementType> integrator() {
        return (state, element, downstream) -> {
            var mergeable = state.stream()
                                 .filter(existing -> canMerge.test(element, existing))
                                 .collect(Collectors.toSet());
            state.removeAll(mergeable);
            ElementType newElement = element;
            for(var mergeableElement : mergeable) {
                newElement = mergeFunction.apply(newElement, mergeableElement);
            }
            state.add(newElement);

            return true;
        };
    }

    @Override
    public BiConsumer<Set<ElementType>, Downstream<? super ElementType>> finisher() {
        return (state, downstream) -> {
            state.forEach(downstream::push);
        };
    }

}
