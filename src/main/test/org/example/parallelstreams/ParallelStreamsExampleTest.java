package org.example.parallelstreams;

import org.example.util.DataSet;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.List;

import static org.example.util.CommonUtil.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class ParallelStreamsExampleTest {

    ParallelStreamsExample parallelStreamsExample = new ParallelStreamsExample();

    @Test
    void stringTransform() {
        List<String> inputList = DataSet.namesList();

        startTimer();
        List<String> resultList = parallelStreamsExample.stringTransform(inputList);
        timeTaken();

        assertEquals(4, resultList.size());
        resultList.forEach(name -> assertTrue(name.contains("-")));
    }

    @ParameterizedTest
    @ValueSource(booleans = {false, true})
    void stringTransformIsParallel(boolean isParallel) {

        List<String> inputList = DataSet.namesList();

        startTimer();
        List<String> resultList = parallelStreamsExample.stringTransform(inputList, isParallel);
        timeTaken();
        stopWatchReset();

        assertEquals(4, resultList.size());
        resultList.forEach(name -> assertTrue(name.contains("-")));
    }
}
