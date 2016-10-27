package com.example.xiamin.musicplayer.Activity;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.util.Log;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.xiamin.musicplayer.Activity.Fragment.LocalMusicFragment;
import com.example.xiamin.musicplayer.Activity.Fragment.PlayFragment;
import com.example.xiamin.musicplayer.Activity.Fragment.SongListFragment;
import com.example.xiamin.musicplayer.Bean.MusicInfoBean;
import com.example.xiamin.musicplayer.MVP.IPlayBar;
import com.example.xiamin.musicplayer.MyView.PlayerBar;
import com.example.xiamin.musicplayer.R;
import com.example.xiamin.musicplayer.Service.MusicPlayService;
import com.example.xiamin.musicplayer.adapter.FragmentAdapter;
import com.example.xiamin.musicplayer.utils.ScreenUtils;

import java.util.ArrayList;

import butterknife.Bind;

/**
 * Created by Xiamin on 2016/9/15.
 */
public class MusicActivity extends BaseActivity implements View.OnClickListener,
        ViewPager.OnPageChangeListener
        , IPlayBar, PlayerBar.ShowPlayingFragmentListener
        , NavigationView.OnNavigationItemSelectedListener {
    @Bind(R.id.drawer_layout)
    DrawerLayout mDrawerLayout;
    @Bind(R.id.navigation_view)
    NavigationView mNavigationView;
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
    private SongListFragment mSongListFragment;
    private PlayFragment mPlayFragment;
    private static final String TAG = "MusicActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_music);
        initView();
        bindService();
    }

    private void initView() {
        mPlayBar = (PlayerBar) findViewById(R.id.fl_play_bar);

        mPlayBar.setShowPlayingFragmentListener(this);

        mViewPager.setOnPageChangeListener(this);
        mDrawerLayout.setOnClickListener(this);
        mIvSearch.setOnClickListener(this);
        mTvLocalMusic.setOnClickListener(this);
        mTvOnlineMusic.setOnClickListener(this);
        mvMenu.setOnClickListener(this);
        mNavigationView.setNavigationItemSelectedListener(this);
        ImageView imageView = new ImageView(this);
        imageView.setLayoutParams(new DrawerLayout.LayoutParams(DrawerLayout.LayoutParams.MATCH_PARENT
                , ScreenUtils.dp2px(200f)));
        imageView.setImageResource(R.drawable.jay);
        mNavigationView.addHeaderView(imageView);

        mLocalMusicFragment = new LocalMusicFragment();
        mSongListFragment = new SongListFragment();
        FragmentAdapter adapter = new FragmentAdapter(getSupportFragmentManager());
        adapter.addFragment(mLocalMusicFragment);
        adapter.addFragment(mSongListFragment);
        mViewPager.setAdapter(adapter);


    }

    private MusicPlayService servicebinder;
    private ServiceConnection connet = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
            Log.i("TAG", "onServiceConnected");
            servicebinder = ((MusicPlayService.Mybinder) iBinder).getservice();
            //    servicebinder.initPlayer();
        }

        //当启动源和service连接意外丢失时会调用
        @Override
        public void onServiceDisconnected(ComponentName componentName) {
            Log.i("TAG", "onServiceDisconnected");
        }
    };

    private void bindService() {
        Intent intent = new Intent();
        intent.setClass(this, MusicPlayService.class);
        bindService(intent, connet, Context.BIND_AUTO_CREATE);
    }

    public MusicPlayService getMusicService() {
        return servicebinder;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_menu: {
                Log.i("TAG", "点击侧滑按钮");
                mDrawerLayout.openDrawer(GravityCompat.START);
                break;
            }
            case R.id.iv_search: {
                Log.i("TAG", "点击搜索按钮");
                break;
            }
            case R.id.tv_local_music: {
                Log.i("TAG", "点击本地音乐");
                mViewPager.setCurrentItem(0);
                break;
            }
            case R.id.tv_online_music: {
                Log.i("TAG", "点击在线音乐");
                mViewPager.setCurrentItem(1);
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
        if (mIsPlayingFragment == true && mPlayFragment != null) {
            hidePlayingFragment();
            return;
        }

        if (mDrawerLayout.isDrawerOpen(GravityCompat.START)) {
            mDrawerLayout.closeDrawers();
            return;
        }
        moveTaskToBack(false);
        //    super.onBackPressed();
    }


    private boolean mIsPlayingFragment;

    @Override
    public void ShowPlayingFragment(MusicInfoBean mMusicInfoBean) {
        //每次点击都得刷新fragment，而hide和show不走生命周期
        mPlayFragment = new PlayFragment();

        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction()
                .setCustomAnimations(R.anim.fragment_slide_up, 0)
                .replace(android.R.id.content, mPlayFragment)
                .show(mPlayFragment)
                .commit();
        mIsPlayingFragment = true;
    }

    private void hidePlayingFragment() {
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.setCustomAnimations(0, R.anim.fragment_slide_down);
        ft.hide(mPlayFragment);
        ft.commit();
        mIsPlayingFragment = false;
    }

    @Override
    public boolean onNavigationItemSelected(final MenuItem item) {
        mDrawerLayout.closeDrawers();
        /*对于NavigationItem 当被按下后会呈现暗色，我们需要手动将其置为可按的状态*/
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                item.setChecked(false);
            }
        }, 500);
        switch (item.getItemId()) {
            case R.id.action_setting:
                Toast.makeText(this, "setting", Toast.LENGTH_SHORT).show();
                return true;
            case R.id.action_night:
                Toast.makeText(this, "action_night", Toast.LENGTH_SHORT).show();
                break;
            case R.id.action_timer:
                Toast.makeText(this, "action_timer", Toast.LENGTH_SHORT).show();
                return true;
            case R.id.action_exit:
                getMusicService().stopPlayer();
                for (Activity k : MusicPlayService.getActivityStack()) {
                    k.finish();
                }
                return true;
            case R.id.action_about:
                return true;
        }
        return false;
    }

//    private void hideOnlineFragment() {
//        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
//        ft.setCustomAnimations(0, R.anim.fragment_slide_down);
//        ft.hide(mPlayFragment);
//        ft.commit();
//        mIsPlayingFragment = false;
//    }


    /**
     * fragment触摸事件
     */
    private ArrayList<MyOnTouchListener> onTouchListeners = new ArrayList<MyOnTouchListener>(
            10);

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        for (MyOnTouchListener listener : onTouchListeners) {
            if(listener != null) {
                listener.onTouch(ev);
            }
        }
        return super.dispatchTouchEvent(ev);
    }

    public void registerMyOnTouchListener(MyOnTouchListener myOnTouchListener) {
        onTouchListeners.add(myOnTouchListener);
    }
    public void unregisterMyOnTouchListener(MyOnTouchListener myOnTouchListener) {
        onTouchListeners.remove(myOnTouchListener) ;
    }
    public interface MyOnTouchListener {
        public boolean onTouch(MotionEvent ev);
    }
}
