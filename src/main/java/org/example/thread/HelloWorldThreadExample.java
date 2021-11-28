package org.example.thread;

import static org.example.util.CommonUtil.delay;

public class HelloWorldThreadExample {
    private static String result = "";

    private static void hello() {
        delay(700);
        result = result.concat("Hello");
    }

    private static void world() {
        delay(600);
        result = result.concat("World");
    }

    public static void main(String[] args) throws InterruptedException {

        Thread helloThread = new Thread(HelloWorldThreadExample::hello);
        Thread worldThread = new Thread(HelloWorldThreadExample::world);

        //Starting the thread
        helloThread.start();
        worldThread.start();

        //Joining the thread (Waiting for the threads to finish)
        helloThread.join();
        worldThread.join();

        System.out.println("Result is : " + result);

    }
}
