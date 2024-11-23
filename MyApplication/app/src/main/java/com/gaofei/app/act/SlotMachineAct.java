package com.gaofei.app.act;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.view.View;

import androidx.annotation.Nullable;

import com.gaofei.app.R;
import com.gaofei.app.serivce.EasyService;
import com.gaofei.app.slotmachine.SlotMachineElementInfo;
import com.gaofei.app.slotmachine.SlotMachineView;
import com.gaofei.library.base.BaseAct;
import com.gaofei.library.utils.LogUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by gaofei3 on 2022/4/24
 * Describe:老虎机
 */
public class SlotMachineAct extends BaseAct {
    private SlotMachineView slotMachine;
    private final Handler mHandler = new Handler(Looper.getMainLooper());

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_slot_machine);
        slotMachine = findViewById(R.id.slotMachine);
        initSlotMachine();
        findViewById(R.id.start).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                slotMachine.startSpin(null, null);
//                mHandler.postDelayed(new Runnable() {
//                    @Override
//                    public void run() {
//                        startService(new Intent(SlotMachineAct.this, EasyService.class));
                LogUtils.d("EasyService starService");
//                        Intent intent = new Intent("com.gaofei.app.serivce.EasyService");
//                        intent.setPackage("com.gaofei.app");
//                        startService(intent);

//                    }
//                }, 3000);
                startService(new Intent(SlotMachineAct.this, EasyService.class));
//                bindService(new Intent(SlotMachineAct.this, EasyService.class), new ServiceConnection() {
//                    @Override
//                    public void onServiceConnected(ComponentName name, IBinder service) {
//                        LogUtils.d("EasyService onServiceConnected");
//                    }
//
//                    @Override
//                    public void onServiceDisconnected(ComponentName name) {
//                        LogUtils.d("EasyService onServiceDisconnected");
//                    }
//                }, Context.BIND_AUTO_CREATE);
            }
        });
        findViewById(R.id.exception).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                throw new RuntimeException("手动");
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
