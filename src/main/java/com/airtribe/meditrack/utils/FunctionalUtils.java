package com.airtribe.meditrack.utils;

import java.util.*;
import java.util.function.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Advanced Java 8 Functional Programming Utilities
 * Demonstrates lambda expressions, method references, and functional interfaces
 */
public class FunctionalUtils {

    // Advanced Java 8: Custom functional interface for triple parameter function
    @FunctionalInterface
    public interface TriFunction<T, U, V, R> {
        R apply(T t, U u, V v);
        
        default <W> TriFunction<T, U, V, W> andThen(Function<? super R, ? extends W> after) {
            Objects.requireNonNull(after);
            return (T t, U u, V v) -> after.apply(apply(t, u, v));
        }
    }

    // Advanced Java 8: Memoization for expensive function calls
    public static <T, R> Function<T, R> memoize(Function<T, R> function) {
        Map<T, R> cache = new HashMap<>();
        return input -> cache.computeIfAbsent(input, function);
    }

    // Advanced Java 8: Retry logic with functional approach
    public static <T> Optional<T> retry(Supplier<T> operation, int maxAttempts) {
        for (int i = 0; i < maxAttempts; i++) {
            try {
                return Optional.of(operation.get());
            } catch (Exception e) {
                if (i == maxAttempts - 1) {
                    return Optional.empty();
                }
            }
        }
        return Optional.empty();
    }

    // Advanced Java 8: Compose predicates with AND logic
    @SafeVarargs
    public static <T> Predicate<T> allOf(Predicate<T>... predicates) {
        return Arrays.stream(predicates)
                .reduce(Predicate::and)
                .orElse(x -> true);
    }

    // Advanced Java 8: Compose predicates with OR logic
    @SafeVarargs
    public static <T> Predicate<T> anyOf(Predicate<T>... predicates) {
        return Arrays.stream(predicates)
                .reduce(Predicate::or)
                .orElse(x -> false);
    }

    // Advanced Java 8: Partition list based on predicate
    public static <T> Map<Boolean, List<T>> partition(List<T> list, Predicate<T> predicate) {
        return list.stream()
                .collect(Collectors.partitioningBy(predicate));
    }

    // Advanced Java 8: Batch processing with Stream API
    public static <T> List<List<T>> batch(List<T> list, int batchSize) {
        return Stream.iterate(0, i -> i + batchSize)
                .limit((list.size() + batchSize - 1) / batchSize)
                .map(i -> list.subList(i, Math.min(i + batchSize, list.size())))
                .collect(Collectors.toList());
    }

    // Advanced Java 8: Safe division with Optional
    public static Optional<Double> safeDivide(double numerator, double denominator) {
        return denominator == 0 ? Optional.empty() : Optional.of(numerator / denominator);
    }

    // Advanced Java 8: Flatten nested lists using flatMap
    public static <T> List<T> flatten(List<List<T>> nestedList) {
        return nestedList.stream()
                .flatMap(List::stream)
                .collect(Collectors.toList());
    }

    // Advanced Java 8: Find duplicates in list
    public static <T> Set<T> findDuplicates(List<T> list) {
        Set<T> seen = new HashSet<>();
        return list.stream()
                .filter(item -> !seen.add(item))
                .collect(Collectors.toSet());
    }

    // Advanced Java 8: Remove duplicates while preserving order
    public static <T> List<T> removeDuplicates(List<T> list) {
        return list.stream()
                .distinct()
                .collect(Collectors.toList());
    }

    // Advanced Java 8: Transform map values using lambda
    public static <K, V, R> Map<K, R> transformMapValues(
            Map<K, V> map, 
            Function<V, R> transformer) {
        return map.entrySet().stream()
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        entry -> transformer.apply(entry.getValue())
                ));
    }

    // Advanced Java 8: Filter and transform in one operation
    public static <T, R> List<R> filterAndMap(
            List<T> list,
            Predicate<T> filter,
            Function<T, R> mapper) {
        return list.stream()
                .filter(filter)
                .map(mapper)
                .collect(Collectors.toList());
    }

    // Advanced Java 8: Reduce with custom accumulator
    public static <T> Optional<T> reduce(
            List<T> list,
            BinaryOperator<T> accumulator) {
        return list.stream()
                .reduce(accumulator);
    }

    // Advanced Java 8: Peek for debugging streams
    public static <T> List<T> debugStream(
            List<T> list,
            Consumer<T> debugAction) {
        return list.stream()
                .peek(debugAction)
                .collect(Collectors.toList());
    }

    // Advanced Java 8: Check if all elements match condition
    public static <T> boolean allMatch(List<T> list, Predicate<T> predicate) {
        return list.stream().allMatch(predicate);
    }

    // Advanced Java 8: Check if any element matches condition
    public static <T> boolean anyMatch(List<T> list, Predicate<T> predicate) {
        return list.stream().anyMatch(predicate);
    }

    // Advanced Java 8: Check if no elements match condition
    public static <T> boolean noneMatch(List<T> list, Predicate<T> predicate) {
        return list.stream().noneMatch(predicate);
    }

    // Advanced Java 8: Group by with custom collector
    public static <T, K> Map<K, Long> countByGrouping(
            List<T> list,
            Function<T, K> classifier) {
        return list.stream()
                .collect(Collectors.groupingBy(
                        classifier,
                        Collectors.counting()
                ));
    }

    // Advanced Java 8: Collect to custom collection with supplier
    public static <T, C extends Collection<T>> C collectToCustomCollection(
            List<T> list,
            Supplier<C> collectionFactory) {
        return list.stream()
                .collect(Collectors.toCollection(collectionFactory));
    }

    // Advanced Java 8: Parallel processing with custom thread pool
    public static <T, R> List<R> parallelMap(
            List<T> list,
            Function<T, R> mapper) {
        return list.parallelStream()
                .map(mapper)
                .collect(Collectors.toList());
    }

    // Advanced Java 8: Lazy evaluation with Supplier
    public static <T> T lazyEvaluate(Supplier<T> supplier) {
        return supplier.get();
    }

    // Advanced Java 8: Currying - convert multi-parameter function to chain of single-parameter functions
    public static <T, U, R> Function<T, Function<U, R>> curry(BiFunction<T, U, R> biFunction) {
        return t -> u -> biFunction.apply(t, u);
    }

    // Advanced Java 8: Compose multiple functions
    @SafeVarargs
    public static <T> Function<T, T> compose(Function<T, T>... functions) {
        return Arrays.stream(functions)
                .reduce(Function.identity(), Function::andThen);
    }

    // Advanced Java 8: Find first matching element or return default
    public static <T> T findFirstOrDefault(
            List<T> list,
            Predicate<T> predicate,
            T defaultValue) {
        return list.stream()
                .filter(predicate)
                .findFirst()
                .orElse(defaultValue);
    }

    // Advanced Java 8: Safe get from map with default value computation
    public static <K, V> V getOrCompute(
            Map<K, V> map,
            K key,
            Supplier<V> defaultSupplier) {
        return map.computeIfAbsent(key, k -> defaultSupplier.get());
    }
}
