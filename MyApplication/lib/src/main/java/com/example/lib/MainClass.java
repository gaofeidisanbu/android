package com.example.lib;

import com.example.lib.algorithm.ArrayAlgorithm;
import com.example.lib.algorithm.SortAlgorithm;
import com.example.lib.algorithm.StackAlgorithm;

import java.util.Stack;

public class MainClass {
    final int a = 1;
    public static void main(String[] args) {
        try {
            SortAlgorithm.INSTANCE.run();
            StackAlgorithm.INSTANCE.run();
//            ArrayAlgorithm.INSTANCE.run();
//            RXJava.INSTANCE.run();
//            TempTest.INSTANCE.run();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }




}
