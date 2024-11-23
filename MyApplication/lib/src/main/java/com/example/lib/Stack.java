package com.example.lib;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by gaofei3 on 2024/11/22
 * Describe:
 */
public class Stack {
    private final List<Integer> mList = new ArrayList<>();

    public void push(int value) {
        mList.add(value);
    }

    public Integer peek() {
        if (isEmpty()) {
            return null;
        }
        return mList.get(size() - 1);
    }

    public Integer pop() {
        if (isEmpty()) {
            return null;
        }
        return mList.remove(size() - 1);
    }

    public boolean isEmpty() {
        return mList.isEmpty();
    }

    public int size() {
        return mList.size();
    }

    public void clear() {
        mList.clear();
    }

}
