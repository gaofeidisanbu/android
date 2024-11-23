package com.gaofei.app.serivce

import android.app.IntentService
import android.app.Notification
import android.app.PendingIntent
import android.app.Service
import android.content.Intent
import android.content.res.Resources
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Binder
import android.os.IBinder
import android.os.Process
import androidx.core.app.NotificationCompat
import com.gaofei.app.MainActivity
import com.gaofei.app.R
import com.gaofei.library.utils.LogUtils

class EasyService : Service() {

    override fun onCreate() {
        super.onCreate()
        LogUtils.d("$TAG ${hashCode()} ${" onCreate processId = " + Process.myPid() + " threadId = " + Thread.currentThread().id}")
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        LogUtils.d("$TAG onStartCommand startId $startId")
//        startForeground(1, getNotification())
//        Thread.sleep(21*1000)
//        stopSelf(startId)
        return super.onStartCommand(intent, flags, startId)
    }


    private fun getNotification(): Notification {
        val builder = NotificationCompat.Builder(applicationContext)

        val nfIntent = Intent(this, MainActivity::class.java)
        builder.setContentIntent(PendingIntent.getActivity(this, 0, nfIntent,
            PendingIntent.FLAG_IMMUTABLE))
            .setLargeIcon(BitmapFactory.decodeResource(resources, R.mipmap.ic_launcher))
            .setContentTitle("下拉列表中的Title")
            .setSmallIcon(R.mipmap.ic_launcher)
            .setContentText("要显示的内容")
            .setWhen(System.currentTimeMillis())

        val notification = builder.build()
        notification.defaults = Notification.DEFAULT_SOUND
        return notification
    }


    override fun onBind(intent: Intent?): IBinder {
        LogUtils.d("$TAG ${" processId = " + Process.myPid() + " threadId = " + Thread.currentThread().id}")
       return Binder();
    }

    override fun onUnbind(intent: Intent?): Boolean {
        return super.onUnbind(intent)
    }

//    override fun onHandleIntent(intent: Intent?) {
//        LogUtils.d("$TAG onHandleIntent")
//
//    }

    override fun onDestroy() {
        super.onDestroy()
        LogUtils.d("$TAG onDestroy")
//        stopSelf();
    }

    companion object {
        private val TAG  = "EasyService"
        val ACTION = "com.gaofei.app2.EasyService"
    }

}