package com.gaofei.library.base;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.MenuItem;

import com.gaofei.library.utils.GFPreferenceManager;
import com.gaofei.library.utils.LogUtils;


/**
 * Created by gaofei on 2017/5/17.
 */

public class BaseAct extends AppCompatActivity {
    public String TAG;
    private final static String COMMON_TAG = "GAOFEI:";
    public static final String TITLE = "title";
    public String mClassName;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mClassName = getClass().getSimpleName();
        TAG = COMMON_TAG + mClassName;
        LogUtils.d(TAG, "------- onCreate savedInstanceState =  " + (savedInstanceState != null ? savedInstanceState.toString() : "null") );
        LogUtils.d(TAG, " processId = "+android.os.Process.myPid()+" threadId = "+Thread.currentThread().getId());
        initActionBar();
    }

    protected void initActionBar() {
        String title = getIntent().getStringExtra(TITLE);
        if (TextUtils.isEmpty(title)) {
            title = mClassName;
        }
        android.support.v7.app.ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setTitle(title);
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
    }


    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        LogUtils.d(TAG, "------- onRestoreInstanceState ---------> " + GFPreferenceManager.getStringValue(TAG + "aa", "----") + " hashCode = " + hashCode());
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        LogUtils.d(TAG, "------- onRestart");
    }

    @Override
    protected void onStart() {
        super.onStart();
        LogUtils.d(TAG, "------- onStart");
    }

    @Override
    protected void onResume() {
        super.onResume();
        LogUtils.d(TAG, "------- onResume");
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        LogUtils.d(TAG, "------- onNewIntent");
    }

    @Override
    protected void onPause() {
        super.onPause();
        LogUtils.d(TAG, "------- onPause");
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        GFPreferenceManager.putString(TAG + "aa", "onSaveInstanceState = " + hashCode());
        LogUtils.d(TAG, "------- onSaveInstanceState");
    }

    @Override
    protected void onStop() {
        super.onStop();
        LogUtils.d(TAG, "------- onStop");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        LogUtils.d(TAG, "------- onDestroy");
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}
