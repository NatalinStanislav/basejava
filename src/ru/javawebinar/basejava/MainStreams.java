package ru.javawebinar.basejava;

import java.util.*;
import java.util.stream.Collectors;

public class MainStreams {

    public static void main(String[] args) {
        int[] spam = new int[]{0, 1, 1, 1, 3, 9, 9, 9, 5, 5};
        System.out.println(minValue(spam));

        List<Integer> integers = new ArrayList<>();
        integers.add(1);
        integers.add(2);
        integers.add(3);
        integers.add(4);
        integers.add(5);
        integers.add(6);
        integers.add(7);
        integers.add(8);
        integers.add(9);
        oddOrEven(integers).forEach(System.out::println);
    }

    public static int minValue(int[] values) {
        return Arrays.stream(values)
                .distinct()
                .sorted()
                .reduce((acc, x) -> acc * 10 + x)
                .getAsInt();
    }

    public static List<Integer> oddOrEven(List<Integer> integers) {
        Map<Boolean, List<Integer>> map = integers.stream()
                .collect(Collectors.partitioningBy(p -> p % 2 == 0));
        int sum = integers.stream()
                .mapToInt(t -> t)
                .sum();
        System.out.println(sum);
        if (sum % 2 == 0) {
            return map.get(false);
        } else {
            return map.get(true);
        }
    }
}
