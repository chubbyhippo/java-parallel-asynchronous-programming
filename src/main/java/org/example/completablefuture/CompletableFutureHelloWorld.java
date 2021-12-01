package org.example.completablefuture;

import lombok.RequiredArgsConstructor;
import org.example.service.HelloWorldService;

import java.util.concurrent.CompletableFuture;

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
}
