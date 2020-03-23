package com.example.lib;

/**
 * @Author gaofei
 * @Date 2020-01-20-15:14
 * @Email gaofei@guanghe.tv
 */
public class Jvm {
    //
    public DJvm jvm1 = new DJvm();
    public final DJvm jvm2 = new DJvm();
    public static DJvm jvm3 = getDJvm();
    public static final DJvm jvm4 = getDJvm();

    public static void main(String[] args) {
        System.out.println("main start");
    }

    public void fun1() {
        int a = 4;
        int b = 5;
        int c = max(a, b);
        DJvm jvm5 = new DJvm();
        System.out.println(c);
        System.out.println(jvm1);
        System.out.println(jvm1.s1);
        System.out.println(jvm1.s2);
        System.out.println(jvm2);
        System.out.println(jvm3);
        System.out.println(jvm5);

        System.out.println(jvm4);
    }

    public static DJvm getDJvm() {
        return new DJvm();
    }

    public int max(int a, int b) {
        return a > b ? a : b;
    }
}

class DJvm {
    public String s1 = "s1";
    public final String s2 = "s2";
    public static final String s3 = "s3";

    static {
        System.out.println("DJvm init");
    }

}


