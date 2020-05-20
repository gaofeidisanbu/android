package com.example.android4

import android.app.Activity
import android.content.Intent
import android.os.Binder
import android.os.Bundle
import android.os.Process
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : Activity() {
    private var clickCounting = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        button.setOnClickListener {
            val pid = Binder.getCallingPid()
            val uid = Binder.getCallingUid()
            val userHandle = Binder.getCallingUserHandle()
            val uid1 = Process.myUid()
            button.text = "${uid1} ${pid} ${uid}"

            val intent = Intent()
            intent.setClassName("com.gaofei.app", "com.gaofei.app.act.AIDLActivity")
            startActivity(intent)
        }
    }
}
