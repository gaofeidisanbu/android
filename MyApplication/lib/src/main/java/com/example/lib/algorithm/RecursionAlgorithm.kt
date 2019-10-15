package com.example.lib.algorithm

object RecursionAlgorithm : Runnable {

    override fun run() {
        count = 3
        hanoi(count, "source", "y", "target")
    }

    var count = 0

    /**
     * x -> z
     */
    private fun hanoi(n: Int, source: String, y: String, target: String) {
        if (n == 1) {
            move(n, source, target)
        } else {
            hanoi(n - 1, source, target, y)
            move(n, source, target)
            hanoi(n - 1, y, source, target)
        }

    }


    fun move(count: Int, source: String, target: String) {
        println("move ${count} ${source} to ${target}")
    }


    /**
     * 马踏棋盘
     */
    class Checkerboard {
        val fx = intArrayOf(1, 2, 2, 1, -1, -2, -2, -1)
        val fy = intArrayOf(2, 1, -1, -2, -2, -1, 1, 2)
        val x = 2
        val y = 2
        val array = Array(8) { 0 }
        val arrays = Array(8) {
            return@Array array
        }

        fun find(x: Int, y: Int, depth: Int): Boolean {
            for (i in 0 until fx.size) {
                val xx = fx[i]
                val yy = fy[i]
                if (check(xx, yy)) {
                    arrays[xx][yy] = depth
                    if (depth == 64) {
                        output()
                        return true
                    } else {
                        if (find(xx, yy, depth + 1)) {
                            return true
                        } else {
                            arrays[xx][yy] = 0
                        }
                    }

                }
            }
            return false

        }


        private fun output() {

        }

        fun check(xx: Int, yy: Int): Boolean {
            return false
        }
    }

    class NQueensII {

        fun output(arr: Array<Array<Int>>) {
            var i = 0
            val len = arr[0].size
            for (j in 0 until len) {
                if (find(arr, i, j, i++)) {
                    arr[i][j] = 1
                }
            }
        }


        fun find(arr: Array<Array<Int>>, x: Int, y: Int,  m: Int): Boolean {
            val curr = arr[m]
            for (i in 0 until curr.size) {

            }
            return false
        }

    }

}