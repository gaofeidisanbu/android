package com.example.gaofei.app.act.holder;

import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * Created by gaofei on 2017/6/29.
 */

public abstract class MyBaseViewHolder extends RecyclerView.ViewHolder {
    protected View mRootView;
    public MyBaseViewHolder(View itemView) {
        super(itemView);
        this.mRootView = itemView;
    }

    public abstract void onBindViewHolder(ViewHolderHandler.Item item);

    public View findViewById(int id){
        return mRootView.findViewById(id);
    }
}
