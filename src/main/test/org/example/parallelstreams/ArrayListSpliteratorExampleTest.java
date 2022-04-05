package org.example.parallelstreams;

import org.example.util.DataSet;
import org.junit.jupiter.api.RepeatedTest;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ArrayListSpliteratorExampleTest {

    ArrayListSpliteratorExample arrayListSpliteratorExample = new ArrayListSpliteratorExample();

    @RepeatedTest(5)
    void multiplyEachValue() {
        int size = 10000000;
        ArrayList<Integer> integers = DataSet.generateArrayList(size);

        List<Integer> results = arrayListSpliteratorExample.multiplyEachValue(integers, 2, false);

        assertEquals(size, results.size());

    }

    @RepeatedTest(5)
    void multiplyEachValueParallel() {
        int size = 10000000;
        ArrayList<Integer> integers = DataSet.generateArrayList(size);

        List<Integer> results = arrayListSpliteratorExample.multiplyEachValue(integers, 2, true);

        assertEquals(size, results.size());

    }
}
