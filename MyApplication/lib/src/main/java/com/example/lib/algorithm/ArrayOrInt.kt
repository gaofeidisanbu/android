package com.example.lib.algorithm

import java.io.Serializable

class ArrayOrInt : Serializable {
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