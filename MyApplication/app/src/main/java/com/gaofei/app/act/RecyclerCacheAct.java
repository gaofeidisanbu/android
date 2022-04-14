package com.gaofei.app.act;

import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.util.SparseArray;
import android.view.View;
import android.view.WindowManager;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.gaofei.app.R;
import com.gaofei.app.act.adapter.RecyclerViewAdapter;
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
        SparseArray sparseArray = new SparseArray();
        setContentView(R.layout.act_recycler_cache);
        getWindow().clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        getWindow().setStatusBarColor(Color.TRANSPARENT);
        SwipeRefreshLayout swipeRefreshLayout = findViewById(R.id.swipe);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mHandler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        swipeRefreshLayout.setRefreshing(false);
                    }
                }, 5 *1000);
            }
        });
        mRecyclerView = (RecyclerView) findViewById(R.id.recycler);
        mAdapter = new RecyclerViewAdapter(this);
        mAdapter.add(getData());
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
//        mRecyclerView.getRecycledViewPool().setMaxRecycledViews(0, 100);
        mRecyclerView.addItemDecoration(new RecyclerViewAdapter.MyItemDecoration(this));
        mRecyclerView.setAdapter(mAdapter);
        findViewById(R.id.button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                mAdapter.add("0000");
                LogUtils.d("RecyclerViewAdapter", "  notifyDataSetChanged");
//                findViewById(R.id.aaa).invalidate();
                mAdapter.notifyDataSetChanged();

            }
        });
        findViewById(R.id.aaa).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LogUtils.d("RecyclerViewAdapter", "  aaa ");
            }
        });
    }


    private List<String> getData() {
        List<String> list = new ArrayList<>();
        int count = mAdapter.getItemCount() + 1;
        for (int i = count; i < 40 + count; i++) {
            list.add(" i = " + i + " " + i + " " + i + " " + i + " " + i + " " + i + " " + i);
        }
        return list;
    }


}
