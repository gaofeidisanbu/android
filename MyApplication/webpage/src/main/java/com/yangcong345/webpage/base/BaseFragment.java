package com.yangcong345.webpage.base;

import android.app.Activity;
import android.os.Bundle;
import androidx.annotation.CallSuper;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import android.view.View;


/**
 * Author: xumin
 * Date: 2017/4/26
 */
public abstract class BaseFragment extends Fragment {

    protected static Interceptor interceptor;

    public interface Interceptor {
        void onPageStart(BaseFragment baseFragment);

        void onPageEnd(BaseFragment baseFragment);
    }


    private boolean mEntered = false;

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);

        if (isResumed()) {
            if (!mEntered && isVisibleToUser) {
                mEntered = true;
                onPageStart();
            } else if (mEntered && !isVisibleToUser) {
                onPageEnd();
                mEntered = false;
            }
        }
    }


    @CallSuper
    @Override
    public void onResume() {
        super.onResume();
        if (!mEntered && getUserVisibleHint()) {
            mEntered = true;
            onPageStart();
        }
    }

    @CallSuper
    @Override
    public void onPause() {
        if (mEntered) {
            onPageEnd();
            mEntered = false;
        }
        super.onPause();
    }

    public static void setInterceptor(Interceptor interceptor) {
        BaseFragment.interceptor = interceptor;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
    }

    public void onPageStart() {
        if (interceptor != null)
            interceptor.onPageStart(this);
    }

    public void onPageEnd() {
        if (interceptor != null)
            interceptor.onPageEnd(this);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    public boolean isEntered() {
        return mEntered;
    }
}
