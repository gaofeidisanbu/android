package com.gaofei.app.act

import android.os.Bundle
import android.os.Handler
import android.os.HandlerThread
import com.gaofei.app.R
import com.gaofei.library.base.BaseAct
import com.gaofei.library.utils.LogUtils
import kotlinx.android.synthetic.main.act_http_client.*
import okhttp3.*
import okhttp3.internal.Util
import java.io.IOException
import java.util.concurrent.SynchronousQueue
import java.util.concurrent.ThreadFactory
import java.util.concurrent.ThreadPoolExecutor
import java.util.concurrent.TimeUnit

class OKHttpClientAct : BaseAct() {
    val handlerThread = HandlerThread("network")
    var count = 0;
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.act_http_client)
        handlerThread.start()
        val handler = Handler(handlerThread.looper)
        val executorService = ThreadPoolExecutor(0, Int.MAX_VALUE, 600, TimeUnit.SECONDS,
                SynchronousQueue(), threadFactory("OkHttp Dispatcher", false))
        val client = OkHttpClient.Builder()
                .dispatcher(Dispatcher(executorService))
                .build()
        button.setOnClickListener {
            handler.post {
                for (i in 0..1) {
                    val request = Request.Builder()
                            .url("https://www.baidu.com")
                            .build()
                    val call = client.newCall(request)
                    call.enqueue(object :Callback {
                        override fun onFailure(call: Call?, e: IOException?) {
                            LogUtils.d("222")
                        }

                        override fun onResponse(call: Call?, response: Response?) {
                            LogUtils.d("111111")
                        }

                    })
                }
            }

        }
        button2.setOnClickListener {
            executorService.shutdown()
        }
    }


    private fun threadFactory(name: String, daemon: Boolean): ThreadFactory? {
        return ThreadFactory { runnable ->
            LogUtils.d("${name}_${++count}")
            val result = Thread(runnable, name)
            result.isDaemon = daemon
            result
        }
    }



}

