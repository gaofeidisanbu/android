package com.example.lib.algorithm

import java.lang.RuntimeException

class Stack(val size: Int) {

    val arrays: Array<Int?> = arrayOfNulls<Int>(size)

    var stackPoint = -1

    fun push(data: Int) {
        if (stackPoint >= size - 1) {
            throw RuntimeException("stack is full")
        }
        arrays[++stackPoint] = data
    }

    fun pop(): Int? {
        if (stackPoint > 0) {
            return arrays[stackPoint--]
        }
        return null
    }

}