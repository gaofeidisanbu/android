package com.example.android4

import android.app.Activity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : Activity() {
    private var clickCounting = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        button.setOnClickListener {
            button.text = "${clickCounting++}"
            Thread.sleep(10000)
        }
    }
}
