package com.example.gaofei.myapplication.act.holder;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.example.gaofei.myapplication.R;
import com.example.gaofei.myapplication.act.adapter.BaseRecyclerAdapter;

/**
 * Created by gaofei on 2017/6/29.
 */

public class ViewHolderHandler {

    public static int[] layout = {R.layout.recycler_view_item_main};

    public static class Item {
        // 一种类型对应一种样式
        public ItemType itemType;
        public Object object;
    }

    public enum ItemType {
        RECYCLERITEMMAIN;

    }

    public static MyBaseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
       return onCreateViewHolder(parent, viewType, null);
    }

    public static MyBaseViewHolder onCreateViewHolder(ViewGroup parent, int viewType, BaseRecyclerAdapter.OnBaseAdapterListener onListener) {
        int ordinal = ViewHolderHandler.ItemType.RECYCLERITEMMAIN.ordinal();
        int layoutId = ViewHolderHandler.layout[ordinal];
        if (viewType == ordinal) {
            return new MainViewHolder(LayoutInflater.from(parent.getContext()).inflate(layoutId, null), onListener);
        }
        return null;
    }
}
