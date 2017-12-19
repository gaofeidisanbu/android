package com.gaofei.app.act;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.gaofei.app.R;
import com.gaofei.app.databinding.ActFragmentBinding;
import com.gaofei.app.databinding.FraMyFragmentBinding;
import com.gaofei.library.base.BaseAct;
import com.gaofei.library.base.BaseFragment;

/**
 * Created by gaofei on 2017/12/18.
 */

public class FragmentAct extends BaseAct {
    private ActFragmentBinding mBinding;
    private MyFragmentFra mFragment;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this, R.layout.act_fragment);
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        mFragment = new MyFragmentFra();
        fragmentTransaction.replace(R.id.fragment, mFragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
//                fragmentTransaction.hide();
//                fragmentTransaction.add()
//                fragmentTransaction.commitAllowingStateLoss();
//                fragmentTransaction.commitNowAllowingStateLoss();
        mBinding.add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
                mFragment = new MyFragmentFra();
                fragmentTransaction.replace(R.id.fragment, mFragment);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
            }
        });
        mBinding.hide.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
                fragmentTransaction.hide(mFragment);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
            }
        });
        mBinding.show.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
                fragmentTransaction.show(mFragment);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
            }
        });

        mBinding.detach.setOnClickListener(new View.OnClickListener() {
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
            FraMyFragmentBinding binding = DataBindingUtil.inflate(inflater, R.layout.fra_my_fragment, container, false);
            return binding.getRoot();
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
