package br.com.luan.barcella.jesus.api.utils;

import static java.util.stream.Collectors.toList;
import static org.apache.commons.lang3.ArrayUtils.isEmpty;
import static org.apache.commons.lang3.RandomUtils.nextInt;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Supplier;
import java.util.stream.Stream;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class RandomCollectionUtils {

    public static <T> T pickRandom(final T[] array) {
        if (isEmpty(array)) {
            return null;
        }

        return array[nextInt(0, array.length)];
    }

    public static <T> List<T> generateList(final Supplier<T> supplier, final int startInclusive,
        final int endExclusive) {
        return Stream.generate(supplier).limit(nextInt(startInclusive, endExclusive)).collect(toList());
    }

    @SuppressWarnings("unchecked")
    public static <T> T pickRandomExcept(final T[] array, final T exception) {
        final List<T> list = new ArrayList<>();
        list.addAll(Arrays.asList(array));
        list.remove(exception);
        return pickRandom(list.toArray((T[]) new Object[list.size()]));
    }

    @SuppressWarnings("unchecked")
    public static <T> T pickRandomExcept(final T[] array, final T[] exceptions) {
        final List<T> list = new ArrayList<>();
        list.addAll(Arrays.asList(array));
        Arrays.asList(exceptions).forEach((e) -> list.remove(e));
        return pickRandom(list.toArray((T[]) new Object[list.size()]));
    }
}

