package org.example.completablefuture;

import org.example.service.HelloWorldService;
import org.junit.jupiter.api.Test;

import java.util.concurrent.CompletableFuture;

import static org.junit.jupiter.api.Assertions.*;

class CompletableFutureHelloWorldTest {

    HelloWorldService helloWorldService = new HelloWorldService();
    CompletableFutureHelloWorld completableFutureHelloWorld = new CompletableFutureHelloWorld(helloWorldService);

    @Test
    void helloWorld() {

        CompletableFuture<String> stringCompletableFuture = completableFutureHelloWorld.helloWorld();
        stringCompletableFuture
                .thenAccept(s -> assertEquals("HELLO WORLD", s))
                .join();


    }

    @Test
    void helloWorldWithSize() {
        CompletableFuture<String> stringCompletableFuture = completableFutureHelloWorld.helloWorldWithSize();
        stringCompletableFuture
                .thenAccept(s -> assertEquals("11 - HELLO WORLD", s))
                .join();
    }
}
