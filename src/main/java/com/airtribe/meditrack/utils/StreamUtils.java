package com.airtribe.meditrack.utils;

import java.util.*;
import java.util.function.Function;
import java.util.function.UnaryOperator;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

/**
 * Advanced Stream API utilities demonstrating Java 8 lambda expressions
 */
public class StreamUtils {

    // Advanced Java 8: Create stream from range
    public static IntStream range(int start, int end) {
        return IntStream.range(start, end);
    }

    // Advanced Java 8: Create stream of repeated element
    public static <T> Stream<T> repeat(T element, int count) {
        return Stream.generate(() -> element).limit(count);
    }

    // Advanced Java 8: Zip two lists together
    public static <T, U, R> List<R> zip(
            List<T> list1,
            List<U> list2,
            java.util.function.BiFunction<T, U, R> zipper) {
        
        return IntStream.range(0, Math.min(list1.size(), list2.size()))
                .mapToObj(i -> zipper.apply(list1.get(i), list2.get(i)))
                .collect(Collectors.toList());
    }

    // Advanced Java 8: Take first N elements
    public static <T> List<T> take(List<T> list, int n) {
        return list.stream()
                .limit(n)
                .collect(Collectors.toList());
    }

    // Advanced Java 8: Skip first N elements
    public static <T> List<T> skip(List<T> list, int n) {
        return list.stream()
                .skip(n)
                .collect(Collectors.toList());
    }

    // Advanced Java 8: Take while predicate is true
    public static <T> List<T> takeWhile(List<T> list, java.util.function.Predicate<T> predicate) {
        return list.stream()
                .takeWhile(predicate)
                .collect(Collectors.toList());
    }

    // Advanced Java 8: Drop while predicate is true
    public static <T> List<T> dropWhile(List<T> list, java.util.function.Predicate<T> predicate) {
        return list.stream()
                .dropWhile(predicate)
                .collect(Collectors.toList());
    }

    // Advanced Java 8: Interleave two lists
    public static <T> List<T> interleave(List<T> list1, List<T> list2) {
        List<T> result = new ArrayList<>();
        Iterator<T> iter1 = list1.iterator();
        Iterator<T> iter2 = list2.iterator();
        
        while (iter1.hasNext() || iter2.hasNext()) {
            if (iter1.hasNext()) result.add(iter1.next());
            if (iter2.hasNext()) result.add(iter2.next());
        }
        return result;
    }

    // Advanced Java 8: Generate infinite stream with iteration
    public static <T> Stream<T> iterate(T seed, UnaryOperator<T> function) {
        return Stream.iterate(seed, function);
    }

    // Advanced Java 8: Sliding window over list
    public static <T> List<List<T>> slidingWindow(List<T> list, int windowSize) {
        return IntStream.rangeClosed(0, list.size() - windowSize)
                .mapToObj(i -> list.subList(i, i + windowSize))
                .collect(Collectors.toList());
    }

    // Advanced Java 8: Group consecutive elements
    public static <T> List<List<T>> groupConsecutive(
            List<T> list,
            java.util.function.BiPredicate<T, T> areConsecutive) {
        
        List<List<T>> result = new ArrayList<>();
        if (list.isEmpty()) return result;
        
        List<T> currentGroup = new ArrayList<>();
        currentGroup.add(list.get(0));
        
        for (int i = 1; i < list.size(); i++) {
            if (areConsecutive.test(list.get(i - 1), list.get(i))) {
                currentGroup.add(list.get(i));
            } else {
                result.add(new ArrayList<>(currentGroup));
                currentGroup.clear();
                currentGroup.add(list.get(i));
            }
        }
        result.add(currentGroup);
        return result;
    }

    // Advanced Java 8: Cartesian product of two lists
    public static <T, U> List<Map.Entry<T, U>> cartesianProduct(List<T> list1, List<U> list2) {
        return list1.stream()
                .flatMap(t -> list2.stream()
                        .map(u -> new AbstractMap.SimpleEntry<>(t, u)))
                .collect(Collectors.toList());
    }

    // Advanced Java 8: Find most frequent element
    public static <T> Optional<T> mostFrequent(List<T> list) {
        return list.stream()
                .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()))
                .entrySet().stream()
                .max(Map.Entry.comparingByValue())
                .map(Map.Entry::getKey);
    }

    // Advanced Java 8: Reverse list using stream
    public static <T> List<T> reverse(List<T> list) {
        return IntStream.rangeClosed(1, list.size())
                .mapToObj(i -> list.get(list.size() - i))
                .collect(Collectors.toList());
    }

    // Advanced Java 8: Rotate list by N positions
    public static <T> List<T> rotate(List<T> list, int positions) {
        if (list.isEmpty()) return list;
        int n = positions % list.size();
        if (n < 0) n += list.size();
        
        return Stream.concat(
                list.subList(n, list.size()).stream(),
                list.subList(0, n).stream()
        ).collect(Collectors.toList());
    }

    // Advanced Java 8: Transpose list of lists (matrix transpose)
    public static <T> List<List<T>> transpose(List<List<T>> matrix) {
        if (matrix.isEmpty() || matrix.get(0).isEmpty()) {
            return new ArrayList<>();
        }
        
        int rows = matrix.size();
        int cols = matrix.get(0).size();
        
        return IntStream.range(0, cols)
                .mapToObj(col -> IntStream.range(0, rows)
                        .mapToObj(row -> matrix.get(row).get(col))
                        .collect(Collectors.toList()))
                .collect(Collectors.toList());
    }

    // Advanced Java 8: Create frequency map
    public static <T> Map<T, Long> frequencyMap(List<T> list) {
        return list.stream()
                .collect(Collectors.groupingBy(
                        Function.identity(),
                        Collectors.counting()
                ));
    }

    // Advanced Java 8: Get top N elements by comparator
    public static <T> List<T> topN(List<T> list, int n, Comparator<T> comparator) {
        return list.stream()
                .sorted(comparator.reversed())
                .limit(n)
                .collect(Collectors.toList());
    }

    // Advanced Java 8: Merge sorted lists
    public static <T extends Comparable<T>> List<T> mergeSorted(List<T> list1, List<T> list2) {
        return Stream.concat(list1.stream(), list2.stream())
                .sorted()
                .collect(Collectors.toList());
    }

    // Advanced Java 8: Intersect two lists
    public static <T> List<T> intersect(List<T> list1, List<T> list2) {
        Set<T> set2 = new HashSet<>(list2);
        return list1.stream()
                .filter(set2::contains)
                .distinct()
                .collect(Collectors.toList());
    }

    // Advanced Java 8: Union of two lists
    public static <T> List<T> union(List<T> list1, List<T> list2) {
        return Stream.concat(list1.stream(), list2.stream())
                .distinct()
                .collect(Collectors.toList());
    }

    // Advanced Java 8: Difference of two lists (list1 - list2)
    public static <T> List<T> difference(List<T> list1, List<T> list2) {
        Set<T> set2 = new HashSet<>(list2);
        return list1.stream()
                .filter(item -> !set2.contains(item))
                .collect(Collectors.toList());
    }

    // Advanced Java 8: Symmetric difference
    public static <T> List<T> symmetricDifference(List<T> list1, List<T> list2) {
        Set<T> set1 = new HashSet<>(list1);
        Set<T> set2 = new HashSet<>(list2);
        
        return Stream.concat(
                list1.stream().filter(item -> !set2.contains(item)),
                list2.stream().filter(item -> !set1.contains(item))
        ).distinct().collect(Collectors.toList());
    }

    // Advanced Java 8: Check if list is sorted
    public static <T extends Comparable<T>> boolean isSorted(List<T> list) {
        return IntStream.range(0, list.size() - 1)
                .allMatch(i -> list.get(i).compareTo(list.get(i + 1)) <= 0);
    }

    // Advanced Java 8: Create index map (value -> index)
    public static <T> Map<T, Integer> indexMap(List<T> list) {
        return IntStream.range(0, list.size())
                .boxed()
                .collect(Collectors.toMap(list::get, Function.identity(), (a, b) -> a));
    }

    // Advanced Java 8: Paginate list
    public static <T> List<List<T>> paginate(List<T> list, int pageSize) {
        return IntStream.range(0, (list.size() + pageSize - 1) / pageSize)
                .mapToObj(page -> list.subList(
                        page * pageSize,
                        Math.min((page + 1) * pageSize, list.size())
                ))
                .collect(Collectors.toList());
    }

    // Advanced Java 8: Shuffle list using stream
    public static <T> List<T> shuffle(List<T> list) {
        List<T> result = new ArrayList<>(list);
        Collections.shuffle(result);
        return result;
    }

    // Advanced Java 8: Sample N random elements
    public static <T> List<T> sample(List<T> list, int n) {
        return shuffle(list).stream()
                .limit(n)
                .collect(Collectors.toList());
    }
}
