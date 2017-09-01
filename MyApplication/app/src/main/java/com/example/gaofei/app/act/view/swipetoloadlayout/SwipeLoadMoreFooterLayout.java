package com.example.gaofei.app.act.view.swipetoloadlayout;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.example.gaofei.app.R;
import com.example.mylibrary.utils.LogUtils;

/**
 * Created by Aspsine on 2015/8/13.
 */
public class SwipeLoadMoreFooterLayout extends FrameLayout implements SwipeLoadMoreTrigger, SwipeTrigger {

    private View mRootView;
    private View mLoading ;
    private TextView mText;

    public SwipeLoadMoreFooterLayout(Context context) {
        this(context, null);
    }

    public SwipeLoadMoreFooterLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
        init(context);
    }

    public SwipeLoadMoreFooterLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {
        mRootView =  LayoutInflater.from(context).inflate(R.layout.layout_swipe_load_more,null);
        mLoading = mRootView.findViewById(R.id.loading);
        mText = (TextView)mRootView.findViewById(R.id.text);
        mLoading.setVisibility(View.GONE);
        addView(mRootView);
    }


    @Override
    public void onLoadMore() {
        mText.setText(R.string.load_more_data_loading_str);
        mLoading.setVisibility(View.VISIBLE);
        LogUtils.d("SwipeLoadMoreFooterLayout onLoadMore");
    }

    @Override
    public void onPrepare() {
        mText.setText(R.string.load_more_data_str);
        LogUtils.d("SwipeLoadMoreFooterLayout onPrepare");
    }

    @Override
    public void onMove(int y, boolean isComplete, boolean automatic) {
//        LogUtils.d("SwipeLoadMoreFooterLayout onMove");
    }

    @Override
    public void onRelease() {
        LogUtils.d("SwipeLoadMoreFooterLayout onRelease");
    }

    @Override
    public void onComplete() {
        mText.setText(R.string.load_more_data_success_str);
        mLoading.setVisibility(View.VISIBLE);
        mLoading.setVisibility(View.GONE);
        LogUtils.d("SwipeLoadMoreFooterLayout onComplete");
    }

    @Override
    public void onReset() {
        LogUtils.d("SwipeLoadMoreFooterLayout onReset");
    }
}
