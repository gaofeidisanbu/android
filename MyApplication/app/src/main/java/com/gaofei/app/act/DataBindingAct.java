package com.gaofei.app.act;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.gaofei.app.act.common.UserInfo;
import com.gaofei.library.base.BaseAct;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.FileWriter;

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
        JSONArray jsonArray = new JSONArray();
        int len = jsonArray.length();
        for (int i = 0; i< len; i++) {
            try {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                String fileName = "/Users/gaofei/Desktop/腾讯小程序/压测脚本整理/report1.txt";
                Integer reportId = jsonObject.getInt("id");
                FileWriter fw = new FileWriter(fileName, true);
                fw.write(reportId);
                fw.write("\r\n");
                fw.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }


}
