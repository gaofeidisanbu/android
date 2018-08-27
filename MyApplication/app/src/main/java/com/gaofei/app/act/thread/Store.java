package com.gaofei.app.act.thread;

import java.util.ArrayList;
import java.util.List;

public class Store {
    public static final String PRODUCT_TAG = "product";
    public static final String CONSUME_TAG = "consume";
    private List<String> mProduct;
    private static final int mMaxProduct = 20;
    private static final int mMinProduct = 2;
    public static int mIndex = 0;


    public Store() {
        this.mProduct = new ArrayList<>();
    }

    public void consume(String id) {
        synchronized (mProduct) {
            while (true) {
                if (mProduct.size() <= mMinProduct) {
                    System.out.println(getCommonTag(0, id) + "产品低于：" + mMinProduct);
                    mProduct.notifyAll();
                }
                if (mProduct.size() >= 0) {
                    String product = mProduct.remove(0);
                    System.out.println(getCommonTag(0, id) + "消费产品编号：product = " + product);
                    try {
                        Thread.sleep(2000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                        System.out.println(getCommonTag(0, id) + " sleep " + e.getMessage());
                        break;
                    }
                } else {
                    try {
                        mProduct.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                        System.out.println(getCommonTag(0, id) + " wait " + mIndex);
                        break;
                    }
                }

            }


        }
    }


    public void product(String id) {
        synchronized (mProduct) {
            while (true) {
                try {
                    if (mProduct.size() > mMaxProduct) {
                        System.out.println(getCommonTag(1, id) + "产品高于：" + mMaxProduct);
                        mProduct.wait();
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                    System.out.println(getCommonTag(1, id) + " wait " + e.getMessage());
                    break;
                }
                System.out.println(getCommonTag(1, id) + "生产产品编号：" + mIndex);
                mProduct.add(mIndex + "");
                mIndex++;
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                    System.out.println(getCommonTag(1, id) + " sleep " + e.getMessage());
                    break;
                }
            }

        }
    }


    private String getCommonTag(int type, String id) {
        StringBuilder sb = new StringBuilder();
        if (type == 0) {
            sb.append(PRODUCT_TAG);
        } else {
            sb.append(CONSUME_TAG);
        }
        sb.append(" id = ");
        sb.append(id);
        sb.append(" ");
        sb.append(" threadId = ");
        sb.append(Thread.currentThread().getId());
        sb.append(" ");
        return sb.toString();
    }



}
