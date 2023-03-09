package com.example.lib

import com.example.lib.file.FileUtils
import io.reactivex.*
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import sun.misc.Unsafe
import java.util.concurrent.TimeUnit

object RXJava : Runnable {
    override fun run() {
        println("start --------------->")
        just()
        observable()
        println("end --------------->")
    }


    private fun just() {
        Observable
                .just(1, 2)
                .subscribe {
//                    println(it)
                }
        Observable
                .just(1, 2)
                .subscribe(object : Observer<Int> {
                    override fun onComplete() {
                    }

                    override fun onSubscribe(d: Disposable?) {
                    }

                    override fun onNext(t: Int?) {
                    }

                    override fun onError(e: Throwable?) {
                    }

                })
        Maybe.just(1)
                .map {
                    20 + 1
                }
                .test()
                .assertResult(21)
    }

    private fun observable() {
//        Observable.create<Any> {
//            it.onNext("1")
//            it.onComplete()
//        }.subscribe()
//        Observable.fromCallable<Any> {
//
//        }.subscribe()
        Observable.interval(10, TimeUnit.SECONDS, Schedulers.trampoline())
                .subscribe {
                    FileUtils.write(it.toString())
                    println("interval $it")

                }

//        Observable.interval(10, TimeUnit.SECONDS, Schedulers.trampoline())
//                .subscribe(COn)

    }


    private fun method(i: ITest) {

    }



}



interface IInterface {
    fun method()
}