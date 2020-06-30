package com.gaofei.app.act;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
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
    private boolean isCurrRequest = false;

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
                if (!isCurrRequest && !mSwipeToLoadLayout.isLoadingMore() && isAutoLoadMore(recyclerView, newState)) {
//                    mSwipeToLoadLayout.setLoadingMore(true);
                    request(false);
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
            LogUtils.d("gf bottom = " + bottom + " text = " + textView.getText() + " height = " + recyclerView.getMeasuredHeight());
            if (bottom >= 0
                    && lastVisibleItemPosition == totalItemCount - 1
                    && visibleItemCount > 0) {
                return true;
            }
        } catch (Exception e) {
            LogUtils.w(e);

        }
        return false;
    }


    private static boolean isAutoLoadMore(RecyclerView recyclerView, int newState){
        try {
            LinearLayoutManager lm = (LinearLayoutManager) recyclerView.getLayoutManager();
            int totalItemCount = recyclerView.getAdapter().getItemCount();
            int lastVisibleItemPosition = lm.findLastVisibleItemPosition();
            int visibleItemCount = recyclerView.getChildCount();
            View lastVisibleView = lm.findViewByPosition(lastVisibleItemPosition);
            int bottom = lastVisibleView.getBottom() - recyclerView.getMeasuredHeight();
            TextView textView = (TextView) lastVisibleView.findViewById(R.id.text);
            LogUtils.d("gf bottom = " + bottom + " text = " + textView.getText() + " height = " + recyclerView.getMeasuredHeight());
            LogUtils.d("gf lastVisibleItemPosition = " + lastVisibleItemPosition + " totalItemCount = " + totalItemCount );
            if (bottom >= 0
                    && lastVisibleItemPosition > totalItemCount - 5
                    && visibleItemCount > 0) {
                return true;
            }
        } catch (Exception e) {
            LogUtils.w(e);

        }
        return false;
    }




    private List<String> getData() {
        List<String> list = new ArrayList<>();
        int count = mAdapter.getItemCount() + 1;
        for (int i = count; i < 10 + count; i++) {
            list.add(" i = " + i + " " + i + " " + i + " " + i + " " + i + " " + i + " " + i);
        }
        return list;
    }

    private void request(final boolean isPullUp) {
        LogUtils.d("isPullUp = "+isPullUp+" isCurrRequest = "+isCurrRequest);
        if (isCurrRequest) {
            if (isPullUp) {
                mSwipeToLoadLayout.setLoadingMore(false);
            }
            return;
        }
        isCurrRequest = true;
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                mAdapter.add(getData());
                if (isPullUp) {
                    mSwipeToLoadLayout.setLoadingMore(false);
                }
                isCurrRequest = false;
            }
        }, 400);

    }

    @Override
    public void onRefresh() {
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                mSwipeToLoadLayout.setRefreshing(false);

            }
        }, 300);
        Message msg  = Message.obtain();
        msg.arg1 = 1;
        mHandler.sendMessage(msg);

    }

    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
        }
    };



    @Override
    public void onLoadMore() {
        request(true);
    }

}
