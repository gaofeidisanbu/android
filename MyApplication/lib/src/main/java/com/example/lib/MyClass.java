package com.example.lib;

import com.example.lib.algorithm.SortAlgorithm;

public class MyClass {

    public static void main(String[] args) {
        try {
            SortAlgorithm sortAlgorithm = new SortAlgorithm();
            sortAlgorithm.run();
            ArrayAlgorithm.INSTANCE.run();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
