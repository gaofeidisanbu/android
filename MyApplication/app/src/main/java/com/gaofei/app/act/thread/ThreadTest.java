package com.gaofei.app.act.thread;

import java.util.ArrayList;
import java.util.List;

public class ThreadTest {
    private static int productCount = 0;
    private static int consumeCount = 0;

    public static void main(String[] args) {
//        Store store = new Store();
//        new Thread(new Consumer(store, "consumer1", 2)).start();
//        try {
//            Thread.sleep(100);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
//        new Thread(new Producer(store, "producer1")).start();
//        new Thread(new Producer(store, "producer2")).start();
//        new Thread(new Producer(store, "producer3")).start();
//        new Thread(new Producer(store, "producer4")).start();
//        new Thread(new Consumer(store, "consumer2", 1)).start();
//        new Thread(new Consumer(store, "consumer3", 3)).start();
//        new Thread(new Consumer(store, "consumer4", 4)).start();
//        new Thread(new Consumer(store, "consumer5", 10)).start();
        Store1 store1 = new Store1();
        new Store1.Producer1("productThread" + productCount++, store1).start();
        new Store1.Producer1("productThread" + productCount++, store1).start();
        new Store1.Consumer1("consumeThread" + consumeCount++, store1, 10).start();
        new Store1.Consumer1("consumeThread" + consumeCount++, store1, 50).start();
    }


}


class Store1 {
    private List<Integer> list = new ArrayList<>();
    private static int count = 0;

    private static final int MAX_NUM = 10;


    public void produce() {
        while (!Thread.currentThread().isInterrupted() && count < 20) {
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
                Thread.interrupted();
            }

            synchronized (list) {
                while (list.size() > MAX_NUM) {
                    try {
                        list.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                        Thread.interrupted();
                    }
                }
                list.add(++count);
                System.out.println(Thread.currentThread().getName() + " produce " + count);
                list.notifyAll();

            }
        }
        System.out.println(Thread.currentThread().getName() + " produce Thread is stop");


    }

    public void consume(int consumeCount) {
        while (!Thread.currentThread().isInterrupted() && consumeCount > 0) {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
                Thread.interrupted();
            }

            synchronized (list) {
                while (list.size() <= 0) {
                    try {
                        list.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                        Thread.interrupted();
                    }
                }
                int consume = list.remove(0);
                consumeCount--;
                System.out.println(Thread.currentThread().getName() + " consume " + consume);
                list.notifyAll();
            }
        }

        System.out.println(Thread.currentThread().getName() + " consume Thread is stop");
    }

    static class Producer1 extends Thread {
        Store1 store1;

        Producer1(String threadName, Store1 store1) {
            super(threadName);
            this.store1 = store1;
        }

        @Override
        public void run() {
            super.run();
            store1.produce();
        }
    }

    static class Consumer1 extends Thread {
        Store1 store1;
        int consumeCount;
        Consumer1(String threadName, Store1 store1, int consumeCount) {
            super(threadName);
            this.store1 = store1;
            this.consumeCount = consumeCount;
        }

        @Override
        public void run() {
            super.run();
            store1.consume(consumeCount);
        }
    }


}
