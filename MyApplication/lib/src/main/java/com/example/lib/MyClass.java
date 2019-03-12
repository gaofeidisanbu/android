package com.example.lib;

import com.example.lib.algorithm.SortAlgorithm;

public class MyClass {

    public static void main(String[] args) {
        try {

            SortAlgorithm.INSTANCE.run();
            ArrayAlgorithm.INSTANCE.run();
            RXJava.INSTANCE.run();
            TempTest.INSTANCE.run();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
