package com.gaofei.app

import android.content.Intent
import android.os.Bundle
import com.gaofei.app.databinding.ActTransparentBinding
import com.gaofei.library.base.BaseAct

class TransparentAct: BaseAct() {
    private var binding:ActTransparentBinding? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.act_transparent)
        binding!!.button.setOnClickListener {
            val intent = Intent(it.context, TransparentAct::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            startActivity(intent)
        }
    }
}