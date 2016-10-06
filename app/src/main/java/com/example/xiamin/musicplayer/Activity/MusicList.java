package com.example.xiamin.musicplayer.Activity;

/**
 * 此activity也已不用，为老版本activity
 */


import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.example.xiamin.musicplayer.R;

/**
 * Created by Xiamin on 2016/8/28.
 */
public class MusicList extends AppCompatActivity implements View.OnClickListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_musiclist);
        ActionBar actionBar = getSupportActionBar();
        /**显示回退键，默认为false*/
        actionBar.setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId())
        {
            case android.R.id.home:
            {
                finish();
                break;
            }
        }
    }
}
