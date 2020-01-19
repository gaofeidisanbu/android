package com.gaofei.app.act;

import android.Manifest;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;

import com.gaofei.app.R;
import com.gaofei.library.base.BaseAct;
import com.gaofei.library.permission.PermissionFail;
import com.gaofei.library.permission.PermissionHandler;
import com.gaofei.library.permission.PermissionSuccess;
import com.gaofei.library.utils.LogUtils;

/**
 * Created by gaofei on 2017/9/18.
 */

public class PermissionAct extends BaseAct implements View.OnClickListener {
    public static final int PERMISSION_WRITE_EXTERNAL_STORAGE_REQUEST_CODE = 1;
    public static final int PERMISSION_READ_EXTERNAL_STORAGE_REQUEST_CODE = 2;
    public static final int PERMISSION_READ_PHONE_STATE_REQUEST_CODE = 3;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_permission);
        Button button1 = findViewById(R.id.button1);
        Button button2 = findViewById(R.id.button2);
        Button button3 = findViewById(R.id.button3);
        button1.setOnClickListener(this);
        button2.setOnClickListener(this);
        button3.setOnClickListener(this);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        PermissionHandler.onRequestPermissionsResult(this, requestCode, permissions, grantResults);
    }

    @PermissionSuccess(requestCode = PERMISSION_WRITE_EXTERNAL_STORAGE_REQUEST_CODE)
    public void onRequestSuccessPERMISSION_WRITE_EXTERNAL_STORAGE_REQUEST_CODE() {
        LogUtils.d("onRequestSuccessPERMISSION_WRITE_EXTERNAL_STORAGE_REQUEST_CODE");
    }

    @PermissionFail(requestCode = PERMISSION_WRITE_EXTERNAL_STORAGE_REQUEST_CODE)
    public void onRequestFailPERMISSION_WRITE_EXTERNAL_STORAGE_REQUEST_CODE() {
        LogUtils.d("onRequestFailPERMISSION_WRITE_EXTERNAL_STORAGE_REQUEST_CODE");
    }

    @PermissionSuccess(requestCode = PERMISSION_READ_EXTERNAL_STORAGE_REQUEST_CODE)
    public void onRequestSuccessPERMISSION_READ_EXTERNAL_STORAGE_REQUEST_CODE() {
        LogUtils.d("onRequestSuccessPERMISSION_READ_EXTERNAL_STORAGE_REQUEST_CODE");
    }

    @PermissionFail(requestCode = PERMISSION_READ_EXTERNAL_STORAGE_REQUEST_CODE)
    public void onRequestFailPERMISSION_READ_EXTERNAL_STORAGE_REQUEST_CODE() {
        LogUtils.d("onRequestFailPERMISSION_READ_EXTERNAL_STORAGE_REQUEST_CODE");
    }

    @PermissionSuccess(requestCode = PERMISSION_READ_PHONE_STATE_REQUEST_CODE)
    public void onRequestSuccessPERMISSION_READ_PHONE_STATE_REQUEST_CODE() {
        LogUtils.d("onRequestSuccessPERMISSION_READ_PHONE_STATE_REQUEST_CODE");
    }

    @PermissionFail(requestCode = PERMISSION_READ_PHONE_STATE_REQUEST_CODE)
    public void onRequestFailPERMISSION_READ_PHONE_STATE_REQUEST_CODE() {
        LogUtils.d("onRequestFailPERMISSION_READ_PHONE_STATE_REQUEST_CODE");
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.button1:
                PermissionHandler.requestPermission(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, PERMISSION_WRITE_EXTERNAL_STORAGE_REQUEST_CODE);
                break;
            case R.id.button2:
                PermissionHandler.requestPermission(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, PERMISSION_READ_EXTERNAL_STORAGE_REQUEST_CODE);
                break;
            case R.id.button3:
                PermissionHandler.requestPermission(this, new String[]{Manifest.permission.READ_PHONE_STATE}, PERMISSION_READ_PHONE_STATE_REQUEST_CODE);
                break;
        }
    }

}
