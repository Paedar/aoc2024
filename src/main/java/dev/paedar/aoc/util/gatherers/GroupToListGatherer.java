package dev.paedar.aoc.util.gatherers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.BiConsumer;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Gatherer;

public class GroupToListGatherer<ElementType, KeyType, ValueType> implements Gatherer<ElementType, Map<KeyType, List<ValueType>>, Map<KeyType, List<ValueType>>> {

    private final Function<ElementType, KeyType> keyMapper;
    private final Function<ElementType, ValueType> valueMapper;

    private GroupToListGatherer(Function<ElementType, KeyType> keyMapper, Function<ElementType, ValueType> valueMapper) {
        this.keyMapper = keyMapper;
        this.valueMapper = valueMapper;
    }

    public static <T, K, R> GroupToListGatherer<T, K, R> of(Function<T, K> keyMapper, Function<T, R> valueMapper) {
        return new GroupToListGatherer<>(keyMapper, valueMapper);
    }

    @Override
    public Integrator<Map<KeyType, List<ValueType>>, ElementType, Map<KeyType, List<ValueType>>> integrator() {
        return (state, element, downstream) -> {
            var key = keyMapper.apply(element);
            var value = valueMapper.apply(element);
            state.computeIfAbsent(key, _ -> new ArrayList<>()).add(value);
            return true;
        };
    }

    @Override
    public Supplier<Map<KeyType, List<ValueType>>> initializer() {
        return HashMap::new;
    }

    @Override
    public BinaryOperator<Map<KeyType, List<ValueType>>> combiner() {
        return (stateThis, stateOther) -> {
            var result = new HashMap<KeyType, List<ValueType>>(stateThis);
            stateOther.entrySet()
                    .forEach(entry -> result.computeIfAbsent(entry.getKey(), _ -> new ArrayList<>()).addAll(entry.getValue()));
            return result;
        };
    }

    @Override
    public BiConsumer<Map<KeyType, List<ValueType>>, Downstream<? super Map<KeyType, List<ValueType>>>> finisher() {
        return (state, downstream) -> {
            downstream.push(state);
        };
    }

}
