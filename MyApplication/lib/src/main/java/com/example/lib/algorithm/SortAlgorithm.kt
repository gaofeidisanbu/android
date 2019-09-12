package com.example.lib.algorithm

object SortAlgorithm : Runnable {

    override fun run() {}
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

        return array[n -1]!!
    }

}
