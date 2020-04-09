package com.example.lib;

public class ThreadTest2 implements Runnable {

    private static volatile int race = 0;
    private static volatile boolean isStop = false;

    public void increase() {
        race++;
    }

    public void shuntDown() {
        isStop = true;
    }

    @Override
    public void run() {
        for (int i = 0; i < 20; i++) {
            new Thread(() -> {
                for (int j = 0; j < 1000; j++) {
                    increase();
                }
            }).start();
        }
        while (Thread.activeCount() > 2) {
            System.out.println(Thread.activeCount());
            Thread.yield();
        }
        System.out.println(race);
    }
}
