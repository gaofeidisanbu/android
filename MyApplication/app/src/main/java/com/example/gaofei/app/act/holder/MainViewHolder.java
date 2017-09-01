package com.example.gaofei.app.act.holder;

import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.gaofei.app.R;
import com.example.gaofei.app.act.adapter.BaseRecyclerAdapter;

/**
 * Created by gaofei on 2017/6/29.
 */

public class MainViewHolder extends MyBaseViewHolder {
    private BaseRecyclerAdapter.OnBaseAdapterListener onBaseAdapterListener;

    public MainViewHolder(View itemView) {
        this(itemView, null);
    }

    public MainViewHolder(View itemView, BaseRecyclerAdapter.OnBaseAdapterListener onListener) {
        super(itemView);
        mRootView = itemView;
        this.onBaseAdapterListener = onListener;
    }

    @Override
    public void onBindViewHolder(final ViewHolderHandler.Item item) {
        final MyObject myObject = (MyObject) item.object;
        TextView textView = (TextView) findViewById(R.id.text);
        Button button = (Button) findViewById(R.id.button);
        textView.setText(myObject.text);
        button.setText(myObject.buttonText);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onBaseAdapterListener != null) {
                    onBaseAdapterListener.click(v, item);
                }
            }
        });
    }

    public static class MyObject {
        public int type;
        public String text;
        public String buttonText;
    }

}
