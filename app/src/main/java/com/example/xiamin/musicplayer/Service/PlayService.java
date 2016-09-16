package com.example.xiamin.musicplayer.Service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;

/**
 * Created by Xiamin on 2016/9/16.
 */
public class PlayService extends Service{



    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
