package com.gaofei.app.act.thread;

public class Consumer implements Runnable {
    private Store mStore;
    private String id;
    private int consumeNum;

    public Consumer(Store store, String id, int consumeNum) {
        this.mStore = store;
        this.id = id;
        this.consumeNum = consumeNum;
    }

    @Override
    public void run() {
        mStore.consume(id, consumeNum);
    }
}
