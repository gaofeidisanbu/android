package com.gaofei.app.plugin

import android.app.Instrumentation
import java.lang.reflect.InvocationHandler
import java.lang.reflect.Method
import java.lang.reflect.Proxy

object ProxyActivityStarter: Runnable {
    private const val activityThreadClassName = "android.app.ActivityThread"
    private const val activityThreadField_instrumentation = "mInstrumentation"
    private  val instrumentationClass = Instrumentation::class.java
    override fun run() {
        val activityThreadClass = Class.forName(activityThreadClassName)
        val instrumentation = activityThreadClass.getField(activityThreadField_instrumentation).get(null) as Instrumentation
        val instrumentationProxy = Proxy.newProxyInstance(ProxyActivityStarter::class.java.classLoader, arrayOf(instrumentationClass)) { proxy, method, args ->
            method.invoke(instrumentation, args)
        }
    }

}