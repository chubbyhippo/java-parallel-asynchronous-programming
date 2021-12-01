package org.example.completablefuture;

import org.example.service.HelloWorldService;

import java.util.concurrent.CompletableFuture;

import static org.example.util.LoggerUtil.log;

public class CompletableFutureHelloWorld {
    public static void main(String[] args) {
        HelloWorldService helloWorldService = new HelloWorldService();
        CompletableFuture.supplyAsync(helloWorldService::helloWorld)
                .thenAccept(s -> log("result is " + s))
                .join();
        log("Done");
    }
}
