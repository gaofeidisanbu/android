package com.example.lib

import java.net.ServerSocket
import java.net.Socket


object ThreadTest : Runnable {

    private var i = 0
    val server = ServerSocket(8080)

    override fun run() {
        var i = 0

        val th = Thread {
            println("1111")
            val server = ServerSocket(8080)
            //等待请求
            //等待请求
            val socket: Socket = server.accept()
            println("222")
//            while (true) {
//                println("i = $i")
//                i++
//                try {
//                    Thread.sleep(1000)
//                }catch (e: InterruptedException) {
//                    e.printStackTrace()
//                }
//            }

        }
        th.start()
        Thread.sleep(100)
        th.interrupt()
//        th.suspend()
        println("main thread")
    }

}


class ThreadUtils {
    companion object {
    }

    fun run() {
    }
}