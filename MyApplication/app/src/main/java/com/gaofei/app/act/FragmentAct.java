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

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this, R.layout.act_fragment);
        mBinding.button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.fragment, new MyFragmentFra());
//                fragmentTransaction.hide();
//                fragmentTransaction.add()
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
//                fragmentTransaction.commitNow();
//                fragmentTransaction.commitAllowingStateLoss();
//                fragmentTransaction.commitNowAllowingStateLoss();
                // 状态保存

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
