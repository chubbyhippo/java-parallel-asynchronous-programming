package org.example.callback;

import java.util.function.IntConsumer;

public class CallBackExample {
    public static void main(String[] args) {
        int x = 1337;
        Result result = new Result();

        f(x, (int y) -> {
            result.setLeft(y);
            System.out.println((result.getLeft() + result.getRight()));
        } );

        g(x, (int z) -> {
            result.setRight(z);
            System.out.println((result.getLeft() + result.getRight()));
        });

    }

    private static void f(int x, IntConsumer dealWithResult) {
        dealWithResult.accept(Functions.f(x));
    }

    private static void g(int x, IntConsumer dealWithResult) {
        dealWithResult.accept(Functions.g(x));
    }
}
