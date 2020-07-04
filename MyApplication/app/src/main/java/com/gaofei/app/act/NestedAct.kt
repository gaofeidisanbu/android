package com.gaofei.app.act

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import com.gaofei.app.R
import com.gaofei.library.base.BaseAct
import kotlinx.android.synthetic.main.act_nested.*
import kotlinx.android.synthetic.main.list_item_nested.view.*

class NestedAct : BaseAct() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.act_nested)
        list.adapter = MyAdapter()
    }

    inner class MyAdapter : BaseAdapter {
        private val list = ArrayList<Int>()
        constructor() {
            for (i in 0 until  10) {
                list.add(i)
            }
        }
        override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
            val view = LayoutInflater.from(this@NestedAct).inflate(R.layout.list_item_nested, null)
            view.text.text = list[position].toString()
            return view
        }

        override fun getItem(position: Int): Any {
            return list[position]

        }

        override fun getItemId(position: Int): Long {
            return list[position].toLong()
        }

        override fun getCount(): Int {
            return list.size
        }

    }
}