package com.example.xiamin.musicplayer.Activity.Fragment;

import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.View;

import com.example.xiamin.musicplayer.Service.MusicPlayService;

import butterknife.ButterKnife;

/**
 * Created by Xiamin on 2016/9/15.
 */
public abstract class BaseFragment extends Fragment {
    private MusicPlayService mMusicPlayService;
    protected Handler mHandler;
    private boolean mResumed = false;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mHandler = new Handler();
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this, view);
        initView();
        mResumed = true;
    }

    public abstract void initView();

    public boolean isResume() {
        return mResumed;
    }
}
