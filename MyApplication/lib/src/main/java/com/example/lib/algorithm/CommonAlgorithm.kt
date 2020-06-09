package com.example.lib.algorithm

import com.example.lib.common.printArray


class Obj1 {

}

object CommonAlgorithm : Runnable {


    override fun run() {
//        common2(3, 2)
//        common3(4)
//        stringWordReverse("Hello        world")
//        common7()
        test()
    }

    fun test() {
        val a = arrayOf<Int>(10, -2, 5, 8, -4, 2, -3, 7, 12, -88, -23, 35)
        val n = a.size
        var h = 1
        while (h <= n / 3) h = 3 * h
        println("3h size = $n h = $h")


        while (h >= 1) {
            for (i in 0 until n) {
                var j = i
                while (j >= h && a[j] < a[j - h]) {
                    val tmp = a[j]
                    a[j] = a[j - h]
                    a[j - h] = tmp
                    j -= h
                }
            }
            h /= 3;//减小间距
        }
        a.printArray()
        common8()
    }

    /**
     *  装箱算法
     * 书籍：算法与基础与在线实践
     * 算法思路：先装最大的
     *
     */
    private fun common1(a: Int, b: Int, c: Int, d: Int, e: Int, f: Int) {
        //箱子个数
        var n = 0
        // 剩余为2*2的空间个数
        var x = 0
        // 剩余为1*1的空间个数
        var y = 0

        val ey = 11
        val dx = 5
        val bx = 9
        val ay = 36
        n = f + e + d
        if (e > 0) {
            y = e * ey
        }
        if (d > 0) {
            x = d * dx
        }
        //计算c（ 3 * 3）
        n += (c + 3) / 4
        val c2: IntArray = intArrayOf(0, 5, 3, 1)
        x += c2[c % 4]
        if (b > x) {
            n += ((b - x) + 8) / bx
            y += ((9 - b) / 9) * 4
        }
        if (a > y) {
            n += (ay + 35) / ay
        }

    }

    /**
     * 约瑟夫问题
     * 书籍：算法与基础与在线实践
     */
    private fun common2(n: Int, m: Int) {
        val list = mutableListOf<Int>()
        for (i in 1..n) {
            list.add(i)
        }
        // 报数
        var x = 1
        var currPosition = 0
        while (list.size <= 1) {
            val size = list.size
            if (x == m) {
                list.removeAt(currPosition)
                x = 1
            } else {
                if (currPosition == size - 1) {
                    currPosition = 0
                } else {
                    currPosition++
                }
                x++
            }

        }

        println(list[0])
    }

    /**
     * 打印n的排列组合
     */
    private fun common3(n: Int) {
        val array = IntArray(n)
        for (i in 0 until n) {
            array[i] = i + 1
        }
        val antherArray = IntArray(n)
        val sb = StringBuilder()
        common3_1(array, antherArray, 0)
        println(sb.toString())
    }

    /**
     * 1.思路：每遍历一次，减少一个数；然后递归进入下一步
     * 2.思路：记录已经遍历的数量为N，数据为printArray,每次遍历和array的数据进行比较，如果相同，continue；否则记录数据，继续递归遍历
     */
    private fun common3_1(array: IntArray, printArray: IntArray, N: Int) {
        val size = array.size
        if (N == size) {
            val sb = StringBuilder()
            printArray.forEachIndexed { index, i ->
                if (index != 0) {
                    sb.append(",")
                }
                sb.append("$i")
            }
            println(sb.toString())
        } else {
            for (i in 0 until size) {
                var isRepeat = false
                for (j in 0..N) {
                    if (printArray[j] == array[i]) {
                        isRepeat = true
                        break
                    }
                }
                if (isRepeat) {
                    continue
                }
                printArray[N] = array[i]
                common3_1(array, printArray, N + 1)
            }
        }
    }

    /**
     * 打印给出排列组合一个数的，下k个数的排列组合
     * @param n
     */
    private fun common3_2(n: Int, array: IntArray, k: Int) {
        val max = n;
    }

    /**
     * 获取当前的下一个
     * 1234 1243 1324
     */
    private fun common3_2_1(n: Int, array: IntArray) {
        val max = n;

    }

    /**
     * 真假币12
     * 找到假币，并且确定假币是比真的轻，还是重
     */
    private fun common4(n: Int, array: IntArray) {
        val max = n;
        // 第一步六六，记录 g6_1 g6_2
        // 第二步g6_1三三，记录g3_1 g3_2
        // 第三步 二，记录g2

    }

    /**
     *有一个整形数组，包含正数和负数，然后要求把数组内的所有负数移至正数的左边，且保证相对位置不变，要求时间复杂度为O(n),
     * 空间复杂度为O(1)。例如，{10, -2, 5, 8, -4, 2, -3, 7, 12, -88, -23, 35}变化后是{-2, -4，-3, -88, -23,5, 8 ,10, 2, 7, 12, 35}。
     */
    private fun common5() {
        val array = arrayOf<Int>(10, -2, 5, 8, -4, 2, -3, 7, 12, -88, -23, 35)
        val len = array.size
        var index = -1
        for (i in 0 until len) {
            if (array[i] > 0) {
                if (index < 0) {
                    index = i
                }
            } else {
                if (index >= 0) {
                    val temp = array[i]
                    for (j in i - 1 downTo index) {
                        array[j + 1] = array[j]
                    }
                    array[index] = temp
                    index++
                }
            }
        }
        array.printArray()
    }

    /**
     *有一个整形数组，包含正数和负数，然后要求把数组内的所有负数移至正数的左边，且保证相对位置不变，要求时间复杂度为O(n),
     * 空间复杂度为O(1)。例如，{10, -2, 5, 8, -4, 2, -3, 7, 12, -88, -23, 35}变化后是{-2, -4，-3, -88, -23,5, 8 ,10, 2, 7, 12, 35}。
     */
    private fun common6() {
        val array = arrayOf<Int>(10, -2, 5, 8, -4, 2, -3, 7, 12, -88, -23, 35)
        val len = array.size
        var index = -1
        var temp: Int
        for (i in 0 until len) {
            if (array[i] > 0) {
                if (index < 0) {
                    index = i
                }
            } else {
                if (index >= 0) {
                    temp = array[i]
                    array[i] = array[index]
                    array[index] = temp
                    index++
                }
            }
        }
        array.printArray()
    }

    private fun common7() {
        val array = arrayOf<Int>(10, -2, 5, 8, -4, 2, -3, 7, 12, -88, -23, 35)
        setParted1(array, 0, array.size - 1)
    }

    fun setParted1(a: Array<Int>, left: Int, right: Int) {
        var left = left
        var right = right
        if (left >= right || left == a.size || right == 0) {
            for (i in a.indices) {
                println(a[i])
            }
            return
        }
        while (a[left] < 0) {
            left++
        }
        while (a[right] >= 0) {
            right--
        }
        if (left >= right || left == a.size || right == 0) {
            for (i in a.indices) {
                println(a[i])
            }
            return
        }
        swap(a, left, right)
        left++
        right--
        setParted1(a, left, right)
    }

    private fun swap(a: Array<Int>, left: Int, right: Int) {
        var temp = 0
        temp = a[left]
        a[left] = a[right]
        a[right] = temp
    }


    private fun common8() {
        val array = arrayOf<Int>(10, -2, 5, 8, -4, 2, -3, 7, 12, -88, -23, 35)
        val len = array.size
        var i = len - 1
        var j = len - 1
        while (i >= 0) {
            if (array[i] > 0) {
                val temp = array[i]
                array[i] = array[j]
                array[j] = temp
                i--
                j--
            } else {
                i--
            }
        }
        array.printArray()

    }


}