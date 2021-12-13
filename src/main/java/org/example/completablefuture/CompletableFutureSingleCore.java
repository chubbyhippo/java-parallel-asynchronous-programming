package org.example.completablefuture;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ForkJoinPool;

import static org.example.util.CommonUtil.delay;
import static org.example.util.LoggerUtil.log;

public class CompletableFutureSingleCore {
    public static void main(String[] args) {
        ForkJoinPool forkJoinPool = new ForkJoinPool(1);
        CompletableFuture<String> first = CompletableFuture.supplyAsync(() -> {
            delay(2000);
            log("first");
            return "first";
        }, forkJoinPool);
        CompletableFuture<String> second = CompletableFuture.supplyAsync(() -> {
            log("second");
            return "second";
        }, forkJoinPool);

        CompletableFuture<String> stringCompletableFuture = first.thenCombine(second, String::concat);
        System.out.println("stringCompletableFuture = " + stringCompletableFuture.join());

        forkJoinPool.shutdown();
    }
}
