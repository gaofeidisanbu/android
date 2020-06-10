package com.gaofei.app

import android.content.Intent
import android.os.Bundle
import com.gaofei.library.base.BaseAct
import kotlinx.android.synthetic.main.act_transparent.*

class TransparentAct: BaseAct() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.act_transparent)
        button.setOnClickListener {
            val intent = Intent(it.context, TransparentAct::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            startActivity(intent)
        }
    }
}