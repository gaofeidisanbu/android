package com.gaofei.app.act.adapter;

import android.content.Context;
import androidx.recyclerview.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import com.gaofei.app.act.holder.MyBaseViewHolder;
import com.gaofei.app.act.holder.ViewHolderHandler;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by gaofei on 2017/6/29.
 */

public class BaseRecyclerAdapter extends RecyclerView.Adapter<MyBaseViewHolder> {
    private Context mContext;
    private OnBaseAdapterListener onBaseAdapterListener;

    public BaseRecyclerAdapter(Context context) {
        this.mContext = context;

    }


    public List<ViewHolderHandler.Item> mList = new ArrayList<>();


    @Override
    public MyBaseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return ViewHolderHandler.onCreateViewHolder(parent,viewType,onBaseAdapterListener);
    }

    @Override
    public void onBindViewHolder(MyBaseViewHolder holder, int position) {
        holder.onBindViewHolder(mList.get(position));
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    @Override
    public int getItemViewType(int position) {
        return mList.get(position).itemType.ordinal();
    }

    public void addList(List<ViewHolderHandler.Item> list) {
        mList.addAll(list);
        notifyDataSetChanged();
    }

    public void setOnBaseAdapterListener(OnBaseAdapterListener onListener) {
        this.onBaseAdapterListener = onListener;
    }

    public interface OnBaseAdapterListener{
        void click(View view, ViewHolderHandler.Item item);
    }
}
