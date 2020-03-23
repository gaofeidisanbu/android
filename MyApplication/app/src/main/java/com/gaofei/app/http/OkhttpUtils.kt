package com.gaofei.app.http

import android.util.Log
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response


object OkhttpUtils {
    fun getDatasync() {
        Thread(Runnable {
            try {
                val client = OkHttpClient()//创建OkHttpClient对象
                val request = Request.Builder()
                        .url("https://hls.media.yangcong345.com/mobileM/mobileM_586f4100065b7e9d714296cb.m3u8")//请求接口。如果需要传参拼接到接口后面。
                        .build()//创建Request 对象
                var response: Response? = null
                response = client.newCall(request).execute()//得到Response 对象
                if (response!!.isSuccessful()) {
                    Log.d("kwwl", "response.code()==" + response!!.code())
                    Log.d("kwwl", "response.message()==" + response!!.message())
                    Log.d("kwwl", "res==" + response!!.body()!!.string())
                    //此时的代码执行在子线程，修改UI的操作请使用handler跳转到UI线程。
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }).start()
    }
}