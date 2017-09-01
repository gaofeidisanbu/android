package com.gaofei.app.act.view.swipetoloadlayout;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.gaofei.app.R;
import com.gaofei.library.utils.LogUtils;

/**
 * Created by Aspsine on 2015/8/13.
 */
public class SwipeRefreshHeaderLayout extends FrameLayout implements SwipeRefreshTrigger, SwipeTrigger {
    private View mRootView;
    private View mLoading ;
    private TextView mText;

    public SwipeRefreshHeaderLayout(Context context) {
        this(context, null);
        init(context);
    }

    public SwipeRefreshHeaderLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
        init(context);
    }

    public SwipeRefreshHeaderLayout(Context context, AttributeSet attrs, int defStyleAttr) {
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
    public void onRefresh() {
        mText.setText(R.string.refresh_data_loading_str);
        mLoading.setVisibility(View.VISIBLE);
        LogUtils.d("SwipeLoadMoreFooterLayout onLoadMore");
    }

    @Override
    public void onPrepare() {
        mText.setText(R.string.refresh_data_str);
    }

    @Override
    public void onMove(int y, boolean isComplete, boolean automatic) {
    }

    @Override
    public void onRelease() {

    }

    @Override
    public void onComplete() {
        mText.setText(R.string.refresh_data_success_str);
        mLoading.setVisibility(View.VISIBLE);
        mLoading.setVisibility(View.GONE);
    }

    @Override
    public void onReset() {
    }
}
