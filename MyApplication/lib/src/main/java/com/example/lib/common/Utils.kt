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