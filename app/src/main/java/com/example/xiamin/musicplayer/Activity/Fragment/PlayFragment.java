package com.example.xiamin.musicplayer.Activity.Fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.xiamin.musicplayer.Bean.MusicInfoBean;
import com.example.xiamin.musicplayer.CircleImage.CircleImageView;
import com.example.xiamin.musicplayer.R;

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

    CircleImageView mPlayImageView;
    MusicInfoBean mMusicBean;


    @Override
    public void initView() {
        mBackHome.setOnClickListener(this);

        mPlayImageView = (CircleImageView) getView().findViewById(R.id.fragment_play_circle_image);
        mPlayImageView.setOnClickListener(this);
        mPlayImageView.StartRotation();

//        mMusicBean = getPlayService().getPlayingMusic();
//        Glide.with(this)
//                .load(mMusicBean.getUri())
//                .error(R.drawable.default_cover)
//                .into(mPlayImageView);
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
        }
    }
}
