package com.gaofei.app.act

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.lifecycle.LiveDataReactiveStreams
import com.gaofei.app.R
import com.gaofei.library.base.BaseAct
import io.reactivex.Flowable
import io.reactivex.Observable
import io.reactivex.functions.Function
import kotlinx.android.synthetic.main.act_rxjava.*
import java.util.*
import java.util.concurrent.TimeUnit
import kotlin.collections.ArrayList
import kotlin.reflect.jvm.internal.impl.load.kotlin.JvmType

class RXJavaAct : BaseAct() {
    var i = 0

    @SuppressLint("CheckResult")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.act_rxjava)
        button.setOnClickListener {
        }


    }

    public fun request() {
    }

    override fun onDestroy() {
        super.onDestroy()
    }

}