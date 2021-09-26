package com.gaofei.app.widget;

import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.FrameLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;


/**
 * Created by gaofei3 on 2020/9/17
 * Describe:
 */
public class NewUserSendGiftGuideView extends FrameLayout {
    public NewUserSendGiftGuideView(@NonNull Context context) {
        super(context);
        init(context);
    }

    public NewUserSendGiftGuideView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public NewUserSendGiftGuideView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    public NewUserSendGiftGuideView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(context);
    }

    private void init(Context context) {
        setBackgroundColor(Color.parseColor("#C0fff666"));
//        LayoutInflater.from(context).inflate(R.layout.layout_new_user_send_gift_guide, this);
    }
}
