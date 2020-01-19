package com.gaofei.app.act;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.gaofei.app.R;
import com.gaofei.library.base.BaseAct;
import com.gaofei.library.base.BaseFragment;

/**
 * Created by gaofei on 2017/12/18.
 */

public class FragmentAct extends BaseAct {
    private MyFragmentFra mFragment;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_fragment);
        Button add = findViewById(R.id.add);
        Button hide = findViewById(R.id.hide);
        Button show = findViewById(R.id.show);
        Button detach = findViewById(R.id.detach);
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        mFragment = new MyFragmentFra();
        fragmentTransaction.replace(R.id.fragment, mFragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
//                fragmentTransaction.hide();
//                fragmentTransaction.add()
//                fragmentTransaction.commitAllowingStateLoss();
//                fragmentTransaction.commitNowAllowingStateLoss();
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
                mFragment = new MyFragmentFra();
                fragmentTransaction.replace(R.id.fragment, mFragment);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
            }
        });
        hide.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
                fragmentTransaction.hide(mFragment);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
            }
        });
        show.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
                fragmentTransaction.show(mFragment);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
            }
        });

        detach.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
                fragmentTransaction.detach(mFragment);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
            }
        });
    }

    public static class MyFragmentFra extends BaseFragment {
        @Override
        public void onAttach(Context context) {
            super.onAttach(context);
        }

        @Override
        public void onCreate(@Nullable Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
        }

        @Nullable
        @Override
        public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
            super.onCreateView(inflater,container,savedInstanceState);
            View binding = inflater.inflate( R.layout.fra_my_fragment, container, false);
            return binding;
        }

        @Override
        public void onResume() {
            super.onResume();
        }

        @Override
        public void onDestroyView() {
            super.onDestroyView();
        }

        @Override
        public void onDestroy() {
            super.onDestroy();
        }

        @Override
        public void onDetach() {
            super.onDetach();
        }
    }
}
