package com.gaofei.app.act;

import android.databinding.BaseObservable;
import android.databinding.Bindable;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.gaofei.app.BR;
import com.gaofei.app.R;
import com.gaofei.app.act.common.UserInfo;
import com.gaofei.app.databinding.ActDataBindingBinding;
import com.gaofei.library.base.BaseAct;

/**
 * Created by gaofei on 30/01/2018.
 */

public class DataBindingAct extends BaseAct {
    private ActDataBindingBinding mBinding;
    private UserInfo mUserInfo;
    private DataBindingViewModel mViewModel;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this, R.layout.act_data_binding);
        mUserInfo = new UserInfo("高飞", "高飞第三部");
        mViewModel = new DataBindingViewModel();
        mBinding.setUser(mUserInfo);
        mBinding.setViewModel(mViewModel);
        mBinding.button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mBinding.setUser(new UserInfo("冯昂","傻"));
                mViewModel.contact.setName("高飞升级");

            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    public static class DataBindingViewModel {
        public final ObservableContact contact = new ObservableContact("高飞3","13071135067");

    }

    public static class ObservableContact extends BaseObservable {
        private String mName;
        private String mPhone;

        public ObservableContact(String name, String phone) {
            mName = name;
            mPhone = phone;
        }

        @Bindable
        public String getName() {
            return mName;
        }

        public void setName(String name) {
            mName = name;
            notifyPropertyChanged(BR.name);
        }

        @Bindable
        public String getPhone() {
            return mPhone;
        }

        public void setPhone(String phone) {
            mPhone = phone;
            notifyPropertyChanged(BR.phone);
        }
    }
}
