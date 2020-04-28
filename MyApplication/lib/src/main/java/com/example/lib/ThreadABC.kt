package com.example.lib

import java.util.*

object ThreadABC : Runnable {
    val obj1 = Object()
    val obj2 = Object()
    val obj3 = Object()
    val threadA = Thread {
        while (true) {
            synchronized(obj1) {
                obj1.wait()
            }
            synchronized(obj2) {
                println("A")
                obj2.notify()
            }
        }

    }
    val threadB = Thread {
        while (true) {
            synchronized(obj2) {
                obj2.wait()
            }
            synchronized(obj3) {
                println("B")
                obj3.notify()
            }
        }

    }
    val threadC = Thread {
        while (true) {
            synchronized(obj3) {
                obj3.wait()

            }

            synchronized(obj1) {
                println("C")
                obj1.notify()
            }
        }

    }

    override fun run() {
        threadA.start()
        threadB.start()
        threadC.start()
        synchronized(obj1) {
            obj1.notify()
        }
    }

}