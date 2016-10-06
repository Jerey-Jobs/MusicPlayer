package com.example.xiamin.musicplayer.Activity;

import android.app.Application;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.support.v4.util.LongSparseArray;
import android.util.DisplayMetrics;

import com.zhy.http.okhttp.OkHttpUtils;

import java.util.concurrent.TimeUnit;

/**
 * Created by Xiamin on 2016/9/15.
 */
public class MusicPlayerApplication extends Application {
    private static MusicPlayerApplication sInstance;
    private LongSparseArray<String> mDownloadList = new LongSparseArray<>();
    private static Resources sRes;

    @Override
    public void onCreate() {
        super.onCreate();

        sInstance = this;
        sRes = getResources();

        initOkHttpUtils();
        initGlide();

    }

    public static MusicPlayerApplication getInstance() {
        return sInstance;
    }

    public LongSparseArray<String> getDownloadList() {
        return mDownloadList;
    }

    private void initOkHttpUtils() {
        OkHttpUtils.getInstance().setConnectTimeout(30, TimeUnit.SECONDS);
        OkHttpUtils.getInstance().setReadTimeout(30, TimeUnit.SECONDS);
        OkHttpUtils.getInstance().setWriteTimeout(30, TimeUnit.SECONDS);

    }

    private void initGlide()
    {
    //    new GlideBuilder().setMemoryCache(new LruResourceCache(MEMORY_MAX_SPACE);
    }


    public static void updateNightMode(boolean on) {
        DisplayMetrics dm = sRes.getDisplayMetrics();
        Configuration config = sRes.getConfiguration();
        config.uiMode &= ~Configuration.UI_MODE_NIGHT_MASK;
        config.uiMode |= on ? Configuration.UI_MODE_NIGHT_YES : Configuration.UI_MODE_NIGHT_NO;
        sRes.updateConfiguration(config, dm);
    }

}
