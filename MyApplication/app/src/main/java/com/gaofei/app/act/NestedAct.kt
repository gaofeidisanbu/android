package com.gaofei.app.act

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import com.gaofei.app.R
import com.gaofei.app.databinding.ActAidlBinding
import com.gaofei.app.databinding.ActNestedBinding
import com.gaofei.library.base.BaseAct
private lateinit var binding: ActNestedBinding



class NestedAct : BaseAct() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActNestedBinding.inflate(layoutInflater)
        setContentView(R.layout.act_nested)
    }

}