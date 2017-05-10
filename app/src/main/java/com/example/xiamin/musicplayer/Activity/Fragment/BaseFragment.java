package com.example.xiamin.musicplayer.Activity.Fragment;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.View;

import com.example.xiamin.musicplayer.Activity.MusicActivity;
import com.example.xiamin.musicplayer.Service.MusicPlayService;

import butterknife.ButterKnife;

/**
 * Created by Xiamin on 2016/9/15.
 */
public abstract class BaseFragment extends Fragment {
    protected Handler mHandler;
    private boolean mResumed = false;
    private Context context;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mHandler = new Handler();
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this, view);
        mResumed = true;

        initView();
    }

    @Override
    public void onAttach(Context context) {
        this.context = context;
        //     mybindService();
        super.onAttach(context);


    }

    private MusicPlayService mMusicPlayService;
    private ServiceConnection connet = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
            Log.i("iii", "onServiceConnected");
            mMusicPlayService = ((MusicPlayService.Mybinder) iBinder).getservice();
            //    servicebinder.initPlayer();
        }

        //当启动源和service连接意外丢失时会调用
        @Override
        public void onServiceDisconnected(ComponentName componentName) {
            Log.i("iii", "onServiceDisconnected");
        }
    };

    /**
     * 绑定service 现如今不再调用
     */
    private void mybindService() {
        Intent intent = new Intent();
        intent.setClass(context, MusicPlayService.class);
        context.bindService(intent, connet, Context.BIND_AUTO_CREATE);
    }

    public abstract void initView();

    public boolean isResume() {
        return mResumed;
    }

    /**
     * 供子类调用 获取service
     * 现在是从activity直接获取service 不自己绑定service
     *
     * @return
     */
    protected MusicPlayService getPlayService() {
        mMusicPlayService = ((MusicActivity) getActivity()).getMusicService();

        if (mMusicPlayService == null) {
            Log.e("iii", "mMusicPlayService == null");
        }
        return mMusicPlayService;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
//        context.unbindService(connet);
    }

    public void log(String logs) {
        Log.i("iii", this.getClass().getName() + "->" + logs);
    }
}


















