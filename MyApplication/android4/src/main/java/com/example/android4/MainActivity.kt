package com.example.android4

import android.app.Activity
import android.content.Intent
import android.content.pm.ActivityInfo
import android.os.Binder
import android.os.Bundle
import android.os.Process
import android.util.Log
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : Activity() {
    companion object {
         var count = 1;
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_USER_PORTRAIT
        count++;
        Log.d("aaaaa", "${count} onCreate")
        button.setOnClickListener {
            val pid = Binder.getCallingPid()
            val uid = Binder.getCallingUid()
            val userHandle = Binder.getCallingUserHandle()
            val uid1 = Process.myUid()
            button.text = "${uid1} ${pid} ${uid}"

            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }
    }

    override fun onPause() {
        super.onPause()
        Log.d("aaaaa", "${count} onPause")
    }

    override fun onStop() {
        super.onStop()
        Log.d("aaaaa", "${count} onStop")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d("aaaaa", "${count} onDestroy")
    }
}
