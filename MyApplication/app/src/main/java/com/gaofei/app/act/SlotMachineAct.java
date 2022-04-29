package com.gaofei.app.act;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;

import com.gaofei.app.R;
import com.gaofei.app.slotmachine.SlotMachineElementInfo;
import com.gaofei.app.slotmachine.SlotMachineView;
import com.gaofei.library.base.BaseAct;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by gaofei3 on 2022/4/24
 * Describe:老虎机
 */
public class SlotMachineAct extends BaseAct {
    private SlotMachineView slotMachine;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_slot_machine);
        slotMachine = findViewById(R.id.slotMachine);
        initSlotMachine();
        findViewById(R.id.start).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                slotMachine.startSpin(null, null);
            }
        });
    }

    private void initSlotMachine() {
        slotMachine.setData(createData(), createData(), createData());
    }

    private List<SlotMachineElementInfo> createData() {
        List<SlotMachineElementInfo> list = new ArrayList<>();
        for (int i = 0; i < 50; i++) {
            SlotMachineElementInfo slotMachineElementInfo = new SlotMachineElementInfo();
            slotMachineElementInfo.setIndex(i);
            slotMachineElementInfo.setKey(R.drawable.app_icon+"");
            list.add(slotMachineElementInfo);
        }
        return list;
    }
}
