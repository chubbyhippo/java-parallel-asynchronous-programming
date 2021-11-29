package org.example.forkjoin;

import org.example.util.DataSet;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.ForkJoinTask;
import java.util.concurrent.RecursiveTask;

import static org.example.util.CommonUtil.delay;
import static org.example.util.CommonUtil.stopWatch;
import static org.example.util.LoggerUtil.log;

public class ForkJoinUsingRecursion extends RecursiveTask<List<String>> {
    private List<String> inputList;

    public ForkJoinUsingRecursion(List<String> inputList) {
        this.inputList = inputList;
    }

    public static void main(String[] args) {


        stopWatch.start();
        List<String> resultList;
        List<String> names = DataSet.namesList();
        ForkJoinPool forkJoinPool = new ForkJoinPool();
        ForkJoinUsingRecursion forkJoinUsingRecursion = new ForkJoinUsingRecursion(names);
        resultList = forkJoinPool.invoke(forkJoinUsingRecursion);

        stopWatch.stop();
        log("Final Result : " + resultList);
        log("Total Time Taken : " + stopWatch.getTime());
    }


    private static String addNameLengthTransform(String name) {
        delay(500);
        return name.length() + " - " + name;
    }

    @Override
    protected List<String> compute() {
        if (inputList.size() <= 1) {
            List<String> results = new ArrayList<>();
            inputList.forEach(s -> results.add(addNameLengthTransform(s)));
            return results;
        }
        int midpoint = inputList.size() / 2;
        ForkJoinTask<List<String>> leftInputList = new ForkJoinUsingRecursion(inputList.subList(0, midpoint)).fork();
        inputList = inputList.subList(midpoint, inputList.size());
        List<String> rightResults = compute();
        List<String> leftResult = leftInputList.join();
        leftResult.addAll(rightResults);
        return leftResult;
    }
}
