package com.example.xiamin.musicplayer.Receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.example.xiamin.musicplayer.Service.MusicPlayService;
import com.example.xiamin.musicplayer.utils.Actions;

/**
 * Created by Xiamin on 2016/10/22.
 * 监听耳机拔插事件
 */

public class PhoneComingReceiver extends BroadcastReceiver{
    @Override
    public void onReceive(Context context, Intent intent) {
        Intent serviceIntent = new Intent(context, MusicPlayService.class);
        serviceIntent.setAction(Actions.ACTION_MEDIA_PAUSE);
        context.startService(serviceIntent);
    }
}
