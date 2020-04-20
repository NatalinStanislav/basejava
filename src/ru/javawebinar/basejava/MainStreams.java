package ru.javawebinar.basejava;

import java.util.*;

public class MainStreams {

    public static void main(String[] args) {
        int[] spam = new int[]{0, 1, 1, 1, 9, 9, 9, 5, 5};
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
                .boxed()
                .distinct()
                .sorted(Comparator.reverseOrder())
                .reduce((acc, x) -> {
                    int k = 10;
                    for (int i = acc; i / 10 > 0; i = i / 10) {
                        k *= 10;
                    }
                    return acc + k * x;
                })
                .get();
    }

    public static List<Integer> oddOrEven(List<Integer> integers) {
        List<Integer> oddList = new ArrayList<>();
        List<Integer> evenList = new ArrayList<>();
        int sum = integers.stream()
                .peek(e -> {
                    if (e % 2 == 0) {
                        evenList.add(e);
                    } else {
                        oddList.add(e);
                    }
                })
                .reduce(Integer::sum)
                .get();
        if (sum % 2 == 0) {
            integers.removeAll(evenList);
        } else {
            integers.removeAll(oddList);
        }
        return integers;
    }
}
