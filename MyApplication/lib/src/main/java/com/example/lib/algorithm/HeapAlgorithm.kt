package com.example.lib.algorithm

import com.example.lib.common.printArray
import kotlin.math.max

object HeapRunnable : Runnable {
    override fun run() {
        fun test() {
            val array = arrayOf<Int>(5, 1, 13, 3, 16, 7, 10, 14, 6, 9)
            array.printArray()
            val head = HeapAlgorithm(array)
            array.printArray()
            head.sort()
            array.printArray()

        }
    }

}

class HeapAlgorithm(val array: Array<Int>) {

    //数量
    val size = array.size

    //层次
    val h = size / 2

    init {
        adjustHead()
    }


    /**
     * 时间复杂度分析：计算方式观察代码
     * 假设有个n个元素，则最后一个非叶子节点为: h = n/2，每一层从h - 1开始，倒数第二次需要比较1次
     * 第i层的根节点总共需要被交换的次数为 2^(i)*(h - i)
     * i从h-1 .. 0
     * Sn = 2^(h-1) + 2^(h-2)*(2) + ... + 2^(1)*(h - 1)+ 2^(0)*(h)
     *  2Sn = 2^(h) + 2^(h - 1) * 2 + ...+ 2^(2)*(h - 1)+ 2^(1)*(h)
     *
     *  sn = sSn - sn =  2^(h) +  2^(h - 1)+  2^(h - 2) + ... + 2 - h
     *   = 2 * 2^h - 2 - h
     *   h = log2n
     *   sn = 2n - 2 - log2n
     */
    private fun adjustHead() {
        for (i in h - 1 downTo 0) {
            val leftNodeIndex = 2 * i + 1
            val rightNodeIndex = 2 * i + 2
            if (rightNodeIndex >= size) {
                if (leftNodeIndex == size - 1) {
                    if (array[leftNodeIndex] > array[i]) {
                        val temp = array[i]
                        array[i] = array[leftNodeIndex]
                        array[leftNodeIndex] = temp
                    }
                }
            } else {
                val maxChildNode = max(array[leftNodeIndex], array[rightNodeIndex])
                if (maxChildNode > array[i]) {
                    val maxChildIndex = if (maxChildNode == array[leftNodeIndex]) leftNodeIndex else rightNodeIndex
                    val temp = array[i]
                    array[i] = array[maxChildIndex]
                    array[maxChildIndex] = temp
                    signDown(maxChildIndex)
                }
            }
        }
    }

    fun signUp(childIndex: Int) {
        var i = childIndex
        while (i >= 0) {
            val leftNodeIndex = 2 * i + 1
            val rightNodeIndex = 2 * i + 2
            if (rightNodeIndex >= size) {
                if (leftNodeIndex == size - 1) {
                    if (array[leftNodeIndex] > array[i]) {
                        val temp = array[i]
                        array[i] = array[leftNodeIndex]
                        array[leftNodeIndex] = temp
                        break
                    } else {
                        break
                    }
                } else {
                    break
                }
            } else {
                val maxChildNode = max(array[leftNodeIndex], array[rightNodeIndex])
                if (maxChildNode > array[i]) {
                    val maxChildIndex = if (maxChildNode == array[leftNodeIndex]) leftNodeIndex else rightNodeIndex
                    val temp = array[i]
                    array[i] = array[maxChildIndex]
                    array[maxChildIndex] = temp
                    break
                } else {
                    break
                }
            }
        }
    }

    private fun signDown(rootIndex: Int) {
        var i = rootIndex
        while (i <= h - 1) {
            val leftNodeIndex = 2 * i + 1
            val rightNodeIndex = 2 * i + 2
            if (rightNodeIndex >= size) {
                if (leftNodeIndex == size - 1) {
                    if (array[leftNodeIndex] > array[i]) {
                        val temp = array[i]
                        array[i] = array[leftNodeIndex]
                        array[leftNodeIndex] = temp
                        break
                    } else {
                        break
                    }
                } else {
                    break
                }
            } else {
                val maxChildNode = max(array[leftNodeIndex], array[rightNodeIndex])
                if (maxChildNode > array[i]) {
                    val maxChildIndex = if (maxChildNode == array[leftNodeIndex]) leftNodeIndex else rightNodeIndex
                    val temp = array[i]
                    array[i] = array[maxChildIndex]
                    array[maxChildIndex] = temp
                    i = maxChildIndex
                } else {
                    break
                }
            }
        }
    }

    /**
     * 时间复杂度为log(n-1)+log(n-2)+...+log(1) = nLogn
     */
    fun sort() {
        for (i in size - 1 downTo 1) {
            var h = i / 2
            val temp = array[0]
            array[0] = array[i]
            array[i] = temp
            var j = 0
            val size = i - 1
            while (j <= h) {
                val leftNodeIndex = 2 * j + 1
                val rightNodeIndex = 2 * j + 2
                if (rightNodeIndex >= size) {
                    if (leftNodeIndex == size - 1) {
                        if (array[leftNodeIndex] > array[j]) {
                            val temp = array[j]
                            array[j] = array[leftNodeIndex]
                            array[leftNodeIndex] = temp
                            break
                        } else {
                            break
                        }
                    } else {
                        break
                    }
                } else {
                    val maxChildNode = max(array[leftNodeIndex], array[rightNodeIndex])
                    if (maxChildNode > array[j]) {
                        val maxChildIndex = if (maxChildNode == array[leftNodeIndex]) leftNodeIndex else rightNodeIndex
                        val temp = array[j]
                        array[j] = array[maxChildIndex]
                        array[maxChildIndex] = temp
                        j = maxChildIndex
                    } else {
                        break
                    }
                }
            }
        }
    }
}
