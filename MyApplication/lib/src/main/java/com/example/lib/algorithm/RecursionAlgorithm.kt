package com.example.lib.algorithm

object RecursionAlgorithm: Runnable {

    override fun run() {
        count = 3
        hanoi(count, "source", "y", "target")
    }
    var  count = 0

    /**
     * x -> z
     */
    private fun hanoi(n: Int, source: String, y: String, target: String) {
        if (n ==  1) {
            move(n, source, target)
        } else {
            hanoi(n - 1,  source, target, y)
            move( n, source, target)
            hanoi(n - 1, y, source, target)
        }

    }


    fun move(count: Int, source: String, target: String) {
        println("move ${count} ${source} to ${target}")
    }

}