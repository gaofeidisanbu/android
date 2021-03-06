package com.example.gaofei.myapplication.act.adapter;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.gaofei.myapplication.R;
import com.example.mylibrary.Test2;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by gaofei on 2017/6/13.
 */

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.RecyclerViewViewHolder>{
    private List<String> mList = new ArrayList<String>();
    private Context mContext;
    public RecyclerViewAdapter(Context context){
        this.mContext = context;
    }
    @Override
    public RecyclerViewViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new RecyclerViewViewHolder(LayoutInflater.from(mContext).inflate(R.layout.recycler_view_item,null));
    }

    @Override
    public void onBindViewHolder(RecyclerViewViewHolder holder, int position) {
        String str = mList.get(position);
        holder.mTextView.setText(str);
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public void add(List<String> data) {
        mList.addAll(data);
        notifyDataSetChanged();
    }

    public static class RecyclerViewViewHolder extends RecyclerView.ViewHolder{
        public TextView mTextView;

        public RecyclerViewViewHolder(View itemView) {
            super(itemView);
            mTextView = (TextView) itemView.findViewById(R.id.text);
        }
    }

    public static class MyItemDecoration extends RecyclerView.ItemDecoration{
        private Context mContext;
        private Paint dividerPaint;
        private int dividerHeight;

        public MyItemDecoration(Context context) {
            this.mContext = context;
            dividerPaint = new Paint();
//            dividerPaint.setColor(context.getResources().getColor(R.color.divider_purchase));
//            dividerHeight = CommonUtils.dip2px(mContext.getApplicationContext(), 40);
        }

//        @Override
//        public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
////            super.getItemOffsets(outRect, view, parent, state);
//            int count = parent.getLayoutManager().getItemCount();
//            boolean last = parent.getChildLayoutPosition(view) == count - 1;
//            boolean lastSecond = parent.getChildLayoutPosition(view) == count - 2;
//            outRect.bottom = last || lastSecond ? 0 : dividerHeight;
//        }

        @Override
        public void onDraw(Canvas c, RecyclerView parent, RecyclerView.State state) {
            int childCount = parent.getChildCount();
            int left = parent.getPaddingLeft();
            int right = parent.getWidth() - parent.getPaddingRight();

            for (int i = 0; i < childCount - 1; i++) {

                View view = parent.getChildAt(i);
                float top = view.getBottom();
                float bottom = view.getBottom() + dividerHeight;
                c.drawRect(left, top, right, bottom, dividerPaint);
            }
        }
    }
}
