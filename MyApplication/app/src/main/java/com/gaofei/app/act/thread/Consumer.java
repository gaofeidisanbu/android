package com.gaofei.app.act.thread;

public class Consumer implements Runnable {
    private Store mStore;
    private String id;

    public Consumer(Store store, String id) {
        this.mStore = store;
        this.id = id;
    }

    @Override
    public void run() {
        mStore.consume(id);
    }
}
