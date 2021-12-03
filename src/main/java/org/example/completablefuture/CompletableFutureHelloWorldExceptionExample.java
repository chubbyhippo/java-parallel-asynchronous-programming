package org.example.completablefuture;

import lombok.RequiredArgsConstructor;
import org.example.service.HelloWorldService;

import java.util.concurrent.CompletableFuture;

import static org.example.util.CommonUtil.*;
import static org.example.util.LoggerUtil.log;

@RequiredArgsConstructor
public class CompletableFutureHelloWorldExceptionExample {

    private final HelloWorldService helloWorldService;

    public String helloWorld3AsyncCallsHandle() {

        startTimer();
        CompletableFuture<String> hello =
                CompletableFuture.supplyAsync(helloWorldService::hello);
        CompletableFuture<String> world =
                CompletableFuture.supplyAsync(helloWorldService::world);
        CompletableFuture<String> hi = CompletableFuture.supplyAsync(() -> {
            delay(1000);
            return " Hi CompletableFuture!";
        });

        String result = hello
                .handle((s, throwable) -> {
                    log("Exception is :" + throwable.getMessage());
                    return "";
                })
                .thenCombine(world, (s, s2) -> s + s2)
                .thenCombine(hi, (s, s2) -> s + s2)
                .thenApply(String::toUpperCase)
                .join();
        timeTaken();
        return result;
    }
}
