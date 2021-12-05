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
}
