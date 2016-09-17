package com.example.xiamin.musicplayer.Activity;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.xiamin.musicplayer.Activity.Fragment.LocalMusicFragment;
import com.example.xiamin.musicplayer.Activity.Fragment.PlayFragment;
import com.example.xiamin.musicplayer.Bean.MusicInfoBean;
import com.example.xiamin.musicplayer.MVP.IPlayBar;
import com.example.xiamin.musicplayer.MyView.PlayerBar;
import com.example.xiamin.musicplayer.R;
import com.example.xiamin.musicplayer.Service.MusicPlayService;
import com.example.xiamin.musicplayer.adapter.FragmentAdapter;

import butterknife.Bind;

/**
 * Created by Xiamin on 2016/9/15.
 */
public class MusicActivity extends BaseActivity implements View.OnClickListener,
        ViewPager.OnPageChangeListener
        ,IPlayBar{
    @Bind(R.id.drawer_layout)
    DrawerLayout mDrawerLayout;
    @Bind(R.id.navigation_view)
    NavigationView mDavigationView;
    @Bind(R.id.iv_menu)
    ImageView mvMenu;
    @Bind(R.id.iv_search)
    ImageView mIvSearch;
    @Bind(R.id.tv_local_music)
    TextView mTvLocalMusic;
    @Bind(R.id.tv_online_music)
    TextView mTvOnlineMusic;
    @Bind(R.id.viewpager)
    ViewPager mViewPager;

    PlayerBar mPlayBar;

    private View vNavigationHeader;
    private LocalMusicFragment mLocalMusicFragment;
    private PlayFragment mPlayFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_music);
        initView();
        bindService();
    }

    private void initView() {
        mPlayBar = (PlayerBar) findViewById(R.id.fl_play_bar);

        mViewPager.setOnPageChangeListener(this);
        mDrawerLayout.setOnClickListener(this);
        mIvSearch.setOnClickListener(this);
        mTvLocalMusic.setOnClickListener(this);
        mTvOnlineMusic.setOnClickListener(this);
        mvMenu.setOnClickListener(this);

        mLocalMusicFragment = new LocalMusicFragment();
        FragmentAdapter adapter = new FragmentAdapter(getSupportFragmentManager());
        adapter.addFragment(mLocalMusicFragment);
        mViewPager.setAdapter(adapter);
    }

    private MusicPlayService servicebinder;
    private ServiceConnection connet = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
            Log.i("iii", "onServiceConnected");
            servicebinder = ((MusicPlayService.Mybinder) iBinder).getservice();
        //    servicebinder.initPlayer();
        }

        //当启动源和service连接意外丢失时会调用
        @Override
        public void onServiceDisconnected(ComponentName componentName) {
            Log.i("iii", "onServiceDisconnected");
        }
    };

    private void bindService() {
        Intent intent = new Intent();
        intent.setClass(this, MusicPlayService.class);
        bindService(intent, connet, Context.BIND_AUTO_CREATE);
    }

    public MusicPlayService getMusicService()
    {
        return servicebinder;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_menu: {
                Log.i("iii","点击侧滑按钮");
                mDrawerLayout.openDrawer(GravityCompat.START);
                break;
            }
            case R.id.iv_search: {
                Log.i("iii","点击搜索按钮");
                break;
            }
            case R.id.tv_local_music: {
                Log.i("iii","点击本地音乐");
                // mViewPager.setCurrentItem(0);
                break;
            }
            case R.id.tv_online_music: {
                Log.i("iii","点击在线音乐");
                //    mViewPager.setCurrentItem(1);
                break;
            }
        }
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        if (position == 0) {
            mTvLocalMusic.setSelected(true);
            mTvOnlineMusic.setSelected(false);
        } else if (position == 1) {
            mTvLocalMusic.setSelected(false);
            mTvOnlineMusic.setSelected(true);
        }
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbindService(connet);
    }


    @Override
    public void setPlayBar(MusicInfoBean musicInfoBean) {
        mPlayBar.setInfo(musicInfoBean);
    }

    @Override
    public void onBackPressed() {
        if (mDrawerLayout.isDrawerOpen(GravityCompat.START)) {
            mDrawerLayout.closeDrawers();
            return;
        }
        moveTaskToBack(false);
    //    super.onBackPressed();
    }

}
