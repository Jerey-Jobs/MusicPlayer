package com.example.xiamin.musicplayer.Activity.Fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.xiamin.musicplayer.Activity.MusicActivity;
import com.example.xiamin.musicplayer.Bean.MusicInfoBean;
import com.example.xiamin.musicplayer.CircleImage.CircleImageView;
import com.example.xiamin.musicplayer.MVP.IPlayBar;
import com.example.xiamin.musicplayer.R;
import com.example.xiamin.musicplayer.Service.MusicPlayService;

import butterknife.Bind;

/**
 * Created by Xiamin on 2016/9/15.
 */
public class PlayFragment extends BaseFragment implements
        View.OnClickListener, GestureDetector.OnGestureListener {
    @Bind(R.id.iv_back)
    ImageView mBackHome;
    @Bind(R.id.tv_artist)
    TextView mArtistText;
    @Bind(R.id.tv_title)
    TextView mTitleText;
    @Bind(R.id.iv_play_page_bg)
    ImageView mBackGround;
    @Bind(R.id.tv_current_time)
    TextView m_CurrentTime;
    @Bind(R.id.sb_progress)
    SeekBar mProgress;
    @Bind(R.id.tv_total_time)
    TextView mTotalTime;
    @Bind(R.id.iv_play)
    ImageView mPlayButton;
    @Bind(R.id.iv_next)
    ImageView mNextButton;
    @Bind(R.id.iv_prev)
    ImageView mPrevButton;

    CircleImageView mPlayImageView;
    MusicInfoBean mMusicBean;
    MusicActivity.MyOnTouchListener onTouchListener;

    @Override
    public void initView() {
        mBackHome.setOnClickListener(this);
        mNextButton.setOnClickListener(this);
        mPlayButton.setOnClickListener(this);
        mPrevButton.setOnClickListener(this);

        mPlayImageView = (CircleImageView) getView().findViewById(R.id.fragment_play_circle_image);
        mPlayImageView.setOnClickListener(this);
        mPlayImageView.StartRotation();

        mMusicBean = getPlayService().getPlayingMusic();

        initUI(mMusicBean);

        onTouchListener = new MusicActivity.MyOnTouchListener() {
            @Override
            public boolean onTouch(MotionEvent ev) {
                gestureDetector.onTouchEvent(ev);
                return false;
            }
        };
        ((MusicActivity) getActivity()).registerMyOnTouchListener(onTouchListener);
    }

    View view;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        log("onCreateView");
        view = inflater.inflate(R.layout.fragment_play, container, false);
        return view;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_back: {
                log("onClick R.id.bar_iv_menu");
                hideThis();

                break;
            }
            case R.id.fragment_play_circle_image: {
                mMusicBean = getPlayService().getPlayingMusic();
                Glide.with(this)
                        .load(mMusicBean.getCoverUri())
                        .error(R.drawable.default_cover)
                        .into(mPlayImageView);
                break;
            }
            case R.id.iv_next: {
                getPlayService().next();

                mMusicBean = getPlayService().getPlayingMusic();
                initUI(mMusicBean);
                ((IPlayBar) getActivity()).setPlayBar(mMusicBean);
                break;
            }
            case R.id.iv_prev: {
                getPlayService().preMusic();

                mMusicBean = getPlayService().getPlayingMusic();
                initUI(mMusicBean);
                ((IPlayBar) getActivity()).setPlayBar(mMusicBean);
                break;
            }
            case R.id.iv_play: {
                play_pressed();
                break;
            }
        }
    }

    private void initUI(MusicInfoBean musicBean) {
        Glide.with(this)
                .load(musicBean.getCoverUri())
                .error(R.drawable.default_cover)
                .into(mPlayImageView);
        Glide.with(this)
                .load(musicBean.getCoverUri())
                .error(R.drawable.default_cover)
                .into(mBackGround);
        mBackGround.setAlpha(70);
        mTotalTime.setText("" + musicBean.getDuration() / 60 + ":" + mMusicBean.getDuration() % 60);
        mArtistText.setText(musicBean.getArtist());
        mTitleText.setText(musicBean.getTitle());
        mPlayButton.setSelected(true);
    }


    private void play_pressed() {
        //获取服务 触发暂停或者播放
        getPlayService().playPause();
        //根据状态 改变按钮的样式 并且设置转轮情况
        if (MusicPlayService.getPlayingState()) {
            mPlayButton.setSelected(true);
            mPlayImageView.StartRotation();
        } else {
            mPlayButton.setSelected(false);
            mPlayImageView.StopRotation();
        }
        //再跟新activity中的按钮样式
        mMusicBean = getPlayService().getPlayingMusic();
        ((IPlayBar) getActivity()).setPlayBar(mMusicBean);
    }

    private void hideThis() {
        /**
         * 使用hide 完美解决销毁问题
         */
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.setCustomAnimations(0, R.anim.fragment_slide_down)
                .hide(this)
                .commit();
    }


    @Override
    public void onResume() {
        super.onResume();
        log("onResume");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        log("onDestroy");
    }

    @Override
    public void onPause() {
        super.onPause();
        log("onPause");
    }

    @Override
    public void onStop() {
        super.onStop();
        log("onStop");
    }

    GestureDetector gestureDetector = new GestureDetector(getActivity(), this);


    @Override
    public boolean onDown(MotionEvent e) {
 //       Log.i("iii", "GestureDetector: " + e.getX() + "-" + e.getY());
        return false;
    }

    @Override
    public void onShowPress(MotionEvent e) {

    }

    @Override
    public boolean onSingleTapUp(MotionEvent e) {
        return false;
    }

    @Override
    public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
        return false;
    }

    @Override
    public void onLongPress(MotionEvent e) {

    }

    @Override
    public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
        if (e2.getY() - e1.getY() > 150) {
            Log.i("iii", "GestureDetector: " + e2.getY() + "-" + e1.getY());
            Log.i("iii", "ondown");
            /**
             * 若不unregister 则第二次出现空指针异常 因为每次都是新fragment
             */
            ((MusicActivity) getActivity()).unregisterMyOnTouchListener(onTouchListener);
            hideThis();
        }
        return false;
    }
}
