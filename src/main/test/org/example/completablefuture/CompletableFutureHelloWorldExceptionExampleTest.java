package org.example.completablefuture;

import org.example.service.HelloWorldService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CompletableFutureHelloWorldExceptionExampleTest {

    @Mock
    HelloWorldService helloWorldService;

    @InjectMocks
    CompletableFutureHelloWorldExceptionExample completableFutureHelloWorldException;


    @Test
    void helloWorld3AsyncCallsHandle() {
        when(helloWorldService.hello())
                .thenThrow(new RuntimeException("Exception Occurred"));
        when(helloWorldService.world())
                .thenReturn("555 abc ")
                .thenCallRealMethod();

        String result = completableFutureHelloWorldException.helloWorld3AsyncCallsHandle();

        assertEquals("555 ABC  HI COMPLETABLEFUTURE!", result);
    }

    @Test
    void helloWorld3AsyncCallsHandle2() {
        when(helloWorldService.hello())
                .thenThrow(new RuntimeException("Exception Occurred"));
        when(helloWorldService.world())
                .thenThrow(new RuntimeException("Exception Occurred"));

        String result = completableFutureHelloWorldException.helloWorld3AsyncCallsHandle();

        assertEquals(" HI COMPLETABLEFUTURE!", result);
    }

    @Test
    void helloWorld3AsyncCallsHandle3() {
        when(helloWorldService.hello())
                .thenCallRealMethod();
        when(helloWorldService.world())
                .thenCallRealMethod();

        String result = completableFutureHelloWorldException.helloWorld3AsyncCallsHandle();

        assertEquals("HELLO WORLD! HI COMPLETABLEFUTURE!", result);
    }

    @Test
    void helloWorld3AsyncCallsExceptionally() {
        when(helloWorldService.hello())
                .thenCallRealMethod();
        when(helloWorldService.world())
                .thenCallRealMethod();

        String result = completableFutureHelloWorldException.helloWorld3AsyncCallsExceptionally();

        assertEquals("HELLO WORLD! HI COMPLETABLEFUTURE!", result);
    }

    @Test
    void helloWorld3AsyncCallsExceptionally2() {
        when(helloWorldService.hello())
                .thenThrow(new RuntimeException("Exception Occurred"));
        when(helloWorldService.world())
                .thenThrow(new RuntimeException("Exception Occurred"));

        String result = completableFutureHelloWorldException.helloWorld3AsyncCallsExceptionally();

        assertEquals(" HI COMPLETABLEFUTURE!", result);
    }
}
