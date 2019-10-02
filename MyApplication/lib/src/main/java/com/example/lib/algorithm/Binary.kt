package com.example.lib.algorithm

object Binary: Runnable {

    override fun run() {

    }


    fun ss(array: Array<Int>) {
        val list = arrayListOf<Array<Int>>()
        val len = array.size
        var i = 0
        var j = i + 1
        var k = j + 1
        while (i < len ) {
            i++
            while (j < len) {
                j++
                while (k < len) {
                    if (array[i] + array[j] + array[k] == 0) {

                        list.add(arrayOf())
                    }
                    k ++
                }
            }
        }
    }



}