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
//        stringWordReverse("Hello        world")
//        quickSort(arrayOf(5, 3, 3, 2, 1))
//        sort1(arrayOf(1, 2, 3, 4, 5))
//        chooseSort(arrayOf(5, 3, 3, 2, 1))
//        insertSort2()
//        mergeArray()
        cellSort()

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

/**
 * 快速排序
 */
fun quickSort(arr: Array<Int>) {
    quickSort1(arr, 0, arr.size - 1)
    arr.printArray()
}

private fun quickSort1(arr: Array<Int>, startLow: Int, startHigh: Int) {
    var low = startLow
    var high = startHigh
    val target = arr[low]
    val beforeLow = startLow
    val beforeHigh = startHigh
    var isLow = true
    while (true) {
        if (low < high) {
            if (isLow) {
                if (arr[high] < target) {
                    arr[low] = arr[high]
                    isLow = false
                } else {
                    high--
                }
            } else {
                if (arr[low] > target) {
                    arr[high] = arr[low]
                    isLow = true
                } else {
                    low++
                }
            }
        } else {
            break
        }
    }
    arr[low] = target
    val leftLow = beforeLow
    val leftHigh = low - 1
    if (leftLow < leftHigh) {
        quickSort1(arr, leftLow, leftHigh)
    }
    val rightLow = beforeLow + 1
    val rightHigh = beforeHigh
    if (rightLow < rightHigh) {
        quickSort1(arr, rightLow, rightHigh)
    }

}

/**
 * 冒泡
 */
fun sort1(arr: Array<Int>) {
    val size = arr.size
    var isExchange = true
    for (i in 0 until size - 1) {
        for (j in 0 until size - i - 1) {
            if (arr[j] > arr[j + 1]) {
                val temp = arr[j]
                arr[j] = arr[j + 1]
                arr[j + 1] = temp
                isExchange = true
            }
        }
        if (!isExchange) {
            break
        }
    }
    arr.printArray()
}

/**
 * 选择排序
 */
fun chooseSort(arr: Array<Int>) {
    val size = arr.size
    for (i in 0 until size) {
        for (j in i + 1 until size) {
            if (arr[j] < arr[i]) {
                val temp = arr[j]
                arr[j] = arr[i]
                arr[i] = temp
            }
        }
    }
    arr.printArray()
}

/**
 * 插入排序,相邻元素直接交换
 */
fun insertSort(arr: Array<Int>) {
    val size = arr.size
    for (i in 1 until size) {
        for (j in i - 1 downTo 0) {
            if (arr[j + 1] < arr[j]) {
                val temp = arr[j + 1]
                arr[j + 1] = arr[j]
                arr[j] = temp
            } else {
                break
            }
        }
    }
    arr.printArray()
}


/**
 * 插入排序,先确定位置，再移动数组
 */
fun insertSort2() {
    val arr = arrayOf(5, 3, 3, 2, 1, 9, 7, 8, 5)
    val size = arr.size
    for (i in 1 until size) {
        var target = arr[i]
        var m = i
        for (j in i - 1 downTo 0) {
            if (target < arr[j]) {
                arr[j + 1] = arr[j]
                m--
            } else {
                break
            }
        }
        arr[m] = target
    }
    arr.printArray()
}

/**
 * 希尔排序
 */
fun cellSort() {
    val arr = arrayOf(5, 3, 3, 2, 1, 9, 7, 8, 5)
    val size = arr.size
    val low = 0
    val high = size - 1
    cellSortInner(arr, low, high)
    arr.printArray()
}

private fun cellSortInner(arr: Array<Int>, low: Int, high: Int) {
    val size = high - low + 1
    val mid = low + size / 2
    if (size > 1) {
        cellSortInner(arr, low, mid - 1)
        cellSortInner(arr, mid, high)
        cellSortInnerMerge(arr, low, mid, high)
    }
}

private fun cellSortInnerMerge(arr: Array<Int>, low: Int, mid: Int, high: Int) {
    val leftLen = mid  - low
    val rightLen = high - mid + 1
    val tempLen = leftLen + rightLen
    val tempArr = Array<Int>(tempLen) {
        return@Array 0
    }
    var leftIndex = low
    var rightIndex = mid
    for (i in 0 until tempLen) {
        if (leftIndex < mid && rightIndex <= high) {
            if (arr[leftIndex] <= arr[rightIndex]) {
                tempArr[i] = arr[leftIndex]
                leftIndex++
            } else {
                tempArr[i] = arr[rightIndex]
                rightIndex++
            }
        } else if (leftIndex < mid) {
            tempArr[i] = arr[leftIndex]
            leftIndex++
        } else if (rightIndex <= high) {
            tempArr[i] = arr[rightIndex]
            rightIndex++
        }

    }
    var tempIndex = 0
    for (i in low..high) {
        arr[i] = tempArr[tempIndex++]
    }
    arr.printArray()


}


private fun mergeArray() {
    val arr1 = intArrayOf(1, 2, 3)
    val arr2 = intArrayOf(4, 5, 6, 8)
    val len1 = arr1.size
    val len2 = arr2.size
    val size = len1 + len2
    val target = Array<Int>(size) {
        return@Array 0
    }
    var m = 0
    var n = 0
    for (i in 0 until size) {
        if (m < len1 && n < len2) {
            if (arr1[m] <= arr2[n]) {
                target[i] = arr1[m]
                m++
            } else if (arr1[m] > arr2[n]) {
                target[i] = arr2[n]
                n++
            }
        } else if (m < len1) {
            target[i] = arr1[m]
            m++
        } else {
            target[i] = arr2[n]
            n++
        }
    }
    target.printArray()
}



