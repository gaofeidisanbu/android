package com.gaofei.app.act;

import android.os.Bundle;
import androidx.annotation.Nullable;
import android.widget.Button;
import android.widget.EditText;

import com.gaofei.app.R;
import com.gaofei.library.base.BaseAct;
import com.gaofei.library.utils.ToastManager;

/**
 * Created by gaofei on 12/01/2018.
 */

public class EditTextAct extends BaseAct {
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       setContentView(R.layout.act_edit_text);
        Button button1 = findViewById(R.id.button1);
        Button button2 = findViewById(R.id.button2);
        EditText edit = findViewById(R.id.edit);
        button1.setOnClickListener( v -> {
            edit.setOnClickListener( e -> {
                ToastManager.show("button1 onclick");
            });
            edit.setFocusable(false);
        });
        button2.setOnClickListener( v -> {
            edit.setFocusable(true);
            edit.setFocusableInTouchMode(true);
            edit.setOnClickListener(null);
            ToastManager.show(edit.isFocused()+"requestFocus =  "+edit.requestFocus());
        });

    }
}
