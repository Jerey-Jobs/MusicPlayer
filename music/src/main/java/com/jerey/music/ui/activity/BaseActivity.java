package com.jerey.music.ui.activity;

import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

/**
 * Created by Xiamin on 2017/5/5.
 */

public class BaseActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //禁止横屏
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
    }

    /**
     * 显示snackbar
     * @param view
     */
    public void showSnackBar(View view, String string) {
        Snackbar.make(view, string, Snackbar.LENGTH_SHORT).show();
    }

    public void showSnackBar(View view, int resid) {
        Snackbar.make(view, resid, Snackbar.LENGTH_SHORT).show();
    }
}
