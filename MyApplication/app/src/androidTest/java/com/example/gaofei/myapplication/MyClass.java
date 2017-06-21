package com.example.gaofei.myapplication;

import android.util.Log;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

public class MyClass {
    public static final String TAG  = "MyClass";
    public static void main(String[] args) {
        try {
            foo();
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("error");
            System.out.println(e);
        }
    }

    private static void foo() throws IOException {
        Log.d(TAG,"-----------start");
        DatagramSocket socket = new DatagramSocket();

        byte[] sendBuff = toByteArray("1a:29:e0:9e:5e:66:51:00:09:f9:0a:0b:07:69:aa:43:10:11:12:13:4f:01:5a:a1:47:09:62:9e:71:3c:66:9a:4d:00:5a:a6:49:04:5e:a2:2a:29:2a:2b:2c:2d:2e:2f:30:31:32:33:34:35:36:37:38:39:3a:3b:3c:3d:3e:3f");
        printHexString(sendBuff);

        DatagramPacket sendPacket = new DatagramPacket(sendBuff, sendBuff.length, InetAddress.getByName("10.8.1.240"), 60000);
        socket.send(sendPacket);

        byte[] buff = new byte[64];
        DatagramPacket packet = new DatagramPacket(buff, buff.length);
        socket.receive(packet);

        String s = new String(packet.getData(), 0, packet.getLength());
        System.out.println("========================>");
        Log.d(TAG,"-----------end");
        printHexString(packet.getData());

        socket.close();
    }

    public static byte[] toByteArray(String hexString) {
//        1a29 e09e 5e6651000 9f90a0b0769aa4310111 2134 f015aa14709629e713c669a4d005aa649045ea2 2a 292a2b2c2d2e2f303132333435363738393a3b3c3d3e3f
//        1a29 d6dd 5e6651000 9f90a0b0769aa4310111 2134 f015aa14709629e713c669a4d005aa649045ea2 27 292a2b2c2d2e2f303132333435363738393a3b3c3d3e3f

        hexString = hexString.replaceAll(":", "");
        hexString = hexString.toLowerCase();
        final byte[] byteArray = new byte[hexString.length() / 2];
        int k = 0;
        for (int i = 0; i < byteArray.length; i++) {//因为是16进制，最多只会占用4位，转换成字节需要两个16进制的字符，高位在先
            byte high = (byte) (Character.digit(hexString.charAt(k), 16) & 0xff);
            byte low = (byte) (Character.digit(hexString.charAt(k + 1), 16) & 0xff);
            byteArray[i] = (byte) (high << 4 | low);
            k += 2;
        }
        return byteArray;
    }

    public static void printHexString(byte[] b) {
//        1a:29:1e:db:5e:66:51:00:09:09:0c:5f:06:05:0f:ff:14:07:15:03:eb:15:16:17:18:19:1a:1b:1c:1d:1e:1f:20:21:22:23:24:25:26:27:2a:29:2a:2b:2c:2d:2e:2f:30:31:32:33:34:35:36:37:38:39:3a:3b:3c:3d:3e:3f
//        1A29 228D 5E66510009090C5F06050FFF14063 151E B15161718191A1B1C1D1E1F20212223242526272A292A2B2C2D2E2F303132333435363738393A3B3C3D3E3F

//        1a29 4187 5e6651000 9090c5f06050fff140 63655e b15161718191a1b1c1d1e1f20212223242526272a292a2b2c2d2e2f303132333435363738393a3b3c3d3e3f
//        1a29 cadc 5e6651000 9090c5f06050fff140 63741e b15161718191a1b1c1d1e1f20212223242526272a292a2b2c2d2e2f303132333435363738393a3b3c3d3e3f

//!!      1a29 4044 5e6651000 9090c5f06050fff140 24b0ae b15161718191a1b1c1d1e1f20212223242526272a292a2b2c2d2e2f303132333435363738393a3b3c3d3e3f

//        1a29 0422 5e6651000 9fd0a0b0769aa4310111 213c 67895b2ca7499bece709dbaf24ca186f648a582 24 292a2b2c2d2e2f303132333435363738393a3b3c3d3e3f
//        1a29 29dd 5e6651000 9f80a0b0769aa4310111 213c 3a3a181cbab99befd9e9dbac1a2a186c5a6a582 25 292a2b2c2d2e2f303132333435363738393a3b3c3d3e3f


        for (int i = 0; i < b.length; i++) {
            String hex = Integer.toHexString(b[i] & 0xFF);
            if (hex.length() == 1) {
                hex = '0' + hex;
            }
            System.out.print(hex.toLowerCase());
        }
    }
}
