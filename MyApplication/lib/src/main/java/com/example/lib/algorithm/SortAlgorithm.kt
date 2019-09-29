package com.example.lib.algorithm

import com.example.lib.common.binarySearch
import com.example.lib.common.printArray
import java.util.*
import kotlin.collections.ArrayList

object SortAlgorithm : Runnable {

    override fun run() {
//        sss(arrayOf(1, 2, 3))
//        val arra = arrayOf(ArrayOrInt(1), ArrayOrInt(arrayOf(ArrayOrInt(2), ArrayOrInt(3), ArrayOrInt(arrayOf(ArrayOrInt(4))))))
//        sss4(arra).print()
//        binarySearchTest()
        stringWordReverse("Hello        world")

    }

    private fun binarySearchTest() {
        val nums = arrayOf(1, 3, 5, 7, 9)
        println(nums.binarySearch(9))
    }

    /**
     * 描述
     * 计一个算法，找出只含素因子2，3，5 的第 n 小的数。
     * 符合条件的数如：1, 2, 3, 4, 5, 6, 8, 9, 10, 12...
     * 1 也是一个丑数
     */

    fun solution1(n: Int): Int {
        val array = arrayOfNulls<Int>(n)
        array[0] = 1
        val p2 = 2
        val p3 = 3
        val p5 = 5
        var p2Index = 0
        var p3Index = 0
        var p5Index = 0
        for (i in 0 until n) {
            val next = Math.min(array[p2]!! * 2, Math.min(array[p3]!! * 3, array[p5]!! * 5))
            if (array[p2]!! * 2 == next) {
                p2Index++
            }
            if (array[p3]!! * 3 == next) {
                p3Index++
            }
            if (array[p5]!! * 5 == next) {
                p5Index++
            }
        }

        return array[n - 1]!!
    }

    fun stringFind(targetStr: String, sourceStr: String) {
        var start = 0
        var i = 0
        var length = 0
        while (i < targetStr.length && start < sourceStr.length - targetStr.length) {
            if (targetStr.toCharArray()[i] == sourceStr.toCharArray()[start + i]) {
                i++
                length++
            } else {
                i = 0
                start++
                length = 0
            }
            if (length == targetStr.length) {
                print("$start")
                break
            }
        }
        if (length == targetStr.length) {
            print("$start")
        }
    }

    private fun sss(nums: Array<Int>) {
        val indexs = Array(nums.size) {
            return@Array 0
        }
        val position = 0
        ssss1(nums, indexs, position)
    }

    private fun ssss1(nums: Array<Int>, indexs: Array<Int>, position: Int) {
        if (position == indexs.size - 1) {
            indexs[position] = nums[0]
            indexs.printArray()
            return
        }
        val len = nums.size
        for (i in 0 until len) {
            indexs[position] = nums[i]
            val nextPos = position + 1
            ssss1(getNewArray(nums, nums[i]), indexs, nextPos)
        }

    }


    private fun getNewArray(nums: Array<Int>, exclude: Int): Array<Int> {
        val len = nums.size
        val newLen = len - 1
        val newArray = Array(newLen) {
            return@Array 0
        }
        var n = 0
        for (i in 0 until len) {
            if (nums[i] != exclude) {
                newArray[n] = nums[i]
                n++
            }
        }
        return newArray
    }


}

fun sss2(array: Array<ArrayOrInt>): List<Int> {
    val list = ArrayList<Int>()
    sss3(array, list)
    return list
}

fun sss3(array: Array<ArrayOrInt>, list: ArrayList<Int>) {
    array.forEach {
        if (it.isInt()) {
            list.add(it.num!!)
        } else {
            sss3(it.nums!!, list)
        }
    }
}

fun sss4(array: Array<ArrayOrInt>): List<Int> {
    val list = ArrayList<Int>()
    val stack = LinkedList<ArrayOrInt>()
    array.forEach {
        stack.push(it)
    }
    while (stack.peek() != null) {
        val curr = stack.poll()
        if (curr.isInt()) {
            list.add(curr.num!!)
        } else {
            curr.nums!!.forEach {
                stack.push(it)
            }
        }
    }

    return list
}

/**
 * 给定一个字符串，逐个翻转字符串中的每个单词
 */
fun stringWordReverse(string: String) {
    val stack1 = Stack<Char>()
    val stack2 = Stack<Char>()
    string.forEach {
        stack1.push(it)
    }
    val sb = StringBuilder()
    while (!stack1.empty()) {
        val next = stack1.pop()
        if (!next.isWhitespace()) {
            stack2.push(next)
        } else {
            while (!stack2.isEmpty()) {
                sb.append(stack2.pop())
            }
            sb.append(next)
        }

    }
    while (!stack2.isEmpty()) {
        sb.append(stack2.pop())
    }

    println(sb.toString())
}


