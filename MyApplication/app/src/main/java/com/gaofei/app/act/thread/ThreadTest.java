package com.gaofei.app.act.thread;

public class ThreadTest {
    public static void main(String[] args) {
        Store store = new Store();
        new Thread(new Consumer(store, "consumer1", 2)).start();
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        new Thread(new Producer(store, "producer1")).start();
        new Thread(new Producer(store, "producer2")).start();
        new Thread(new Producer(store, "producer3")).start();
        new Thread(new Producer(store, "producer4")).start();
        new Thread(new Consumer(store, "consumer2", 1)).start();
        new Thread(new Consumer(store, "consumer3", 3)).start();
        new Thread(new Consumer(store, "consumer4", 4)).start();
        new Thread(new Consumer(store, "consumer5", 10)).start();
    }

}
