package com.jerey.music.ui.widget;

import android.content.Context;
import android.media.MediaPlayer;
import android.net.Uri;
import android.util.AttributeSet;
import android.view.View;
import android.widget.VideoView;

/**
 * 自定义播放控件
 * Created by Xiamin on 2017/5/6.
 */

public class GuideVideoView extends VideoView {
    public GuideVideoView(Context context) {
        super(context);
    }

    public GuideVideoView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public GuideVideoView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        setMeasuredDimension(View.MeasureSpec.getSize(widthMeasureSpec),View.MeasureSpec.getSize(heightMeasureSpec));
    }

    /**
     * 播放视频
     * @param uri
     */
    public void playVideo(Uri uri){
        if (uri == null){
            throw new IllegalArgumentException("uri can not null");
        }
        setVideoURI(uri);
        start();
        setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mp) {
                //设置循环播放
                mp.setLooping(true);
            }
        });

        setOnErrorListener(new MediaPlayer.OnErrorListener() {
            @Override
            public boolean onError(MediaPlayer mp, int what, int extra) {

                return true;
            }
        });
    }
}
