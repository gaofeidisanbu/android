package com.gaofei.app;

import android.content.Context;
import android.content.Intent;
import android.graphics.Paint;
import android.graphics.Rect;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.RequiresApi;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import com.gaofei.app.act.AnnoAct;
import com.gaofei.app.act.BarrageAnimationAct;
import com.gaofei.app.act.BroadcastReceiverAct;
import com.gaofei.app.act.ClockSetUpActivity;
import com.gaofei.app.act.DataBindingAct;
import com.gaofei.app.act.EditTextAct;
import com.gaofei.app.act.ExceptionAct;
import com.gaofei.app.act.FragmentAct;
import com.gaofei.app.act.KeyboardAct;
import com.gaofei.app.act.LayoutAct;
import com.gaofei.app.act.PermissionAct;
import com.gaofei.app.act.ProcessAct;
import com.gaofei.app.act.RecyclerSwipeAct;
import com.gaofei.app.act.ReflectAct;
import com.gaofei.app.act.ScreenshotAct;
import com.gaofei.app.act.SetUpAct;
import com.gaofei.app.act.TestAct;
import com.gaofei.app.act.TouchEventAct;
import com.gaofei.app.act.ViewPagerAct;
import com.gaofei.app.act.adapter.BaseRecyclerAdapter;
import com.gaofei.app.act.holder.MainViewHolder;
import com.gaofei.app.act.holder.ViewHolderHandler;
import com.gaofei.app.act.view.swipetoloadlayout.OnLoadMoreListener;
import com.gaofei.app.act.view.swipetoloadlayout.OnRefreshListener;
import com.gaofei.app.act.view.swipetoloadlayout.SwipeToLoadLayout;
import com.gaofei.app.webview.WebviewActivity;
import com.gaofei.library.TestKotlinKt;
import com.gaofei.library.base.BaseAct;
import com.gaofei.library.utils.CommonUtils;
import com.gaofei.library.utils.LogUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import io.reactivex.Flowable;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.internal.functions.Functions;
import io.reactivex.schedulers.Schedulers;

public class MainActivity extends BaseAct implements BaseRecyclerAdapter.OnBaseAdapterListener, OnRefreshListener, OnLoadMoreListener {
    private RecyclerView mRecyclerView;
    private SwipeToLoadLayout mSwipeToLoadLayout;
    public static String process = "default";
    /**
     * 增加一个测试类，需要同时在2个数组增加
     */
    private Class[] classArr = {KeyboardAct.class, RecyclerSwipeAct.class, ExceptionAct.class, TestAct.class,
            WebviewActivity.class, AnnoAct.class, ScreenshotAct.class, LayoutAct.class,
            BroadcastReceiverAct.class,ProcessAct.class,PermissionAct.class,ReflectAct.class,
            TouchEventAct.class,ViewPagerAct.class,FragmentAct.class, EditTextAct.class, DataBindingAct.class, BarrageAnimationAct.class, CanvasActivity.class, ClockSetUpActivity.class};
    private String[] buttonArr = {"键盘", "Recycler和刷新", "异常", "普通测试", "webView",
            "注解", "截屏", "layout", "广播","进程信息","权限","反射","touchEvent","ViewPager","fragment","editText","dataBinding", "弹幕动画", "canvas", "clock"};
    private Handler mHandler = new Handler();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        process = "MainActivity";
        LogUtils.d(process);
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
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.setup, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.setup) {
            SetUpAct.intentTo(this);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void click(View view, ViewHolderHandler.Item item) {
        MainViewHolder.MyObject myObject = (MainViewHolder.MyObject) item.object;
        Intent intent = new Intent(MainActivity.this, classArr[myObject.type]);
        intent.putExtra(TITLE, buttonArr[myObject.type]);
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
    protected void onStop() {
        super.onStop();
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