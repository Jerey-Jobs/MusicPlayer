package com.example.xiamin.musicplayer.Activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.example.xiamin.musicplayer.utils.PermissionsChecker;

/**
 * Created by Xiamin on 2016/10/23.
 * 权限activity，负责申请权限，没有view
 */
public class PermissionActivity extends AppCompatActivity {
    public static final int PERMISSIONS_GRANTED = 0; // 权限授权
    public static final int PERMISSIONS_DENIED = 1; // 权限拒绝
    public static final String PERMISSION_REQUEST_FLAG = "permission";
    private static final int PERMISSION_REQUEST_CODE = 4;

    private static final String TAG = "PermissionActivity";

    private PermissionsChecker mChecker; // 权限检测器
    private static boolean isRequireCheck;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getIntent() == null || !getIntent().hasExtra(PERMISSION_REQUEST_FLAG)) {
            throw new RuntimeException("PermissionsActivity需要使用静态startActivityForResult方法启动!");
        }
        mChecker = new PermissionsChecker(this);
        isRequireCheck = true;
        Log.i("TAG", "onCreate ");
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (isRequireCheck == false) {
            return;
        }
        String[] permissions = getIntent().getStringArrayExtra(PERMISSION_REQUEST_FLAG);
        if (mChecker.lacksPermissions(permissions)) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                Log.i("TAG", "onResume() 请求权限");
                isRequireCheck = true;
                requestPermissions(permissions, PERMISSION_REQUEST_CODE); // 请求权限
            }
        } else {
            allPermissionsGranted(); // 全部权限都已获取
        }
    }


    /**
     * 全部权限均已获取
     */
    private void allPermissionsGranted() {
        setResult(PERMISSIONS_GRANTED);
        finish();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == PERMISSION_REQUEST_CODE && hasAllPermissionsGranted(grantResults)) {
            isRequireCheck = true;
            allPermissionsGranted();
        } else {
            isRequireCheck = false;
            showMissingPermissionDialog();
        }
    }

    /**
     * 检查是否所有的权限都已经被获取
     * 全部获取返回ture
     * 没有返回false
     *
     * @param grantResults
     * @return
     */
    private boolean hasAllPermissionsGranted(@NonNull int[] grantResults) {
        for (int grantResult : grantResults) {
            if (grantResult == PackageManager.PERMISSION_DENIED) {
                return false;
            }
        }
        return true;
    }

    /**
     * 显示缺少权限
     */
    private void showMissingPermissionDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this)
                .setTitle("帮助！")
                .setMessage("缺少权限，缺少权限软件将不能正常工作，请在设置中打开所有权限！")
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                        intent.setData(Uri.parse("package:" + getPackageName()));
                        startActivity(intent);
                        finish();
                    }
                })
                .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        setResult(PERMISSIONS_DENIED);
                        finish();
                    }
                });
        builder.show();
    }
}
