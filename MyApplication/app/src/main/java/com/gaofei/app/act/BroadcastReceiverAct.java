package com.gaofei.app.act;

import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import androidx.annotation.Nullable;
import android.view.View;

import com.gaofei.app.R;
import com.gaofei.app.broadcast.BroadcastReceiverTest;
import com.gaofei.library.base.BaseAct;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.functions.Function;
import io.reactivex.internal.functions.ObjectHelper;
import io.reactivex.internal.operators.observable.ObservableZip;
import io.reactivex.plugins.RxJavaPlugins;

/**
 * Created by gaofei on 2017/9/1.
 */

public class BroadcastReceiverAct extends BaseAct {
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        IntentFilter intentFilter = new IntentFilter(BroadcastReceiverTest.ACTION);
        registerReceiver(new BroadcastReceiverTest(), intentFilter);
        setContentView( R.layout.act_broadcast_reveiver);
        findViewById(R.id.button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.addFlags(Intent.FLAG_RECEIVER_FOREGROUND);
                intent.setAction(BroadcastReceiverTest.ACTION);
                sendBroadcast(intent);
                Iterable<Observable<String>> list = new ArrayList<>();
                Observable.zip(list, new Function<Object[], Object>() {
                    @Override
                    public Object apply(Object[] objects) throws Exception {
                        return null;
                    }
                });

            }
        });

    }

}
