package com.gaofei.app.slotmachine;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

/**
 * Created by gaofei3 on 2022/4/24
 * Describe: 老虎机View
 */
public class SlotMachineView extends View {
    private SlotMachineControl mSlotMachineControl;
    private SlotMachinePlayer mPlayer;

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
        mPlayer = new SlotMachinePlayer();
    }

    public void startSpin(@NonNull SlotMachinePlayInfo playInfo, @NonNull OnSlotMachinePlayerListener listener) {
        mPlayer.startSpin(playInfo, listener);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
    }


}
