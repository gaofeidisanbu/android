package com.example.lib.thread

import java.lang.Thread.sleep
import java.util.concurrent.Semaphore

object Semaphore : Runnable {

    val obj = Store(10)
    override fun run() {

        Thread(Runnable {
            aa()
        }).start()

        Thread(Runnable {
            bb()
        }).start()

        Thread(Runnable {
            cc()
        }).start()
    }

    fun aa() {
        println("aaaaaaaa  start")
        synchronized(obj) {
            println("aaaaaaaa  111")
            obj.wait()
            println("aaaaaaaa  222")
        }
    }

    fun bb() {
        println("bb  start 11")
        sleep(200)
        println("bb  start 22")
        synchronized(obj) {
            println("bbbbb")
        }
    }

    fun cc() {
        println("cc  start 11")
        sleep(100)
        println("cc  start 22")
        synchronized(obj) {
            println("ccc  111")
            sleep(300)
            println("ccc  222")
            obj.notifyAll()
        }
    }


//    class Store(val initValue: Int) : Object() {
//
//        private var index = 0
//        private val list = mutableListOf<Int>()
//
//        fun add() {
//            list.add(index++)
//            System.out.println("produce $index")
//        }
//
//        fun remove(): Int {
//            val value = list.removeAt(0)
//            println("remove $value")
//            return value
//        }
//
//        fun getSize(): Int {
//            return list.size
//        }
//
//
//    }

//    class Producer(private val store: Store, val sem: Semaphore) {
//
//        companion object {
//            const val MAX = 5
//        }
//
//        fun product() {
//            sem.acquire()
//            while (true) {
//                val size = store.getSize()
//                if (size == MAX) {
//                    store.wait()
//                }
//                store.add()
//            }
//            sem.acquire()
//
//        }
//
//    }
//
//    class Consumer(private val store: Store) {
//        fun consume(): Int {
//            synchronized(store) {
//                val size = store.getSize()
//                if (size == 0) {
//                    store.notifyAll()
//                }
//                return store.remove()
//            }
//        }
//    }


}

class Store(val initValue: Int) : Object() {

    private var index = 0
    private val list = mutableListOf<Int>()

    fun add() {
        list.add(index++)
        println("produce $index")
    }

    fun remove(): Int {
        val value = list.removeAt(0)
        println("remove $value")
        return value
    }

    fun getSize(): Int {
        return list.size
    }


}

class Producer(private val store: Store, val proSem: Semaphore, val conSem: Semaphore, val mutex: Semaphore) {

    companion object {
        const val MAX = 5
    }


    fun product() {
        while (true) {
            proSem.acquire()
            mutex.acquire()
            store.add()
            mutex.release()
            conSem.release()
        }
    }

}

class Consumer(private val store: Store, val proSem: Semaphore, val conSem: Semaphore, val mutex: Semaphore) {
    fun consume() {
        conSem.acquire()
        mutex.acquire()
        store.remove()
        mutex.release()
        proSem.release()
    }
}


