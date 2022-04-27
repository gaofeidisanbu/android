package com.gaofei.app.slotmachine;

import androidx.annotation.NonNull;

/**
 * Created by gaofei3 on 2022/4/24
 * Describe:老虎机播放器
 */
public class SlotMachinePlayer {
    private final static int LENGTH = 3;
    private SlotMachineColumnInfo mColumnInfo0;
    private SlotMachineColumnInfo mColumnInfo1;
    private SlotMachineColumnInfo mColumnInfo2;
    private SlotMachineCanvas mCanvas;
    private boolean isInitSuccess;

    public boolean init(@NonNull SlotMachineColumnInfo columnInfo0, @NonNull SlotMachineColumnInfo columnInfo1, @NonNull SlotMachineColumnInfo columnInfo2) {
        isInitSuccess = false;
        this.mColumnInfo0 = columnInfo0;
        this.mColumnInfo1 = columnInfo1;
        this.mColumnInfo2 = columnInfo2;
        isInitSuccess = true;
        return true;
    }


    public void startSpin(@NonNull SlotMachinePlayInfo playInfo, @NonNull OnSlotMachinePlayerListener listener, @NonNull OnPlayerListener onPlayerListener) {
        SlotMachineAnimation slotMachineAnimation0 = SlotMachineAnimation
                .newBuilder()
                .setElementInfoList(mColumnInfo0.getSlotMachineElementInfoList())
                .setElementSize(mCanvas.getWidth(), mCanvas.getHeight())
                .setStartIndex()
                .setEndIndex()
                .setSlotMachineAnimationListener(new SlotMachineAnimation.OnSlotMachineAnimationListener() {
                    @Override
                    public void onAnimationStart() {

                    }

                    @Override
                    public void onAnimationUpdate(SlotMachineElementInfo startElementInfo, float offset) {
                        draw(startElementInfo, offset);
                        onPlayerListener.onFrameUpdate();
                    }

                    @Override
                    public void onAnimationEnd() {

                    }
                }).builder();
    }

    private void calculate() {

    }


    private void draw(SlotMachineElementInfo elementInfoArray, float yOffset) {

    }


    private boolean validateSlotMachineColumnInfo(@NonNull SlotMachineColumnInfo columnInfo) {
        return true;
    }


}
