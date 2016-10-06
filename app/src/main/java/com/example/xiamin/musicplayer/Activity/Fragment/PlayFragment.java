package com.example.xiamin.musicplayer.Activity.Fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.xiamin.musicplayer.Bean.MusicInfoBean;
import com.example.xiamin.musicplayer.CircleImage.CircleImageView;
import com.example.xiamin.musicplayer.R;
import com.example.xiamin.musicplayer.Service.MusicPlayService;

import butterknife.Bind;

/**
 * Created by Xiamin on 2016/9/15.
 */
public class PlayFragment extends BaseFragment implements View.OnClickListener {
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
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_play, container, false);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_back: {
                log("onClick R.id.bar_iv_menu");

                /**
                 * 使用hide 完美解决销毁问题
                 */
                FragmentTransaction transaction = getFragmentManager()
                        .beginTransaction();
                transaction.setCustomAnimations(0, R.anim.fragment_slide_down)
                        .hide(this)
                        .commit();
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
                break;
            }
            case R.id.iv_prev: {
                break;
            }
            case R.id.iv_play: {
                getPlayService().playPause();

                if (MusicPlayService.getPlayingState()) {
                    mPlayButton.setSelected(true);
                    mPlayImageView.StartRotation();
                } else {
                    mPlayButton.setSelected(false);
                    mPlayImageView.StopRotation();
                }
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
        mBackGround.setAlpha(50);
        mTotalTime.setText("" + musicBean.getDuration() / 60 + ":" + mMusicBean.getDuration() % 60);
        mArtistText.setText(musicBean.getArtist());
        mTitleText.setText(musicBean.getTitle());
        mPlayButton.setSelected(true);
    }
}
