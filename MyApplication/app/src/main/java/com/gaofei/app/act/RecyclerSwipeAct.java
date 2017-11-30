package com.gaofei.app.act;

import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

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

public class RecyclerSwipeAct extends BaseAct implements OnRefreshListener, OnLoadMoreListener {
    private RecyclerView mRecyclerView;
    private RecyclerViewAdapter mAdapter;
    private SwipeToLoadLayout mSwipeToLoadLayout;
    private Handler mHandler = new Handler();

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_recycler);
        mSwipeToLoadLayout = (SwipeToLoadLayout) findViewById(R.id.swipe);
        mSwipeToLoadLayout.setOnRefreshListener(this);
        mSwipeToLoadLayout.setOnLoadMoreListener(this);
        mSwipeToLoadLayout.setRefreshEnabled(false);
        mRecyclerView = (RecyclerView) findViewById(R.id.swipe_target);
        mAdapter = new RecyclerViewAdapter(this);
        mAdapter.add(getData());
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.addItemDecoration(new RecyclerViewAdapter.MyItemDecoration(this));
        mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                if (isScrollVerticallyBottom(recyclerView, newState) && !mSwipeToLoadLayout.isLoadingMore()) {
                    mSwipeToLoadLayout.setLoadingMore(true);
                }
//                LogUtils.d("gf onScrollStateChanged newState = " + newState);
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
            }
        });
        mRecyclerView.setAdapter(mAdapter);
        findViewById(R.id.button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mAdapter.add("0000");
            }
        });
    }

    public static boolean isScrollVerticallyBottom(RecyclerView recyclerView, int newState) {
        try {
            LinearLayoutManager lm = (LinearLayoutManager) recyclerView.getLayoutManager();
            int totalItemCount = recyclerView.getAdapter().getItemCount();
            int lastVisibleItemPosition = lm.findLastVisibleItemPosition();
            int visibleItemCount = recyclerView.getChildCount();
            View lastVisibleView = lm.findViewByPosition(lastVisibleItemPosition);
            int bottom = lastVisibleView.getBottom() - recyclerView.getMeasuredHeight();
            TextView textView = (TextView) lastVisibleView.findViewById(R.id.text);
            LogUtils.d("gf bottom = " + bottom+" text = "+textView.getText()+" height = "+recyclerView.getMeasuredHeight());
            if (bottom >= 0 && newState == RecyclerView.SCROLL_STATE_IDLE
                    && lastVisibleItemPosition == totalItemCount - 1
                    && visibleItemCount > 0) {
                return true;
            }
        }catch (Exception e){
            LogUtils.w(e);

        }
        return false;
    }

    int count = 0;

    private List<String> getData() {
        List<String> list = new ArrayList<>();
        for (int i = count; i < 10 + count; i++) {
            list.add(" i = " + i + " " + i + " " + i + " " + i + " " + i + " " + i + " " + i);
        }
        count = list.size();
        return list;
    }

    @Override
    public void onRefresh() {
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                mSwipeToLoadLayout.setRefreshing(false);

            }
        }, 700);
    }

    @Override
    public void onLoadMore() {
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                mAdapter.add(getData());
                mSwipeToLoadLayout.setLoadingMore(false);
            }
        }, 1000);
    }

}
