package com.jerey.music.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.jerey.loglib.LogTools;
import com.jerey.music.R;
import com.jerey.music.ui.guide.GuideActivity;

public class WelcomActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcom);
        LogTools.d("onCreate");
        startActivity(new Intent(WelcomActivity.this, GuideActivity.class));
    }
}
