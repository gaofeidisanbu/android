package com.gaofei.app.slotmachine;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.List;

/**
 * Created by gaofei3 on 2022/4/24
 * Describe: 老虎机View
 */
public class SlotMachineView extends View {
    private SlotMachineControl mSlotMachineControl;
    private SlotMachinePlayer mPlayer;
    private SlotMachineCanvas mCanvas;

    public SlotMachineView(Context context) {
        this(context, null, 0, 0);
    }

    public SlotMachineView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0, 0);
    }

    public SlotMachineView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        this(context, attrs, defStyleAttr, 0);
    }

    public SlotMachineView(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(context);
    }

    private void init(Context context) {
        mSlotMachineControl = new SlotMachineControl(context);
        mCanvas = new SlotMachineCanvas();
        mPlayer = new SlotMachinePlayer(context, mCanvas);
        setWillNotDraw(false);
    }

    public void setData(@NonNull List<SlotMachineElementInfo> columnInfo0, @NonNull List<SlotMachineElementInfo> columnInfo1, @NonNull List<SlotMachineElementInfo> columnInfo2) {
        mPlayer.init( columnInfo0, columnInfo1, columnInfo2);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        if (mCanvas.invalid()) {
            int result = mCanvas.initialize(getWidth(), getHeight());
            if( 0 != result ) {
                throw new RuntimeException("fuck!");
            }
        }
        int result;
        if (!mCanvas.invalid()) {
            result = mCanvas.resize(getWidth(), getHeight());
            if (0 != result) {
                mCanvas.release();
            }
        }
        return;
    }

    public void startSpin(@NonNull SlotMachinePlayInfo playInfo, @NonNull OnSlotMachinePlayerListener listener) {
        mPlayer.startSpin(playInfo, listener, new OnPlayerListener() {
            @Override
            public void onFrameUpdate() {
                invalidate();
            }
        });
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        mPlayer.onDraw(canvas);
    }


}
