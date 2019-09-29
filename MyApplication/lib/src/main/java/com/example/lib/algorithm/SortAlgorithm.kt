package com.example.lib.algorithm

import com.example.lib.common.print
import com.example.lib.common.printArray
import java.util.*
import kotlin.Comparator
import kotlin.collections.ArrayList
import kotlin.collections.HashMap

object SortAlgorithm : Runnable {

    override fun run() {
//        sss(arrayOf(1, 2, 3))
        val arra = arrayOf(ArrayOrInt(1), ArrayOrInt(arrayOf(ArrayOrInt(2), ArrayOrInt(3), ArrayOrInt(arrayOf(ArrayOrInt(4))))))
        sss4(arra).print()

    }

    /**
     * 描述
     * 计一个算法，找出只含素因子2，3，5 的第 n 小的数。
     * 符合条件的数如：1, 2, 3, 4, 5, 6, 8, 9, 10, 12...
     * 1 也是一个丑数
     */

    fun solution1(n: Int): Int {
        val i = 0
        val result = -1
        val special = 1
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
        var curr = stack.poll()
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

class ArrayOrInt {
    constructor(num: Int) {
        this.num = num
    }

    constructor(nums: Array<ArrayOrInt>) {
        this.nums = nums
    }

    var num: Int? = null
    var nums: Array<ArrayOrInt>? = null

    fun isInt(): Boolean {
        return num != null
    }


}

class LFU<T, R>(val count: Int) {
    private val map = HashMap<T, Node>()
    private val queue = PriorityQueue<Node>()

    fun set(key: T, value: R) {
        val existNode = map[key]
        if (existNode != null) {
            existNode.value = value
        } else {
            clear()
            map[key] = Node(key, value, 0)
        }

    }

    fun get(key: T): R? {
        val result = map[key]
        result?.let {
            queue.remove(it)
            result.accessCount++
            queue.add(result)
        }
        return result?.value
    }

    private fun clear() {
        if (queue.size == count) {
            val node = queue.poll()
            map.remove(node.key)
            print("remove, $node")
        }
    }


    inner class Node(val key: T, var value: R, var accessCount: Int) : Comparator<Node> {
        override fun compare(p0: Node, p1: Node): Int {
            return when {
                p0.accessCount == p1.accessCount -> 0
                p0.accessCount == p1.accessCount -> 1
                else -> -1

            }
        }


    }

    fun cc(array: Array<Array<Int>>, target: Int) {
        val n = array.size
        val m = array[0].size
        var mP = 0
        var nP = 0
        val start = 0
        val end = n - 1
        while (start < end) {
            val midValue = array[(start + end) / 2][m - 1]
            if (midValue < target) {

            }
        }

    }

    private fun binarySearch(array: Array<Int>): Int {
        return 0
    }
}
