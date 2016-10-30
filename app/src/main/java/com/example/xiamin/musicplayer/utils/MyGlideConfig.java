package com.example.xiamin.musicplayer.utils;

import android.content.Context;

import com.bumptech.glide.Glide;
import com.bumptech.glide.GlideBuilder;
import com.bumptech.glide.load.DecodeFormat;
import com.bumptech.glide.load.engine.cache.InternalCacheDiskCacheFactory;
import com.bumptech.glide.load.engine.cache.LruResourceCache;
import com.bumptech.glide.module.GlideModule;

import java.io.File;

/**
 * Created by Xiamin on 2016/10/6.
 */
public class MyGlideConfig implements GlideModule {
    private static final int MEMORY_MAX_SPACE=(int) (Runtime.getRuntime().maxMemory()/8);

    @Override
    public void applyOptions(Context context, GlideBuilder builder) {
        //设置加载图片的样式,比默认图片质量好,但占用内存会大点
        builder.setDecodeFormat(DecodeFormat.PREFER_ARGB_8888);
        builder.setMemoryCache(new LruResourceCache(MEMORY_MAX_SPACE));
        builder.setDiskCache(new InternalCacheDiskCacheFactory(context, getDiskFileString(context,"glideCache"), 20*1024*1024));
    }

    private String getDiskFileString(Context mContext,String str) {
        File dirFile=new File(mContext.getCacheDir().getAbsolutePath().toString()+str);
        File tempFile=new File(dirFile,"bitmaps");
        if (! tempFile.getParentFile().exists()){
            tempFile.getParentFile().mkdirs();
        }
        return tempFile.getAbsolutePath().toString();
    }

    @Override
    public void registerComponents(Context context, Glide glide) {

    }
}
