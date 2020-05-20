package com.example.lib.algorithm

import java.lang.StringBuilder

object CommonAlgorithm : Runnable {


    override fun run() {
//        common2(3, 2)
//        common3(4)
        stringWordReverse("Hello        world")
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



}