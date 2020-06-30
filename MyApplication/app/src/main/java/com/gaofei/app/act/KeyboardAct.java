package com.gaofei.app.act;

import android.app.Activity;
import android.content.pm.ApplicationInfo;
import android.graphics.Rect;
import android.os.Bundle;
import androidx.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.view.ViewTreeObserver;

import com.gaofei.app.R;
import com.gaofei.library.base.BaseAct;
import com.gaofei.library.utils.LogUtils;
import com.gaofei.library.utils.ToastManager;

import java.io.File;
import java.io.IOException;
import java.util.zip.ZipFile;

/**
 * Created by gaofei on 2017/6/15.
 */

public class KeyboardAct extends BaseAct {
    public static final String TAG = "KeyboardAct";

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_keyboard);
        ToastManager.show("aaaaa49");
        int aa = 100;
        Log.d("aa", "aa = +"+aa);

        KeyboardHandler.setKeyboardListener(this, new KeyboardHandler.onKeyboardListener() {
            @Override
            public void onShowKeyboard(int keyboardHeight) {
                Log.d(TAG, "onShowKeyboard keyboardHeight = " + keyboardHeight);
                Log.d(TAG, "onShowKeyboard keyboardHeight3 = " + keyboardHeight);
            }

            @Override
            public void onHideKeboard() {
                Log.d(TAG, "onHideKeboard");
            }
        });

        ApplicationInfo applicationInfo = getApplicationContext().getApplicationInfo();
        Log.d("", applicationInfo + "");
        String str = applicationInfo.dataDir;
        File file = new File("/data/app");
        delete(file);
        String apkPath = applicationInfo.sourceDir; //base.apk
        try {
            ZipFile apk = new ZipFile(apkPath);
            Log.d("","apk = "+apk);
        } catch (IOException e) {
            e.printStackTrace();
        }
        fun();

    }

    private void fun() {

    }


    private void delete(File file) {
        if (file != null && file.isDirectory() && file.listFiles() != null) {
            for (File file1 : file.listFiles()) {
                if (file1 != null && file1.isDirectory()) {
                    delete(file1);
                } else {
                    boolean is = file1.delete();
                    LogUtils.d("delete file1 = " + file1.toString() + " is = " + is);
                }

            }
        } else {
            if (file.isFile()){

                boolean is =  file.delete();
                LogUtils.d("delete file1 = " + file.toString() + " is = " + is);
            }
        }
    }


    public static class KeyboardHandler {
        private Activity mAct;
        private onKeyboardListener mOnKeyboardListener;
        private boolean isShowKeyboard = false;
        // 状态栏高度
        private int statusHeight;
        //虚拟键盘高度
        private int virtualeyboardHeight;
        // 键盘高度
        private boolean keyboardHeight;
        // 键盘没有弹起的高度，内容区域高度
        private int contentSourceHeight = -1;

        public KeyboardHandler(Activity activity, onKeyboardListener onKeyboardListener) {
            this.mAct = activity;
            this.mOnKeyboardListener = onKeyboardListener;
            init();
        }

        private void init() {
            // 屏幕可视区域，包含状态栏，标题栏，内容和虚拟键盘
            final View decorView = mAct.getWindow().getDecorView();
            decorView.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
                @Override
                public void onGlobalLayout() {
                    Rect rect = new Rect();
                    // 屏幕高度，包含整个区域
                    int screenHeight = decorView.getMeasuredHeight();
                    decorView.getWindowVisibleDisplayFrame(rect);

                    statusHeight = rect.top;
                    virtualeyboardHeight = screenHeight - rect.bottom;
                    if (contentSourceHeight == -1) {
                        contentSourceHeight = rect.bottom - rect.top;
                        isShowKeyboard = false;
                    }
                    int currContentHeight = rect.bottom - rect.top;
                    if (!isShowKeyboard && contentSourceHeight != -1 && currContentHeight < contentSourceHeight) {
                        isShowKeyboard = true;
                        if (mOnKeyboardListener != null) {
                            mOnKeyboardListener.onShowKeyboard(contentSourceHeight - currContentHeight);
                        }
                    }
                    if (isShowKeyboard && currContentHeight == contentSourceHeight) {
                        isShowKeyboard = false;
                        if (mOnKeyboardListener != null) {
                            mOnKeyboardListener.onHideKeboard();
                        }
                    }
                    Log.d(TAG, "screenHeight = " + screenHeight + " statusHeight = " + statusHeight + " virtualeyboardHeight = " + virtualeyboardHeight + " contentSourceHeight = " + contentSourceHeight);
                }
            });
        }

        public static void setKeyboardListener(Activity activity, onKeyboardListener onKeyboardListener) {
            new KeyboardHandler(activity, onKeyboardListener);
        }

        public interface onKeyboardListener {
            void onShowKeyboard(int keyboardHeight);

            void onHideKeboard();
        }
    }

}
