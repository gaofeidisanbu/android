package com.example.lib;

public class BinaryAlgorithm {

    public static void main(String[] args) {
        System.out.println(add(12233, 1449));
    }

    private static int add(int a, int b) {
        int sum = 0;
        while (b > 0) {
            sum = a ^ b;
            b = a & b << 1;
        }
        return sum;
    }
}
