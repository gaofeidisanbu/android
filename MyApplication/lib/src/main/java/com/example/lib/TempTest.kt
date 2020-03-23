package com.example.lib

import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

object TempTest : Runnable {

    private final val  m = "aaa"

    override fun run() {
//        GlobalScope.launch {
//            print(m)
//        }

    }

    inline fun f(crossinline body: () -> Unit) {
        val f = object: Runnable {
            override fun run() = body()
        }
        // ……
    }

    inline fun f1( noinline body: () -> Unit) {
        val f = object: Runnable {
            override fun run() = body()
        }
        // ……
    }

}