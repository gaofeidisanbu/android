package com.example.lib;

public class BinaryAlgorithm {

    public static void main(String[] args) {
        System.out.println(add(-5, -6));
    }

    private static int add(int a, int b) {
        int sum = a;
        while (b != 0) {
            sum = a ^ b;
            b = ((a & b) << 1);
            a = sum;
        }
        return sum;
    }
}
