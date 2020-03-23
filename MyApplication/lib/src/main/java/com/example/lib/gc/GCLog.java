package com.example.lib.gc;


/**
 * VM Args: -Xms20m -Xmx20m -XX:+PrintGCDetails
 */
public class GCLog {

//    public static void main(String[] args) {
//        byte[] bytes = new byte[10 * 1024 * 1024];
//        System.gc();
//    }

//    public static void main(String[] args) {
//        {
//            byte[] bytes = new byte[10 * 1024 * 1024];
//        }
//        System.gc();
//    }

    public static void main(String[] args) {
        {
            byte[] bytes = new byte[10 * 1024 * 1024];
        }
        int a = 0;
        System.gc();
    }
}
