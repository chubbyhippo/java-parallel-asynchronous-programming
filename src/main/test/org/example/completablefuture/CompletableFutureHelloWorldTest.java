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

    @Test
    void helloWorldMultipleAsyncCalls() {
        String helloWorld = completableFutureHelloWorld.helloWorldMultipleAsyncCalls();

        assertEquals("HELLO WORLD!", helloWorld);
    }

    @Test
    void helloWorld3AsyncCalls() {
        String helloWorld = completableFutureHelloWorld.helloWorld3AsyncCalls();

        assertEquals("HELLO WORLD! HI COMPLETABLEFUTURE!", helloWorld);
    }

    @Test
    void helloWorld4AsyncCalls() {
        String helloWorld = completableFutureHelloWorld.helloWorld4AsyncCalls();

        assertEquals("HELLO WORLD! HI COMPLETABLEFUTURE! BYE!", helloWorld);
    }

    @Test
    void helloWorldThenCompose() {
        CompletableFuture<String> stringCompletableFuture = completableFutureHelloWorld.helloWorldThenCompose();
        stringCompletableFuture
                .thenAccept(s -> assertEquals("hello world!", s))
                .join();
    }

    @Test
    void helloWorld4AsyncCallsCustomThreadPool() {
        String helloWorld = completableFutureHelloWorld.helloWorld4AsyncCallsCustomThreadPool();

        assertEquals("HELLO WORLD! HI COMPLETABLEFUTURE! BYE!", helloWorld);
    }

    @Test
    void helloWorld4AsyncCallsCustomThreadPoolAsync() {
        String helloWorld = completableFutureHelloWorld.helloWorld4AsyncCallsCustomThreadPoolAsync();

        assertEquals("HELLO WORLD! HI COMPLETABLEFUTURE! BYE!", helloWorld);
    }
}
