package com.example.lib.algorithm

object CommonAlgorithm : Runnable {
    override fun run() {

    }


    /**
     *  装箱算法
     * 书籍：算法与基础与在线实践
     * 算法思路：先装最大的
     *
     */
    fun common1(a: Int, b: Int, c: Int, d: Int, e: Int, f: Int) {
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
}