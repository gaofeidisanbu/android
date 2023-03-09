package com.example.lib;

import com.example.lib.algorithm.Algorithm;
import com.example.lib.algorithm.ArrayAlgorithm;
import com.example.lib.algorithm.CommonAlgorithm;
import com.example.lib.algorithm.RecursionAlgorithm;
import com.example.lib.algorithm.SortAlgorithm;
import com.example.lib.algorithm.StackAlgorithm;
import com.example.lib.algorithm.TreeAlgorithm;
import com.example.lib.load.SimpleClassLoader;
import com.example.lib.thread.Semaphore;

import java.util.LinkedList;
import java.util.Locale;
import java.util.Stack;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.example.lib.thread.SemaphoreKt.ThirdThreadABC;

public class MainClass {
    final int a = 1;

    {
        int b = 3;
        System.out.println("a");
    }
    public static void main(String[] args) {
        try {
//            MainClass mainClass = new MainClass();
//            SortAlgorithm.INSTANCE.run();
//            StackAlgorithm.INSTANCE.run();
//            ArrayAlgorithm.INSTANCE.run();
//            RXJava.INSTANCE.run();
//            TempTest.INSTANCE.run();
//            RecursionAlgorithm.INSTANCE.run();
//            Algorithm algorithm = new Algorithm();
//            algorithm.run();
//            ClassLoader classLoader = new SimpleClassLoader();
//            Class clazz = classLoader.loadClass("com.example.lib.load.TestClassLoad");
//            clazz.newInstance();
//            System.out.println(adapter());
//            TreeAlgorithm.INSTANCE.run();
//            new ThreadTest2().run();
//            CommonAlgorithm.INSTANCE.run();
//            SortAlgorithm.INSTANCE.run();
//           ThirdThreadABC();
//            String aa = "0";
//            String bb = "0";
//            System.out.println(aa == bb);
//
//            String cc = "-1";
//            String dd = "-1";
//            System.out.println(cc == dd);
            clazz();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void  clazz() {
//        ArrayList canvasActivity = new java.util.ArrayList<Object>();
//        System.out.print(canvasActivity.getClass().isAssignableFrom(List.class));
//        System.out.print(List.class.isAssignableFrom(canvasActivity.getClass()));
        System.out.println("aaaaaaa "+Pattern.matches("[a-zA-Z0-9]+[13579ace]$", "c2b07e8f67ec234b"));
        System.out.println("aaaaaaa "+Pattern.matches("[a-zA-Z0-9]+[24680bdf]$", "c2b07e8f67ec234b"));
    }

    private static int realStringLength(String str) {
        int count = 0;
        for (int i = 0; i < str.length(); i++) {
            char c = str.charAt(i);
            if (Character.isHighSurrogate(c)) {
                System.out.println("c = "+c);
                i++;
            }

            if ( Character.isLowSurrogate(c)) {
                System.out.println("c = "+c);
                i++;
            }
            count++;
        }

        return count;
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
