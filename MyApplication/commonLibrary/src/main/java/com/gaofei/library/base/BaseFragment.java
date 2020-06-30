package com.gaofei.library.base;

import android.content.Context;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.gaofei.library.utils.LogUtils;

/**
 * Created by gaofei on 2017/6/13.
 */

public class BaseFragment extends Fragment{
    private final static String COMMON_TAG = "gf_fragment:";
    public String TAG;
    public String mClassName;
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mClassName = getClass().getSimpleName()+" hashCode = " + this.hashCode();
        TAG = COMMON_TAG + mClassName;
        LogUtils.d(TAG, "------- onAttach -----------");
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LogUtils.d(TAG, "------- onCreate savedInstanceState =  " + (savedInstanceState != null ? savedInstanceState.toString() : "null"));
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        LogUtils.d(TAG, "------- onCreateView -----------");
        return null;
    }

    @Override
    public void onResume() {
        super.onResume();
        LogUtils.d(TAG, "------- onResume -----------");
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        LogUtils.d(TAG, "------- onDestroyView -----------");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        LogUtils.d(TAG, "------- onDestroy -----------");
    }

    @Override
    public void onDetach() {
        super.onDetach();
        LogUtils.d(TAG, "------- onDetach -----------");
    }
}
