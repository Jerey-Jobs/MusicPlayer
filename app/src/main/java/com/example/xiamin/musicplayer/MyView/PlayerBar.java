package com.example.xiamin.musicplayer.MyView;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.xiamin.musicplayer.R;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Xiamin on 2016/9/15.
 */
public class PlayerBar extends FrameLayout implements View.OnClickListener{


    @Bind(R.id.iv_play_bar_cover)
    ImageView mImageCover;
    @Bind(R.id.tv_play_bar_title)
    TextView mMusicTitle;
    @Bind(R.id.tv_play_bar_artist)
    TextView mMusicArtist;
    @Bind(R.id.iv_play_bar_play)
    ImageView mImagePlayButton;
    @Bind(R.id.iv_play_bar_next)
    ImageView mImagePlayNext;
    @Bind(R.id.pb_play_bar)
    ProgressBar mProgress;    //之前 绑定错id出错

    private Context mContext;

    public PlayerBar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        init(context, attrs);
    }

    public PlayerBar(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public PlayerBar(Context context) {
        super(context);
        init(context, null);
    }

    private void init(Context context, AttributeSet attrs) {
        mContext = context;
    }

    @Override
    protected void onFinishInflate() {
        ButterKnife.bind(this);
        super.onFinishInflate();

        mImagePlayButton.setClickable(true);
        mImagePlayNext.setClickable(true);
        mImageCover.setClickable(true);

        mImageCover.setOnClickListener(this);
        mImagePlayButton.setOnClickListener(this);
        mImagePlayNext.setOnClickListener(this);
        this.setClickable(true);
        this.setOnClickListener(this);
    }


    @Override
    public void onClick(View view) {

        switch (view.getId())
        {
            case R.id.iv_play_bar_play:
            {
                Log.i("iii","iv_play_bar_play 点击事件 暂停或者播放");

                break;
            }
            case R.id.iv_play_bar_next:{
                Log.i("iii","iv_play_bar_next 点击事件，下一首");
                break;
            }

            default:{
                Log.i("iii","onclick 去播放界面");
            }

        }

    }
}
