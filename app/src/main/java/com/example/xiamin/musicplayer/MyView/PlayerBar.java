package com.example.xiamin.musicplayer.MyView;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

/**
 * Created by Xiamin on 2016/9/15.
 */
public class PlayerBar extends FrameLayout {
    private Context mContext;

    private ImageView mImageCover;
    private TextView mMusicTitle;
    private TextView mMusicArtist;
    private ImageView mImagePlayButton;
    private ImageView getmImagePlayNext;
    private ProgressBar mProgress;

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
        super.onFinishInflate();
    }


}
