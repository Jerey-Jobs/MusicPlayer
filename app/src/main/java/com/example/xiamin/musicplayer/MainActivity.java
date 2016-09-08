package com.example.xiamin.musicplayer;

import android.app.Service;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;

import com.example.xiamin.musicplayer.CircleImage.CircleImageView;
import com.example.xiamin.musicplayer.MVP.IMainView;
import com.example.xiamin.musicplayer.Service.MusicPlayService;

public class MainActivity extends AppCompatActivity implements View.OnClickListener,
        SeekBar.OnSeekBarChangeListener,IMainView{

    CircleImageView mCircleImageView;
    TextView mMusicTitle;
    TextView mMusicArtist;
    TextView mMusicPassTime;
    TextView mMusicWholeTime;
    SeekBar mSeekBar;
    Button mPreMusic;
    Button mPlayOrStop;
    Button mNextMusic;


    private MusicPlayService servicebinder;
    private ServiceConnection connet = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
            Log.i("iii","onServiceConnected");
            servicebinder =  ((MusicPlayService.Mybinder)iBinder).getservice();
            servicebinder.initPlayer();
        }

        //当启动源和service连接意外丢失时会调用
        @Override
        public void onServiceDisconnected(ComponentName componentName) {
            Log.i("iii","onServiceDisconnected");
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initView();


        Intent bindintent = new Intent(MainActivity.this,MusicPlayService.class);
    //    startService(bindintent);

        bindService(bindintent,connet, Service.BIND_AUTO_CREATE);
        Log.i("iii","Service create");

    }

    private void initView()
    {
        mCircleImageView = (CircleImageView) findViewById(R.id.main_circleimage);
        mMusicTitle = (TextView) findViewById(R.id.tv_main_title);
        mMusicArtist = (TextView) findViewById(R.id.tv_main_artist);
        mMusicPassTime = (TextView) findViewById(R.id.tv_pass_time);
        mMusicWholeTime = (TextView) findViewById(R.id.tv_main_time);
        mSeekBar = (SeekBar) findViewById(R.id.progressbar);
        mPreMusic = (Button) findViewById(R.id.pre_music);
        mPlayOrStop = (Button) findViewById(R.id.pause_music);
        mNextMusic = (Button) findViewById(R.id.next_music);

        mPreMusic.setOnClickListener(this);
        mPlayOrStop.setOnClickListener(this);
        mNextMusic.setOnClickListener(this);
    }


    @Override
    public void onClick(View view) {
        switch (view.getId())
        {
            case R.id.pre_music:{

                break;
            }
            case R.id.pause_music:{

                break;
            }
            case R.id.next_music:{

                break;
            }
        }
    }

    @Override
    public void onProgressChanged(SeekBar seekBar, int i, boolean b) {

    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {

    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {

    }


    @Override
    public void setMusicTitle(String name) {
        mMusicTitle.setText(name);
    }

    @Override
    public void setArtistName(String name) {
        mMusicArtist.setText(name);
    }

    @Override
    public void setSeekBarProgress(int progress) {
        mSeekBar.setProgress(progress);
    }

    @Override
    public void setMusicPassTime(String time) {
        mMusicPassTime.setText(time);
    }

    @Override
    public void setMusicWholeTime(String time) {
        mMusicPassTime.setText(time);
    }
}
