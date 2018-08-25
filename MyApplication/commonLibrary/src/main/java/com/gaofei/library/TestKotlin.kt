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


}