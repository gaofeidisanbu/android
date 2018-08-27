package com.gaofei.app.act.thread;

public class ThreadTest {
    public static void main(String[] args) {
        Store store = new Store();
        new Thread(new Producer(store, "producer1")).start();
        new Thread(new Producer(store, "producer2")).start();
        new Thread(new Producer(store, "producer3")).start();
        new Thread(new Producer(store, "producer4")).start();
        new Thread(new Consumer(store, "consumer1")).start();
        new Thread(new Consumer(store, "consumer2")).start();
        new Thread(new Consumer(store, "consumer3")).start();
        new Thread(new Consumer(store, "consumer4")).start();
        new Thread(new Consumer(store, "consumer5")).start();
    }

}
