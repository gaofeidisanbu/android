package com.gaofei.app.act.thread;

public class Producer implements Runnable {
    private Store mStore;
    private String id;

    public Producer(Store store, String id) {
        this.mStore = store;
        this.id = id;
    }

    @Override
    public void run() {
        mStore.product(id);
    }
}
