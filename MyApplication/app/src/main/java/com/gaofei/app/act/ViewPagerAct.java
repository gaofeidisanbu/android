package com.gaofei.app.act;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.gaofei.app.R;
import com.gaofei.library.base.BaseAct;
import com.gaofei.library.utils.LogUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by gaofei on 2017/12/16.
 */

public class ViewPagerAct extends BaseAct {
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView( R.layout.act_view_pager);
        ViewPager2 pager = findViewById(R.id.pager);
//        pager.setSaveFromParentEnabled(false);
        pager.setAdapter(new MyPagerAdapter(this));
    }

    class MyPagerAdapter extends FragmentStateAdapter {
        private List<Integer> list = new ArrayList<>();

        public MyPagerAdapter(@NonNull FragmentActivity fragmentActivity) {
            super(fragmentActivity);
            list.add(1);
            list.add(2);
        }

        @NonNull
        @Override
        public Fragment createFragment(int position) {
            MyFragment fragment = new MyFragment();
            Bundle bundle = new Bundle();
            bundle.putSerializable("key", position);
            fragment.setArguments(bundle);
            return fragment;
        }

        @Override
        public int getItemCount() {
            return list.size();
        }
    }

    public static class MyFragment extends Fragment {

        @Override
        public void onCreate(@Nullable Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            if (savedInstanceState != null) {
                int value = (int) savedInstanceState.getInt("key", -1);
                LogUtils.d("MyFragment", " value = "+value);
            }
        }

        @Nullable
        @Override
        public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
            View view = inflater.inflate(R.layout.fra_my_fragment, container, false);
            return view;
        }


    }

}
