package com.gaofei.app.slotmachine;

import androidx.annotation.NonNull;

/**
 * Created by gaofei3 on 2022/4/24
 * Describe:老虎机播放器
 */
public class SlotMachinePlayer {
    private boolean isInitSuccess;
    private final static int LENGTH = 3;
    private final SlotMachineColumnInfo[] mColumnInfoArray = new SlotMachineColumnInfo[3];

    public boolean init(@NonNull SlotMachineColumnInfo[] columnInfoArray) {
        isInitSuccess = false;
        if (columnInfoArray.length != LENGTH) {
            return false;
        }
        for (SlotMachineColumnInfo slotMachineColumnInfo: mColumnInfoArray) {
            if (slotMachineColumnInfo == null || validateSlotMachineColumnInfo(slotMachineColumnInfo)) {
                return false;
            }
        }
        for (int i = 0; i < LENGTH; i++) {
            mColumnInfoArray[i] = columnInfoArray[i];
        }
        isInitSuccess = true;
        return true;
    }


    private boolean validateSlotMachineColumnInfo(@NonNull SlotMachineColumnInfo columnInfo) {
        return true;
    }

    public void startSpin(@NonNull SlotMachinePlayInfo playInfo, @NonNull OnSlotMachinePlayerListener listener) {

    }

}
