package com.gaofei.app.act;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.gaofei.app.MainActivity;
import com.gaofei.app.R;
import com.gaofei.app.act.adapter.BaseRecyclerAdapter;
import com.gaofei.app.act.holder.SetUpHolder;
import com.gaofei.app.act.holder.ViewHolderHandler;
import com.gaofei.library.ProjectApplication;
import com.gaofei.library.base.BaseAct;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by gaofei on 2017/9/5.
 */

public class SetUpAct extends BaseAct implements BaseRecyclerAdapter.OnBaseAdapterListener {
    public static void intentTo(Activity activity) {
        activity.startActivity(new Intent(activity, SetUpAct.class));
    }

    private String[] textArr = {"打印activity状态信息"};
    private String[] buttonArr = {"打印"};
    private int[] typeArr = {0};

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView( R.layout.act_setup);
        BaseRecyclerAdapter adapter = new BaseRecyclerAdapter(this);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        RecyclerView recycler = findViewById(R.id.recycler);
        recycler.setLayoutManager(linearLayoutManager);
        adapter.setOnBaseAdapterListener(this);
        adapter.addList(getListData());
        recycler.addItemDecoration(new MainActivity.MyItemDecoration(this));
        recycler.setAdapter(adapter);
    }

    private List<ViewHolderHandler.Item> getListData() {
        List<ViewHolderHandler.Item> list = new ArrayList<>();
        int len = textArr.length;
        for (int i = 0; i < len; i++) {
            ViewHolderHandler.Item item = new ViewHolderHandler.Item();
            SetUpHolder.MyObject myObject = new SetUpHolder.MyObject();
            myObject.type = typeArr[i];
            myObject.text = textArr[i];
            myObject.buttonText = buttonArr[i];
            item.object = myObject;
            item.itemType = ViewHolderHandler.ItemType.RECYCLER_SETUP;
            list.add(item);
        }
        return list;
    }

    @Override
    public void click(View view, ViewHolderHandler.Item item) {
        SetUpHolder.MyObject myObject = (SetUpHolder.MyObject) item.object;
        if(myObject.type == typeArr[0]){
            ProjectApplication.getInstance().printActivityStateInfo();
        }
    }
}
