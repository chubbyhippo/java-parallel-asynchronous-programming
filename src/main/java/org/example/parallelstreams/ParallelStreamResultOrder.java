package org.example.parallelstreams;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class ParallelStreamResultOrder {
    public static List<Integer> listOrder(List<Integer> inputList) {
        return inputList.parallelStream()
                .map(integer -> integer * 2)
                .toList();
    }

    public static Set<Integer> setOrder(Set<Integer> inputSet) {
        return inputSet.parallelStream()
                .map(integer -> integer * 2)
                .collect(Collectors.toSet());
    }

    public static void main(String[] args) {
        List<Integer> integerList = List.of(1, 2, 3, 4, 5, 6, 7, 8);
        System.out.println("integerList = " + integerList);
        List<Integer> result = listOrder(integerList);
        System.out.println("result = " + result);

        Set<Integer> integerSet = Set.of(1, 2, 3, 4, 5, 6, 7, 8);
        System.out.println("integerSet = " + integerSet);
        Set<Integer> resultSet = setOrder(integerSet);
        System.out.println("resultSet = " + resultSet);

    }
}
