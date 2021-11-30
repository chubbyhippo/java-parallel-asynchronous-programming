package org.example.parallelstreams;

import org.example.util.DataSet;

import java.util.List;

import static org.example.util.CommonUtil.*;

public class ParallelStreamsExample {
    private String addNameLengthTransform(String name) {
        delay(500);
        return name.length() + " - " + name;
    }

    public List<String> stringTransform(List<String> names) {
        return names
                .parallelStream()
                .map(this::addNameLengthTransform)
                .toList();
    }

    public List<String> stringTransform(List<String> names, boolean isParallel) {

        return isParallel ? names.parallelStream().map(this::addNameLengthTransform).toList() :
                names.stream().map(this::addNameLengthTransform).toList();
    }

    public List<String> stringsToLowercase(List<String> names) {
        return names
                .parallelStream()
                .map(String::toLowerCase)
                .toList();
    }

    public static void main(String[] args) {
        List<String> namesList = DataSet.namesList();
        ParallelStreamsExample parallelStreamsExample = new ParallelStreamsExample();
        startTimer();
        List<String> results = parallelStreamsExample.stringTransform(namesList);
        System.out.println("results = " + results);
        timeTaken();
    }
}
