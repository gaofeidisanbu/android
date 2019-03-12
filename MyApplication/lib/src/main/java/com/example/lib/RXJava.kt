package com.example.lib

import io.reactivex.Maybe
import io.reactivex.MaybeObserver
import io.reactivex.Observable
import io.reactivex.Observer
import io.reactivex.disposables.Disposable
import org.omg.CORBA.Object

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
                    println(it)
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
        Observable.create<Any> {
            it.onNext("1")
            it.onComplete()
        }.subscribe()
        Observable.fromCallable<Any> {

        }.subscribe()
    }

}