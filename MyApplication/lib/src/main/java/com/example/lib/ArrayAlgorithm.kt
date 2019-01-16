package com.example.lib

import java.lang.StringBuilder

object ArrayAlgorithm : Runnable {


    override fun run() {
        arrayMerge1()
        print("11111111111111111111111")
    }

    private val arrayA = arrayOf(3, 6, 28, 29, 45)
    private val arrayB = arrayOf(1, 9, 20, 21, 30)
    /**
     * 下面的算法是将两个已经有序的数组 a[0,m-1]， b[0,n-1]合并为一个更大的有序数组
     */
    private fun arrayMerge1() {
        val arrayALength = arrayA.size
        val arrayBLength = arrayB.size
        val arrayCLength = arrayALength + arrayBLength
        val arrayC = arrayOfNulls<Int>(arrayCLength)
        var pa = 0
        var pb = 0
        var pc = 0
        while (pc < arrayCLength - 1) {
            when {
                pa < arrayALength && pb < arrayBLength -> {
                    if (arrayA[pa] < arrayB[pb]) {
                        arrayC[pc] = arrayA[pa]
                        pa++
                    } else {
                        arrayC[pc] = arrayA[pb]
                        pb++
                    }
                    pc++
                }
                pa == arrayALength - 1 && pb < arrayBLength - 1 -> {
                    System.arraycopy(arrayB, pb, arrayC, pc, arrayBLength - pb)
                    pc = arrayCLength - 1
                }
                pa < arrayALength - 1 && pb == arrayBLength - 1 -> {
                    System.arraycopy(arrayA, pa, arrayC, pc, arrayALength - pa)
                    pc = arrayCLength - 1
                }

            }
        }
        val sb = StringBuilder()
        arrayC.forEach {
            sb.append(" ${it ?: "null"} ")
        }
        println(sb.toString())

    }
}