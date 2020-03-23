package com.example.lib;

import com.example.lib.algorithm.Algorithm;
import com.example.lib.algorithm.ArrayAlgorithm;
import com.example.lib.algorithm.RecursionAlgorithm;
import com.example.lib.algorithm.SortAlgorithm;
import com.example.lib.algorithm.StackAlgorithm;

import java.util.Stack;

public class MainClass {
    final int a = 1;
    public static void main(String[] args) {
        try {
//            SortAlgorithm.INSTANCE.run();
//            StackAlgorithm.INSTANCE.run();
//            ArrayAlgorithm.INSTANCE.run();
//            RXJava.INSTANCE.run();
            TempTest.INSTANCE.run();
            Jvm jvm = new Jvm();
            jvm.fun1();

//            RecursionAlgorithm.INSTANCE.run();
//            Algorithm algorithm = new Algorithm();
//            algorithm.run();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static Integer adapter() {
        try {
            return 1;
        } catch (Exception e) {
        } finally {
            return 2;
        }
    }

    public static int div() throws Exception{
        try {
            int a = 5 / 0;
            return a;
        } catch (ArithmeticException e) {
            System.out.println("catch in div");
            throw new Exception("Exception in div"); // 抛出新的异常
        } finally {
            System.out.println("finally in div");
            throw new Exception("Exception in Finally");  // 抛出新的异常
        }
    }




}
