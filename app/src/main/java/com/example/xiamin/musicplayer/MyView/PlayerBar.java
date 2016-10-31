package com.example.xiamin.musicplayer.MyView;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.xiamin.musicplayer.Bean.MusicInfoBean;
import com.example.xiamin.musicplayer.R;
import com.example.xiamin.musicplayer.Service.MusicPlayService;

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
    private MusicInfoBean mMusicInfoBean;

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
        mybindService();
    }

    private MusicPlayService musicPlayService;
    private ServiceConnection connet = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
            Log.i("iii", "onServiceConnected");
            musicPlayService = ((MusicPlayService.Mybinder) iBinder).getservice();
            //    servicebinder.initPlayer();
        }

        //当启动源和service连接意外丢失时会调用
        @Override
        public void onServiceDisconnected(ComponentName componentName) {
            Log.i("iii", "onServiceDisconnected");
        }
    };

    private void mybindService() {
        Intent intent = new Intent();
        intent.setClass(mContext, MusicPlayService.class);
        mContext.bindService(intent, connet, Context.BIND_AUTO_CREATE);
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
                musicPlayService.playPause();
                if(MusicPlayService.getPlayingState())
                {
                    mImagePlayButton.setImageResource(R.drawable.ic_play_bar_btn_pause);
                }else {
                    mImagePlayButton.setImageResource(R.drawable.ic_play_bar_btn_play);
                }

                break;
            }
            case R.id.iv_play_bar_next:{
                Log.i("iii","iv_play_bar_next 点击事件，下一首");
                musicPlayService.next();
                setInfo(MusicPlayService.getMusicList().get(MusicPlayService.getPlayingMusicPosition()));
                break;
            }

            default:{
                Log.i("iii","onclick 去播放界面");
                if(playingFragmentListener != null)
                {
                    playingFragmentListener.ShowPlayingFragment(mMusicInfoBean);
                }
                else
                {
                    Log.i("iii","playingFragmentListener = null");
                }
            }

        }

    }

    public void setInfo(MusicInfoBean musicInfoBean)
    {
        mMusicInfoBean = musicInfoBean;
        Glide.with(mContext)
                .load(musicInfoBean.getCoverUri())
                .error(R.drawable.default_cover)
                .into(mImageCover);
        mMusicTitle.setText(musicInfoBean.getTitle());
        mMusicArtist.setText(musicInfoBean.getArtist());
        if(MusicPlayService.getPlayingState())
        {
            mImagePlayButton.setImageResource(R.drawable.ic_play_bar_btn_pause);
        }else {
            mImagePlayButton.setImageResource(R.drawable.ic_play_bar_btn_play);
        }
    }

    public interface ShowPlayingFragmentListener {
       public void ShowPlayingFragment(MusicInfoBean mMusicInfoBean);
    }
    ShowPlayingFragmentListener playingFragmentListener;

    public  void setShowPlayingFragmentListener(ShowPlayingFragmentListener listener){
        playingFragmentListener = listener;
    }

}
