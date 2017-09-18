package com.gaofei.app.act;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.databinding.DataBindingUtil;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.view.View;

import com.gaofei.app.R;
import com.gaofei.app.databinding.ActPermissionBinding;
import com.gaofei.library.base.BaseAct;
import com.gaofei.library.utils.LogUtils;
import com.gaofei.library.utils.ToastManager;

/**
 * Created by gaofei on 2017/9/18.
 */

public class PermissionAct extends BaseAct implements View.OnClickListener {
    private ActPermissionBinding mBinding;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this, R.layout.act_permission);
        mBinding.button1.setOnClickListener(this);
        mBinding.button2.setOnClickListener(this);
        mBinding.button3.setOnClickListener(this);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == PERMISSION_WRITE_EXTERNAL_STORAGE_REQUEST_CODE) {
            int len = permissions.length;
            if (len > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                permissionSuccess();
            } else {
                permissionFail();
            }
        }
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.button1:
                requestPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE, PERMISSION_WRITE_EXTERNAL_STORAGE_REQUEST_CODE);
                break;
            case R.id.button2:
                requestPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE, PERMISSION_WRITE_EXTERNAL_STORAGE_REQUEST_CODE);
                break;
            case R.id.button3:
                requestPermission(this, Manifest.permission.READ_PHONE_STATE, PERMISSION_WRITE_EXTERNAL_STORAGE_REQUEST_CODE);
                break;
        }
    }

    public void permissionSuccess() {
        ToastManager.show("permissionSuccess");
    }

    public void permissionFail() {
        ToastManager.show("permissionFail");
    }

    public static final int PERMISSION_WRITE_EXTERNAL_STORAGE_REQUEST_CODE = 1;

    private void requestPermission(Activity context, String permission, int requestCode) {
        if (Build.VERSION.SDK_INT >= 23) {
            int permissionResult = ContextCompat.checkSelfPermission(context, permission);
            if (permissionResult != PackageManager.PERMISSION_GRANTED) {
                if (ActivityCompat.shouldShowRequestPermissionRationale(context,
                        permission)) {
                    ToastManager.show("该权限必须！");
                    ActivityCompat.requestPermissions(context, new String[]{permission}, requestCode);
                }else {
                    ActivityCompat.requestPermissions(context, new String[]{permission}, requestCode);
                }

            } else {
                // TODO: 2017/9/18
                permissionSuccess();
            }
        } else {
            // // TODO: 2017/9/18
            permissionSuccess();
        }
    }
}
