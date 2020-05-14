package com.gaofei.app.act

import android.os.Bundle
import com.gaofei.app.R
import com.gaofei.library.base.BaseAct
import com.gaofei.library.utils.LogUtils
import io.reactivex.Observable
import io.reactivex.Scheduler
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.act_rxjava.*
import java.util.concurrent.TimeUnit

class RXJavaAct : BaseAct() {
    var i = 0
    private var disposable: Disposable? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.act_rxjava)
        button.setOnClickListener {
            disposable = Observable.fromCallable {
                Thread.sleep(100000)
               return@fromCallable arrayListOf<Any>()
            }.subscribeOn(Schedulers.io())
                    .subscribe {
                        LogUtils.d(i)
                    }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        disposable?.dispose()
    }

}