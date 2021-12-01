package org.example.completablefuture;

import lombok.RequiredArgsConstructor;
import org.example.service.HelloWorldService;

import java.util.concurrent.CompletableFuture;

import static org.example.util.CommonUtil.*;
import static org.example.util.LoggerUtil.log;

@RequiredArgsConstructor
public class CompletableFutureHelloWorld {

    private final HelloWorldService helloWorldService;

    public static void main(String[] args) {
        CompletableFutureHelloWorld completableFutureHelloWorld =
                new CompletableFutureHelloWorld(new HelloWorldService());
        String result = completableFutureHelloWorld.helloWorld().join();
        System.out.println("result = " + result);

        log("Done");
    }

    public CompletableFuture<String> helloWorld() {
        return CompletableFuture.supplyAsync(helloWorldService::helloWorld)
                .thenApply(String::toUpperCase);

    }

    public CompletableFuture<String> helloWorldWithSize() {
        return CompletableFuture.supplyAsync(helloWorldService::helloWorld)
                .thenApply(String::toUpperCase)
                .thenApply(s -> s.length() + " - " + s);
    }

    public String helloWorldMultipleAsyncCalls() {
        startTimer();
        CompletableFuture<String> hello =
                CompletableFuture.supplyAsync(helloWorldService::hello);
        CompletableFuture<String> world =
                CompletableFuture.supplyAsync(helloWorldService::world);

        String result = hello.thenCombine(world, (s, s2) -> s + s2)
                .thenApply(String::toUpperCase)
                .join();
        timeTaken();
        return result;
    }

    public String helloWorld3AsyncCalls() {

        startTimer();
        CompletableFuture<String> hello =
                CompletableFuture.supplyAsync(helloWorldService::hello);
        CompletableFuture<String> world =
                CompletableFuture.supplyAsync(helloWorldService::world);
        CompletableFuture<String> hi = CompletableFuture.supplyAsync(() -> {
            delay(1000);
            return " Hi CompletableFuture!";
        });

        String result = hello.thenCombine(world, (s, s2) -> s + s2)
                .thenCombine(hi, (s, s2) -> s + s2)
                .thenApply(String::toUpperCase)
                .join();
        timeTaken();
        return result;
    }


}
