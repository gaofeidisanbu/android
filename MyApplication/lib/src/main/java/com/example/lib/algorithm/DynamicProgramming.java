package com.example.lib.algorithm;


import java.lang.reflect.Array;
import java.util.List;

public class DynamicProgramming implements Runnable {

    public static void main(String[] args) {
        new DynamicProgramming().run();
    }

    @Override
    public void run() {
//        fun1();
//        fun3(3);
//        System.out.println(fun2(10));
        fun6();
        fun7();
    }

    /**
     * 走楼梯算法
     */
    private void fun1() {
        fun1_1(10);
        System.out.println(a);
    }

    int a = 0;

    private void fun1_1(int n) {
        if (n == 0) {
            a++;
            return;
        }
        fun1_1(n - 1);
        if (n >= 2) {
            fun1_1(n - 2);
        }

    }

    private void fun1_2(int n) {
        int sum = 0;
        while (n >= 0) {
            int c = n;
        }
    }

    /**
     * 1+1+1+1+..+1
     */
    private int fun2(int n) {
        if (n == 0) {
            return 0;
        }
        return 1 + fun2(n - 1);
    }


    private int fun3(int n) {
        if (n == 0) {
            return 0;
        } else if (n == 1) {
            return 1;
        } else if (n == 2) {
            return 2;
        }
        int dp1 = 1;
        int dp2 = 2;
        int curr = 0;
        for (int i = 3; i <= n; i++) {
            curr = dp1 + dp2;
            dp1 = dp2;
            dp2 = curr;
        }
        System.out.println(curr);
        return curr;
    }

    private int fun4(List<List<Integer>> list) {
        int len = list.size();
        int lastLen = list.get(len - 1).size();
        Array[] array = new Array[lastLen];
        for (int i = 0; i < lastLen; i++) {
            for (int j = 3; j < len; j++) {
                int dp1 = list.get(i).get(j);
                int dp = dp1 + Math.max(list.get(i - 1).get(j), list.get(i - 1).get(j - 1));
            }
        }
        return 0;
    }

    private int fun5(List<List<Integer>> list) {
        int len = list.size();
        int lastLen = list.get(len - 1).size();
        Array[] array = new Array[lastLen];
        for (int i = 0; i < lastLen; i++) {
            for (int j = 3; j < len; j++) {
                int dp1 = list.get(i).get(j);
                int dp = dp1 + Math.max(list.get(i - 1).get(j), list.get(i - 1).get(j - 1));
            }
        }
        return 0;
    }

    /**
     * 二维数组的 DP
     */
    private void fun6() {
        int[][] arr = new int[4][4];
        int xLen = arr.length;
        int yLen = arr[0].length;
        int[][] dp = new int[xLen][yLen];
        for (int m = 0; m < xLen; m++) {
            dp[m][0] = 1;
        }
        for (int n = 0; n < yLen; n++) {
            dp[0][n] = 1;
        }
        for (int m = 1; m < xLen; m++) {
            for (int n = 1; n < yLen; n++) {
                dp[m][n] = dp[m - 1][n] + dp[m][n - 1];
            }
        }
        System.out.println(dp[xLen - 1][yLen - 1]);
    }

    /**
     * 二维数组的 DP优化
     */
    private void fun7() {
        int[][] arr = new int[4][4];
        int xLen = arr.length;
        int yLen = arr[0].length;
        int[] dp = new int[yLen];
        for (int n = 0; n < yLen; n++) {
            dp[n] = 1;
        }
        int last;
        for (int m = 1; m < xLen; m++) {
             last = 1;
            for (int n = 1; n < yLen; n++) {
                dp[n] = last + dp[n];
                last = dp[n];
            }
        }
        System.out.println(dp[yLen - 1]);
    }

    /**
     * 股票买卖一次
     * @param arr
     * @return
     */
    private Integer fun8(int[] arr) {
        int minBuy = arr[0];
        int maxSell = 0;
        for (int i = 1; i< arr.length; i++){
            minBuy = Math.min(minBuy, arr[i]);
            maxSell = Math.max(maxSell, arr[i] - minBuy);
        }
        return maxSell;
    }

}
