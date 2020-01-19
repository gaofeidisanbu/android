package com.gaofei.app.act;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.gaofei.app.R;
import com.gaofei.app.act.common.UserInfo;
import com.gaofei.library.base.BaseAct;

/**
 * Created by gaofei on 30/01/2018.
 */

public class DataBindingAct extends BaseAct {
    private UserInfo mUserInfo;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mUserInfo = new UserInfo("高飞", "高飞第三部");
    }

    @Override
    protected void onResume() {
        super.onResume();
    }


}
