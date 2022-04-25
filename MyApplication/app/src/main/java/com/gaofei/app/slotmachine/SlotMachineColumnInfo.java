package com.gaofei.app.slotmachine;

/**
 * Created by gaofei3 on 2022/4/24
 * Describe: 老虎机每一列信息
 */
public class SlotMachineColumnInfo {
    private int columnIndex;
    private SlotMachineElementInfo[] slotMachineElementInfoArray;

    public int getColumnIndex() {
        return columnIndex;
    }

    public void setColumnIndex(int columnIndex) {
        this.columnIndex = columnIndex;
    }

    public SlotMachineElementInfo[] getSlotMachineElementInfoArray() {
        return slotMachineElementInfoArray;
    }

    public void setSlotMachineElementInfoArray(SlotMachineElementInfo[] slotMachineElementInfoArray) {
        this.slotMachineElementInfoArray = slotMachineElementInfoArray;
    }
}
