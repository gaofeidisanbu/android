package com.example.lib.common

import java.lang.StringBuilder

fun <T> Array<T>.printArray() {
    val sb = StringBuilder()
    this.forEach {
        sb.append("$it ")
    }
    println(sb)
}

fun <T> Iterable<T>.print() {
    val sb = StringBuilder()
    this.forEach {
        sb.append("$it ")
    }
    println(sb)
}

/**
 * 二分法查找
 */
fun Array<Int>.binarySearch(target: Int): Int {
    val len = this.size
    var start = 0
    var end = len - 1
    while (start <= end) {
        val mid = (end + start) / 2
        if (this[mid] == target) {
            return mid
        }
        if (this[mid] > target) {
            end = mid - 1
        }
        if (this[mid] < target) {
            start = mid + 1
        }
    }
    return -1
}

/**
 * 是否是字母
 */
fun Char.isLetter(): Boolean {
    return this.isLetter()
}

