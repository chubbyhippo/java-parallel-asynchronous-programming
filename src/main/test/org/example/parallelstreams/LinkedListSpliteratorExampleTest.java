package org.example.parallelstreams;

import org.example.util.DataSet;
import org.junit.jupiter.api.RepeatedTest;

import java.util.LinkedList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class LinkedListSpliteratorExampleTest {

    LinkedListSpliteratorExample linkedListSpliteratorExample = new LinkedListSpliteratorExample();

    @RepeatedTest(5)
    void multiplyEachValue() {
        int size = 10000000;
        LinkedList<Integer> integers = DataSet.generateIntegerLinkedList(size);

        List<Integer> results = linkedListSpliteratorExample.multiplyEachValue(integers, 2, false);

        assertEquals(size, results.size());

    }

    @RepeatedTest(5)
    void multiplyEachValueParallel() {
        int size = 10000000;
        LinkedList<Integer> integers = DataSet.generateIntegerLinkedList(size);


        List<Integer> results = linkedListSpliteratorExample.multiplyEachValue(integers, 2, true);

        assertEquals(size, results.size());

    }
}
