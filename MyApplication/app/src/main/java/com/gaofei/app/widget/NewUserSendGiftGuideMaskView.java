package com.gaofei.app.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * Created by gaofei3 on 2020/9/17
 * Describe:
 */
public class NewUserSendGiftGuideMaskView extends MaskViewProcessor {

    public NewUserSendGiftGuideMaskView(@NotNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @NotNull
    @Override
    public View getMaskView() {
        return new NewUserSendGiftGuideView(getContext()) {
            @Override
            protected void onDraw(Canvas canvas) {
                super.onDraw(canvas);
                float left = getTargetOnScreenX();
                float top = getTargetOnScreenY();
                float right = getTargetWidth();
                float bottom = getTargetHeight();
                float width = getMeasuredWidth();
                float height = getMeasuredHeight();
                right = 1000;
                bottom = 1000;
                RectF rectF = new RectF(20, 20, right, bottom);
                digRoundRect(canvas, rectF, 0, 0, null);
            }
        };
    }
}
