package com.gaofei.app.act.holder;

import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.gaofei.app.R;
import com.gaofei.app.act.ProcessAct;
import com.gaofei.app.act.adapter.BaseRecyclerAdapter;

/**
 * Created by gaofei on 2017/9/11.
 */

public class ProcessHolder extends MyBaseViewHolder {
    private BaseRecyclerAdapter.OnBaseAdapterListener onBaseAdapterListener;

    public ProcessHolder(View itemView) {
        this(itemView, null);
    }

    public ProcessHolder(View itemView, BaseRecyclerAdapter.OnBaseAdapterListener onListener) {
        super(itemView);
        mRootView = itemView;
        this.onBaseAdapterListener = onListener;
    }

    @Override
    public void onBindViewHolder(final ViewHolderHandler.Item item) {
        final ProcessAct.ProcessInfo myObject = (ProcessAct.ProcessInfo) item.object;
        TextView textView = (TextView) findViewById(R.id.text);
        Button button = (Button) findViewById(R.id.button);
        textView.setText(myObject.progressName + "   " + myObject.pid);
        button.setText(myObject.pid + "");
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onBaseAdapterListener != null) {
                    onBaseAdapterListener.click(v, item);
                }
            }
        });
    }


}
