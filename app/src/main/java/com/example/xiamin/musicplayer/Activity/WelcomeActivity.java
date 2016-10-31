package com.example.xiamin.musicplayer.Activity;

import android.Manifest;
import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;

import com.example.xiamin.musicplayer.R;
import com.example.xiamin.musicplayer.utils.PermissionsChecker;

/**
 * Created by Xiamin on 2016/8/27.
 */
public class WelcomeActivity extends AppCompatActivity {

    private static final int REQUEST_CODE = 111; // 请求码
    // 所需的全部权限
    static final String[] PERMISSIONS = new String[]{
            //       Manifest.permission.MEDIA_CONTENT_CONTROL,
            Manifest.permission.ACCESS_NETWORK_STATE,
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.INTERNET
    };
    PermissionsChecker permissionsChecker;
    private View view;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        view = View.inflate(this, R.layout.welcome, null);
        setContentView(view);
        PermissionCheck();
    }

    private void PermissionCheck() {
        permissionsChecker = new PermissionsChecker(this);
        if (permissionsChecker.lacksPermissions(PERMISSIONS)) {
            Log.i("iii", "缺少权限，跳转权限申请界面");
            Intent intent = new Intent(this, PermissionActivity.class);
            intent.putExtra(PermissionActivity.PERMISSION_REQUEST_FLAG, PERMISSIONS);
            startActivityForResult(intent, REQUEST_CODE);
        } else {
            startToMain();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        /**
         * 若权限拒绝
         */
        if (requestCode == REQUEST_CODE && resultCode == PermissionActivity.PERMISSIONS_DENIED) {
            finish();
        } else if (requestCode == REQUEST_CODE && resultCode == PermissionActivity.PERMISSIONS_GRANTED) {
            startToMain();
        }
    }

    /**
     * 开启动画进入主界面
     */
    private void startToMain() {
        /**
         * 解决欢迎界面上面出现通知栏的问题，伪全屏
         */
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            // LOLLIPOP解决方案
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            getWindow().setStatusBarColor(Color.TRANSPARENT);
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            // KITKAT解决方案
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }
        /**
         * 改成放大的属性动画
         */
        ObjectAnimator animtorAlpha = ObjectAnimator.ofFloat(view, "alpha", 0.1f, 1f);
        ObjectAnimator animatorX = ObjectAnimator.ofFloat(view, "scaleX", 1f, 1.0f);
        ObjectAnimator animatorY = ObjectAnimator.ofFloat(view, "scaleY", 1f, 1.0f);

        AnimatorSet set = new AnimatorSet();
        set.setDuration(1000).play(animatorX).with(animatorY).with(animtorAlpha);
        set.start();

        set.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {

                startActivity(new Intent(WelcomeActivity.this, MusicActivity.class));

                overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
                WelcomeActivity.this.finish();
            }
        });
    }
}
