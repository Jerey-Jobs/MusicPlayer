package com.example.xiamin.musicplayer.Service;

import android.app.Service;
import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Binder;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;

import com.example.xiamin.musicplayer.Bean.MusicInfoBean;
import com.example.xiamin.musicplayer.utils.Actions;
import com.example.xiamin.musicplayer.utils.MusicScanUntils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Xiamin on 2016/9/8.
 */
public class MusicPlayService extends Service implements MediaPlayer.OnPreparedListener,MediaPlayer.OnCompletionListener
        ,MediaPlayer.OnBufferingUpdateListener {

    private MediaPlayer mediaPlayer =  new MediaPlayer();
    private static List<MusicInfoBean> sMusicList = new ArrayList<MusicInfoBean>();
    //private String path = "http://ws.stream.qqmusic.qq.com/104779440.m4a?fromtag=46";



    @Nullable
    @Override
    public IBinder onBind(Intent intent) {

        return new Mybinder();
    }

    @Override
    public void onCreate() {
        Log.i("iii","Service onCreate");
        if(mediaPlayer.isPlaying()){
            stopPlayer();
        }
        mediaPlayer.setOnCompletionListener(this);
        updateMusicList();
        super.onCreate();
    }

    @Override
    public void onPrepared(MediaPlayer mediaPlayer) {
        mediaPlayer.start();
        Log.e("iii", "mediaPlayer onPrepared");
    }

    @Override
    public void onBufferingUpdate(MediaPlayer mediaPlayer, int i) {

    }

    /**
     * 一首歌曲播放完毕了，该播放下一首
     * @param mediaPlayer
     */
    @Override
    public void onCompletion(MediaPlayer mediaPlayer) {

    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.i("iii","service onStartCommand");
        switch (intent.getAction()) {
            case Actions.ACTION_MEDIA_PLAY_PAUSE:
            //    playPause();
                break;
            case Actions.ACTION_MEDIA_NEXT:
             //   next();
                break;
            case Actions.ACTION_MEDIA_PREVIOUS:
            //    prev();
                break;
        }
        return super.onStartCommand(intent, flags, startId);
    }

    public static List<MusicInfoBean> getMusicList()
    {
        return sMusicList;
    }

    /**
     * 每次启动时扫描音乐
     */
    public void updateMusicList() {
        MusicScanUntils.scanMusic(this, getMusicList());
        if (getMusicList().isEmpty()) {
            Log.i("iii","getMusicList().isEmpty()");
            return;
        }
        for(MusicInfoBean k:sMusicList)
        {
            Log.i("iii",k.getTitle());
        }
    }



























    public void initPlayer()
    {
        if(mediaPlayer.isPlaying()){
            stopPlayer();
        }
        mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);// 设置媒体流类型
        mediaPlayer.setOnBufferingUpdateListener(this);
        mediaPlayer.setOnPreparedListener(this);

       // playUrl(path);
        Log.i("iii","service initPlayer");
    }


    /**
     *
     * @param url
     *            url地址
     */
    public void playUrl(String url) {
        try {
            mediaPlayer.reset();
            mediaPlayer.setDataSource(url); // 设置数据源
            mediaPlayer.prepare(); // prepare自动播放
            mediaPlayer.setOnPreparedListener(this);//注册一个监听器
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        } catch (SecurityException e) {
            e.printStackTrace();
        } catch (IllegalStateException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }






    public void stopPlayer()
    {
        if(mediaPlayer != null) {
            mediaPlayer.stop();
            try {
                mediaPlayer.prepare(); // 在调用stop后如果需要再次通过start进行播放,需要之前调用prepare函数
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        if(mediaPlayer != null){
            mediaPlayer.stop();
            mediaPlayer.release();
        }
    }

    public class Mybinder extends Binder
    {
        public MusicPlayService getservice()
        {
            return MusicPlayService.this;
        }
    }
}
