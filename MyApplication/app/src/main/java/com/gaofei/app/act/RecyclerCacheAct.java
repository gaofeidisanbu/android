package com.gaofei.app.act;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.gaofei.app.R;
import com.gaofei.app.act.adapter.RecyclerViewAdapter;
import com.gaofei.app.act.view.swipetoloadlayout.OnLoadMoreListener;
import com.gaofei.app.act.view.swipetoloadlayout.OnRefreshListener;
import com.gaofei.app.act.view.swipetoloadlayout.SwipeToLoadLayout;
import com.gaofei.library.base.BaseAct;
import com.gaofei.library.utils.LogUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by gaofei on 2017/6/13.
 */

public class RecyclerCacheAct extends BaseAct {
    private RecyclerView mRecyclerView;
    private RecyclerViewAdapter mAdapter;
    private Handler mHandler = new Handler();
    private boolean isCurrRequest = false;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_recycler_cache);
        mRecyclerView = (RecyclerView) findViewById(R.id.recycler);
        mAdapter = new RecyclerViewAdapter(this);
        mAdapter.add(getData());
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.addItemDecoration(new RecyclerViewAdapter.MyItemDecoration(this));
        mRecyclerView.setAdapter(mAdapter);
        findViewById(R.id.button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                mAdapter.add("0000");
                mAdapter.notifyDataSetChanged();
            }
        });
    }


    private List<String> getData() {
        List<String> list = new ArrayList<>();
        int count = mAdapter.getItemCount() + 1;
        for (int i = count; i < 15 + count; i++) {
            list.add(" i = " + i + " " + i + " " + i + " " + i + " " + i + " " + i + " " + i);
        }
        return list;
    }


}
