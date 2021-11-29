package org.example.parallelstreams;

import org.example.util.DataSet;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.example.util.CommonUtil.startTimer;
import static org.example.util.CommonUtil.timeTaken;
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
        resultList.forEach(name -> {
            assertTrue(name.contains("-"));
        } );
    }
}
