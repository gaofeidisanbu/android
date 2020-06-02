package com.example.lib.thread

import java.lang.Thread.sleep
import java.util.concurrent.Semaphore

object Semaphore : Runnable {

    val obj = Store(10)
    override fun run() {

//        Thread(Runnable {
//            aa()
//        }).start()
//
//        Thread(Runnable {
//            bb()
//        }).start()
//
//        Thread(Runnable {
//            cc()
//        }).start()
        ThirdThreadABC()
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

val a = Object()
val b = Object()
val c = Object()
fun ThirdThreadABC() {
    Thread(Runnable {
        while (true) {
            synchronized(a) {
                println("a")
                synchronized(b) {
                    b.notifyAll()
                }
                a.wait()
            }

        }

    }).start()
    Thread.sleep(100)
    Thread(Runnable {
        while (true) {
            synchronized(b) {
                b.wait()
                println("b")

            }
            synchronized(c) {
                c.notifyAll()
            }
        }

    }).start()
    Thread(Runnable {
        while (true) {
            synchronized(c) {
                c.wait()
                println("c")
                synchronized(a) {
                    a.notifyAll()
                }
            }
        }

    }).start()
}


class StoreA : Object() {
    val list = mutableListOf<Int>()
    val max = 10

    @Synchronized
    fun add() {
        synchronized(this) {

        }

    }

    @Synchronized
    fun remove() {
        synchronized(this) {

        }
    }

    @Synchronized
    fun getLen(): Int {
        return 0
    }

    class Product(val store: StoreA) {
        fun product() {
            while (true) {
                val i = 1
                synchronized(store) {
                    if (store.getLen() > 1) {
                        store.wait()
                    } else {
                        store.add()
                    }


                }
            }

        }
    }
}




