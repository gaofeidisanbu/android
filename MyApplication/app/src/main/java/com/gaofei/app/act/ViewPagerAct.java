package com.gaofei.app.act;

import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.gaofei.app.R;
import com.gaofei.library.base.BaseAct;

/**
 * Created by gaofei on 2017/12/16.
 */

public class ViewPagerAct extends BaseAct {
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView( R.layout.act_view_pager);
        ViewPager pager = findViewById(R.id.pager);
        pager.setAdapter(new MyPagerAdapter());
    }

    class MyPagerAdapter extends PagerAdapter {

        @Override
        public int getCount() {
            return 2;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            TextView textView = new TextView(container.getContext());
            textView.setBackgroundResource((position + 1) % 2 == 0 ? R.color.yc_red2 : R.color.yc_blue2);
            textView.setText(position + "");
            container.addView(textView);
            return textView;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }
    }

}
