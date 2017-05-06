package com.jerey.music.ui.guide;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.jerey.loglib.LogTools;
import com.jerey.music.R;
import com.jerey.music.ui.activity.BaseActivity;
import com.jerey.music.ui.activity.HomeActivity;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class GuideActivity extends BaseActivity {
    public static final int GUIDE_COUNT = 3;
    @BindView(R.id.vp)
    ViewPager mViewPager;
    @BindView(R.id.iv1)
    ImageView mIv1;
    @BindView(R.id.iv2)
    ImageView mIv2;
    @BindView(R.id.iv3)
    ImageView mIv3;
    @BindView(R.id.bt_start)
    Button mBtStart;


    private ArrayList<Fragment> mFragments;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guide);
        LogTools.d("onCreate");
        ButterKnife.bind(this);
        initData();
    }

    private void initData() {
        mFragments = new ArrayList<Fragment>(GUIDE_COUNT);
        for (int i = 0; i < GUIDE_COUNT; i++) {
            GuideFragment guideFragment = new GuideFragment();
            Bundle bundle = new Bundle();
            bundle.putInt("index", i);
            guideFragment.setArguments(bundle);
            mFragments.add(guideFragment);
        }

        mViewPager.setOffscreenPageLimit(GUIDE_COUNT);
        mViewPager.setAdapter(new MyAdapter(getSupportFragmentManager()));
        mViewPager.addOnPageChangeListener(new MyPagerListener());
    }

    @OnClick(R.id.bt_start)
    public void onViewClicked() {
        startActivity(new Intent(GuideActivity.this, HomeActivity.class));
        finish();
    }

    class MyAdapter extends FragmentPagerAdapter {

        public MyAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragments.get(position);
        }

        @Override
        public int getCount() {
            return mFragments.size();
        }
    }

    public class MyPagerListener implements ViewPager.OnPageChangeListener {

        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        }

        @Override
        public void onPageSelected(int position) {
            mBtStart.setVisibility(View.GONE);
            mIv1.setImageResource(R.mipmap.dot_normal);
            mIv2.setImageResource(R.mipmap.dot_normal);
            mIv3.setImageResource(R.mipmap.dot_normal);

            if (position == 0) {
                mIv1.setImageResource(R.mipmap.dot_focus);
            } else if (position == 1) {
                mIv2.setImageResource(R.mipmap.dot_focus);
            } else {
                mIv3.setImageResource(R.mipmap.dot_focus);
                mBtStart.setVisibility(View.VISIBLE);
            }
        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }
    }

}
