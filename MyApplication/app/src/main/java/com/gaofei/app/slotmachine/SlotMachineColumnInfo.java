package com.gaofei.app.slotmachine;

import java.util.List;

/**
 * Created by gaofei3 on 2022/4/24
 * Describe: 老虎机每一列信息
 */
public class SlotMachineColumnInfo {
    private int columnIndex;
    private List<SlotMachineElementInfo> slotMachineElementInfoList;

    public int getColumnIndex() {
        return columnIndex;
    }

    public void setColumnIndex(int columnIndex) {
        this.columnIndex = columnIndex;
    }

    public List<SlotMachineElementInfo> getSlotMachineElementInfoList() {
        return slotMachineElementInfoList;
    }

    public void setSlotMachineElementInfoArray(List<SlotMachineElementInfo> slotMachineElementInfoList) {
        this.slotMachineElementInfoList = slotMachineElementInfoList;
    }
}
