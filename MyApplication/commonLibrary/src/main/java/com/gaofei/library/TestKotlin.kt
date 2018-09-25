package com.gaofei.library

import com.gaofei.library.utils.LogUtils

fun main(args: Array<String>) {
    recycleUserItemView()
}

private fun recycleUserItemView() {
    val size = 4
    val maxNum = 3
    for (i in maxNum until size) {
        print(i)
    }
}

class TestKotlin {

    fun main(args: Array<String>) {
        val size = 10
        val maxNum = 4
        if (size > maxNum) {
            for (i in size - 1 downTo  maxNum) {
                LogUtils.d("maxNum = $maxNum i = $i")
            }
        }

    }

    interface Source<out T> {
        fun nextT(): T
    }

    fun demo(strs: Source<String>) {
        val objects: Source<Any> = strs // 这个没问题，因为 T 是一个 out-参数
        // ……
    }

    interface Comparable<in T> {
        operator fun compareTo(other: T): Int
    }

    fun demo(x: Comparable<Number>) {
        x.compareTo(1.0) // 1.0 拥有类型 Double，它是 Number 的子类型
        // 因此，我们可以将 x 赋给类型为 Comparable <Double> 的变量
        val y: Comparable<Double> = x // OK！
    }


}