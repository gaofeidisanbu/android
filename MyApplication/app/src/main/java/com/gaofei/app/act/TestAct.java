package com.gaofei.app.act;

import android.animation.ValueAnimator;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.NotificationCompat;
import android.support.v4.widget.NestedScrollView;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.TextPaint;
import android.text.TextUtils;
import android.text.style.AbsoluteSizeSpan;
import android.text.style.ForegroundColorSpan;
import android.text.style.StrikethroughSpan;
import android.text.style.StyleSpan;
import android.util.Log;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.Button;
import android.widget.TextView;

import com.airbnb.lottie.LottieAnimationView;
import com.gaofei.app.R;
import com.gaofei.app.databinding.ActTestBinding;
import com.gaofei.library.base.BaseAct;
import com.gaofei.library.utils.CommonUtils;
import com.gaofei.library.utils.LogUtils;

/**
 * Created by gaofei on 2017/6/29.
 */

public class TestAct extends BaseAct {
    private ActTestBinding mBinding;
    private boolean isToolBarShow = false;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this, R.layout.act_test);
        mBinding.image2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NotificationManager mNotificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
                NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(TestAct.this);
                PendingIntent mPendingIntent = PendingIntent.getActivity(TestAct.this, 1, new Intent(), Notification.FLAG_AUTO_CANCEL);
                mBuilder.setContentTitle("测试标题")
                        .setLargeIcon(BitmapFactory.decodeResource(getResources(),R.mipmap.task_reward_coin))
                        .setContentText("测试内容")
//设置通知栏点击意图
                        .setContentIntent(mPendingIntent)
//通知首次出现在通知栏，带上升动画效果的
                        .setTicker("测试通知来啦")
//通知产生的时间，会在通知信息里显示，一般是系统获取到的时间
                        .setWhen(System.currentTimeMillis())
                        //设置该通知优先级
                        .setPriority(Notification.PRIORITY_DEFAULT)
//设置这个标志当用户单击面板就可以让通知将自动取消
                        .setAutoCancel(true)
//使用当前的用户默认设置
                        .setDefaults(Notification.DEFAULT_VIBRATE)
//设置通知小ICON(应用默认图标)
                        .setSmallIcon(R.mipmap.ic_launcher);
                mNotificationManager.notify(1, mBuilder.build());
            }
        });
    }


}
