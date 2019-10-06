package com.example.lib.algorithm;

public class Algorithm implements Runnable{

    @Override
    public void run() {
        int n = 2;
        int[] a = new int[n];
        coding(a, 2-1);
    }

    private void coding (int[] b, int n) {
        if (n==0) {
            b[n] = 0;outBn(b);
            b[n] = 1;outBn(b);
        }
        else {
            b[n] = 0; coding(b,n-1);
            b[n] = 1; coding(b,n-1);
        }
    }
    private void outBn (int[] b) {
        for (int i=0;i<b.length;i++)
            System.out.print(b[i]);
        System.out.println();
    }
}
