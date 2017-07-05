package com.example.gaofei.myapplication;

import android.content.Context;
import android.content.Intent;
import android.graphics.Paint;
import android.graphics.Rect;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.example.gaofei.myapplication.act.AnnoAct;
import com.example.gaofei.myapplication.act.ExceptionAct;
import com.example.gaofei.myapplication.act.KeyboardAct;
import com.example.gaofei.myapplication.act.RecyclerSwipeAct;
import com.example.gaofei.myapplication.act.TestAct;
import com.example.gaofei.myapplication.webview.WebviewActivity;
import com.example.gaofei.myapplication.act.adapter.BaseRecyclerAdapter;
import com.example.gaofei.myapplication.act.holder.MainViewHolder;
import com.example.gaofei.myapplication.act.holder.ViewHolderHandler;
import com.example.gaofei.myapplication.act.view.swipetoloadlayout.OnLoadMoreListener;
import com.example.gaofei.myapplication.act.view.swipetoloadlayout.OnRefreshListener;
import com.example.gaofei.myapplication.act.view.swipetoloadlayout.SwipeToLoadLayout;
import com.example.gaofei.myapplication.utils.CommonUtils;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends BaseAct implements BaseRecyclerAdapter.OnBaseAdapterListener, OnRefreshListener, OnLoadMoreListener {
    private RecyclerView mRecyclerView;
    private SwipeToLoadLayout mSwipeToLoadLayout;
    /**
     * 增加一个测试类，需要同时在2个数组增加
     */
    private Class[] classArr = {KeyboardAct.class, RecyclerSwipeAct.class, ExceptionAct.class, TestAct.class, WebviewActivity.class,AnnoAct.class};
    private String[] buttonArr = {"键盘", "Recycler和刷新", "异常", "普通测试", "webView","注解"};
    private Handler mHandler = new Handler();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        initData();
    }

    private void initData() {

    }

    private List<ViewHolderHandler.Item> getListData() {
        List<ViewHolderHandler.Item> list = new ArrayList<>();
        int len = classArr.length;
        for (int i = len - 1; i >= 0; i--) {
            ViewHolderHandler.Item item = new ViewHolderHandler.Item();
            MainViewHolder.MyObject myObject = new MainViewHolder.MyObject();
            myObject.type = i;
            myObject.text = buttonArr[i];
            myObject.buttonText = classArr[i].getSimpleName();
            item.object = myObject;
            item.itemType = ViewHolderHandler.ItemType.RECYCLERITEMMAIN;
            list.add(item);
        }
        return list;
    }

    private void initView() {
        mSwipeToLoadLayout = (SwipeToLoadLayout) findViewById(R.id.swipe);
        mSwipeToLoadLayout.setOnRefreshListener(this);
        mSwipeToLoadLayout.setOnLoadMoreListener(this);
        mRecyclerView = (RecyclerView) findViewById(R.id.swipe_target);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(linearLayoutManager);
        MyAdapter myAdapter = new MyAdapter(this);
        myAdapter.setOnBaseAdapterListener(this);
        myAdapter.addList(getListData());
        mRecyclerView.addItemDecoration(new MyItemDecoration(this));
        mRecyclerView.setAdapter(myAdapter);

    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void click(View view, ViewHolderHandler.Item item) {
        MainViewHolder.MyObject myObject = (MainViewHolder.MyObject) item.object;
        Intent intent = new Intent(MainActivity.this, classArr[myObject.type]);
        intent.putExtra(TITLE,buttonArr[myObject.type]);
        startActivity(intent);
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
                mSwipeToLoadLayout.setLoadingMore(false);
            }
        }, 700);
    }


    public class MyAdapter extends BaseRecyclerAdapter {

        public MyAdapter(Context context) {
            super(context);
        }

    }

    public static class MyItemDecoration extends RecyclerView.ItemDecoration {
        private Context mContext;
        private Paint dividerPaint;
        private int dividerHeight;

        public MyItemDecoration(Context context) {
            this.mContext = context;
            dividerPaint = new Paint();
            dividerPaint.setColor(context.getResources().getColor(R.color.divider_purchase));
            dividerHeight = CommonUtils.dip2px(mContext.getApplicationContext(), 10);
        }

        @Override
        public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
            super.getItemOffsets(outRect, view, parent, state);
            outRect.bottom = dividerHeight;

        }
    }


}