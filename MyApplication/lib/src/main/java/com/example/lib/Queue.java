package com.example.lib;

/**
 * Created by gaofei3 on 2024/11/22
 * Describe:
 */
public class Queue {

    private final Stack mStack = new Stack();
    private final Stack mTempStack = new Stack();

    public void push(int value) {
        mStack.push(value);
    }

    public Integer pop() {
        if (mStack.isEmpty()) {
            return null;
        }
        mTempStack.clear();
        while (!mStack.isEmpty()) {
            mTempStack.push(mStack.pop());
        }
        int targetValue = mTempStack.pop();
        while (!mTempStack.isEmpty()) {
            mStack.push(mTempStack.pop());
        }
        return targetValue;
    }

    public Integer peek() {
        if (mStack.isEmpty()) {
            return null;
        }
        while (!mStack.isEmpty()) {
            mTempStack.push(mStack.pop());
        }
        int targetValue = mTempStack.peek();
        while (!mTempStack.isEmpty()) {
            mStack.push(mTempStack.pop());
        }
        return targetValue;
    }

    public boolean isEmpty() {
        return mStack.isEmpty();
    }

    public int size() {
        return mStack.size();
    }

    public void clear() {
        mStack.clear();
    }

}
