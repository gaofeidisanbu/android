package com.example.gaofei.myapplication.act;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.example.gaofei.myapplication.BaseAct;
import com.example.gaofei.myapplication.R;
import com.example.gaofei.myapplication.act.adapter.RecyclerViewAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by gaofei on 2017/6/13.
 */

public class RecyclerAct extends BaseAct{
    private RecyclerView mRecyclerView;
    private RecyclerViewAdapter mAdapter;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_recycler);
        mRecyclerView = (RecyclerView)findViewById(R.id.swipe_target);
        mAdapter = new RecyclerViewAdapter(this);
        mAdapter.add(getData());
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.addItemDecoration(new RecyclerViewAdapter.MyItemDecoration(this));
        mRecyclerView.setAdapter(mAdapter);
        findViewById(R.id.button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mAdapter.add("0000");
            }
        });
    }

    private List<String> getData(){
        List<String> list  = new ArrayList<>();
        for (int i = 0;i < 50;i++){
            list.add(" i = "+i+" "+i+" "+i+" "+i+" "+i+" "+i+" "+i);
        }
        return list;
    }
}
