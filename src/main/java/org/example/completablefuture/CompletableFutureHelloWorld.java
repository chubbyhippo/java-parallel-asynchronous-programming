package org.example.completablefuture;

import lombok.RequiredArgsConstructor;
import org.example.service.HelloWorldService;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

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
        stopWatchReset();
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
        stopWatchReset();
        return result;
    }

    public String helloWorld4AsyncCalls() {
        startTimer();
        CompletableFuture<String> hello = CompletableFuture.supplyAsync(helloWorldService::hello);
        CompletableFuture<String> world = CompletableFuture.supplyAsync(helloWorldService::world);
        CompletableFuture<String> hi = CompletableFuture.supplyAsync(() -> {
            delay(1000);
            return " HI CompletableFuture!";
        });

        CompletableFuture<String> bye = CompletableFuture.supplyAsync(() -> {
            delay(1000);
            return " Bye!";
        });

        String hw = hello
                .thenCombine(world, (h, w) -> h + w) // (first,second)
                .thenCombine(hi, (previous, current) -> previous + current)
                .thenCombine(bye, (previous, current) -> previous + current)
                .thenApply(String::toUpperCase)
                .join();

        timeTaken();
        stopWatchReset();
        return hw;
    }

    public String helloWorld4AsyncCallsCustomThreadPool() {
        startTimer();

        ExecutorService executorService = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());
        CompletableFuture<String> hello = CompletableFuture.supplyAsync(helloWorldService::hello, executorService);
        CompletableFuture<String> world = CompletableFuture.supplyAsync(helloWorldService::world, executorService);
        CompletableFuture<String> hi = CompletableFuture.supplyAsync(() -> {
            delay(1000);
            return " HI CompletableFuture!";
        }, executorService);

        CompletableFuture<String> bye = CompletableFuture.supplyAsync(() -> {
            delay(1000);
            return " Bye!";
        });

        String hw = hello
                .thenCombine(world, (h, w) -> {
                    log("thenCombine h/w");
                    return h + w;
                }) // (first,second)
                .thenCombine(hi, (previous, current) -> {
                    log("thenCombine grevious/current");
                    return previous + current;
                })
                .thenCombine(bye, (previous, current) -> {
                    log("thenApply");
                    return previous + current;
                })
                .thenApply(String::toUpperCase)
                .join();

        timeTaken();
        stopWatchReset();
        return hw;
    }

    public String helloWorld4AsyncCallsCustomThreadPoolAsync() {
        startTimer();

        ExecutorService executorService = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());
        CompletableFuture<String> hello = CompletableFuture.supplyAsync(helloWorldService::hello, executorService);
        CompletableFuture<String> world = CompletableFuture.supplyAsync(helloWorldService::world, executorService);
        CompletableFuture<String> hi = CompletableFuture.supplyAsync(() -> {
            delay(1000);
            return " HI CompletableFuture!";
        }, executorService);

        CompletableFuture<String> bye = CompletableFuture.supplyAsync(() -> {
            delay(1000);
            return " Bye!";
        });

        String hw = hello
                .thenCombineAsync(world, (h, w) -> {
                    log("thenCombine h/w");
                    return h + w;
                }, executorService) // (first,second)
                .thenCombineAsync(hi, (previous, current) -> {
                    log("thenCombine grevious/current");
                    return previous + current;
                }, executorService)
                .thenCombineAsync(bye, (previous, current) -> {
                    log("thenApply");
                    return previous + current;
                }, executorService)
                .thenApplyAsync(String::toUpperCase)
                .join();

        timeTaken();
        stopWatchReset();
        return hw;
    }

    public CompletableFuture<String> helloWorldThenCompose() {
        return CompletableFuture.supplyAsync(helloWorldService::hello)
                .thenCompose(helloWorldService::worldFuture);

    }

    public String anyOf() {
        CompletableFuture<String> responseFromDb = CompletableFuture.supplyAsync(() -> {
            delay(1000);
            log("response from db");
            return "hello world";
        });

        CompletableFuture<String> responseFromRest = CompletableFuture.supplyAsync(() -> {
            delay(3002);
            log("response from rest");
            return "hello world";
        });

        CompletableFuture<String> responseFromSoap = CompletableFuture.supplyAsync(() -> {
            delay(5001);
            log("response from soap");
            return "hello world";
        });

        List<CompletableFuture<String>> responses = List.of(responseFromDb, responseFromRest, responseFromSoap);
        return (String) CompletableFuture.anyOf(responses.toArray(new CompletableFuture[0])).thenApply(o -> {
            if (o instanceof String) {
                return o;
            }
            return null;
        }).join();
    }
}
