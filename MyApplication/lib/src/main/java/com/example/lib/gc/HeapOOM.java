package com.example.lib.gc;

import java.lang.ref.ReferenceQueue;
import java.lang.ref.SoftReference;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;


/**
 * VM Args: -Xms20m -Xmx20m -XX:+PrintGCDetails -XX:+HeapDumpOnOutOfMemoryError
 */
public class HeapOOM {

    static class OOMObject {

    }

    public static void main(String[] args) throws InterruptedException {
        Object obj = new Object();
        ReferenceQueue rq = new ReferenceQueue();
//        WeakReference<Object> sr = new WeakReference<>(obj, rq);
        SoftReference<Object> sr = new SoftReference(obj, rq);

        obj = null;
        System.gc();
        Thread.sleep(1000);
        if (sr.get() == null) {
            System.out.println("aaaaaaaaaaaaaa");
        } else  {
            System.out.println("11111111");
        }
    }
}
