package com.example.lib;


public class TestTest {
    private String str = "1";

    public TestTest() {
    }

    public void main(String[] args) {
        fun1(new Runnable() {
            @Override
            public void run() {

            }
        });
        Runnable runnable = () -> {
            System.out.println(str);
        };
        fun1(runnable);
        fun2(() -> {
            this.str = "fun2_2";
            System.out.println(str);
        });
    }

    public void fun1(Runnable test) {
        test.run();
    }


    public void fun2(Runnable test) {
        test.run();
    }

}





