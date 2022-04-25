package com.gaofei.app.slotmachine;

/**
 * Created by gaofei3 on 2022/4/25
 * Describe: 老虎机播放信息
 */
public class SlotMachinePlayInfo {
    private SlotMachineColumnInfo[] startArray;
    private SlotMachineColumnInfo[] endArray;
    private int round;

    public SlotMachineColumnInfo[] getStartArray() {
        return startArray;
    }

    public SlotMachineColumnInfo[] getEndArray() {
        return endArray;
    }

    public int getRound() {
        return round;
    }

    public void setRound(int round) {
        this.round = round;
    }
}
