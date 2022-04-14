package com.gaofei.library

import android.app.Activity
import com.gaofei.library.utils.LogUtils

fun main(args: Array<String>) {
    clazz()

}

fun convertDuration(mill: Long): Triple<String, String, String> {
    val ss = 1000
    val mi = ss * 60
    val hh = mi * 60
    val hour = mill / hh
    val minute = (mill - hour * hh) / mi
    val second = (mill - hour * hh - minute * mi) / ss
    return Triple(getFillZeroStr(hour, 2), getFillZeroStr(minute, 2), getFillZeroStr(second, 2))
}

private fun getFillZeroStr(num: Long, unit: Int): String {
    val newStr = StringBuilder()
    val str = num.toString() + ""
    val len = str.length
    val diff = unit - len
    if (diff > 0) {
        for (i in 0 until diff) {
            newStr.append(0)
        }
    }
    newStr.append(str)
    return newStr.toString()
}

private fun recycleUserItemView() {
    val size = 4
    val maxNum = 3
    for (i in maxNum until size) {
        print(i)
    }
}

val aa = arrayListOf<Any>(java.util.List::class.java)

private fun clazz() {
    var canvasActivity = java.util.ArrayList<Any>()
    val listClass = java.util.List::class.java

    for (it in aa) {
        println("${it}")
        println("${(it as Class<*>).isAssignableFrom(canvasActivity::class.java)}")
    }
}

class TestKotlin {

    fun main(args: Array<String>) {
        val size = 10
        val maxNum = 4
        if (size > maxNum) {
            for (i in size - 1 downTo maxNum) {
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