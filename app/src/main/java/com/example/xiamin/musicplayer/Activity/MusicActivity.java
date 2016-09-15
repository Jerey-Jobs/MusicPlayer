package com.example.xiamin.musicplayer.Activity;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.support.design.widget.NavigationView;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.xiamin.musicplayer.R;
import com.example.xiamin.musicplayer.Service.MusicPlayService;

import butterknife.Bind;

/**
 * Created by Xiamin on 2016/9/15.
 */
public class MusicActivity extends BaseActivity {
    @Bind(R.id.drawer_layout)
    DrawerLayout drawerLayout;
    @Bind(R.id.navigation_view)
    NavigationView navigationView;
    @Bind(R.id.iv_menu)
    ImageView ivMenu;
    @Bind(R.id.iv_search)
    ImageView ivSearch;
    @Bind(R.id.tv_local_music)
    TextView tvLocalMusic;
    @Bind(R.id.tv_online_music)
    TextView tvOnlineMusic;
    @Bind(R.id.viewpager)
    ViewPager mViewPager;

    private View vNavigationHeader;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_music);


    }
















    private MusicPlayService servicebinder;
    private ServiceConnection connet = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
            Log.i("iii","onServiceConnected");
            servicebinder =  ((MusicPlayService.Mybinder)iBinder).getservice();
            servicebinder.initPlayer();
        }

        //当启动源和service连接意外丢失时会调用
        @Override
        public void onServiceDisconnected(ComponentName componentName) {
            Log.i("iii","onServiceDisconnected");
        }
    };
    private void bindService()
    {
        Intent intent = new Intent();
        intent.setClass(this, MusicPlayService.class);
        bindService(intent, connet, Context.BIND_AUTO_CREATE);
    }


}
