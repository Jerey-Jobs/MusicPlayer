package com.example.xiamin.musicplayer.Receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.view.KeyEvent;

import com.example.xiamin.musicplayer.Service.MusicPlayService;
import com.example.xiamin.musicplayer.utils.Actions;

/**
 * Created by Xiamin on 2016/10/22.
 * 耳机远程线控事件
 */

public class RemoteControlReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        KeyEvent event = intent.getParcelableExtra(Intent.EXTRA_KEY_EVENT);
        if (event == null || event.getAction() != KeyEvent.ACTION_UP) {
            return;
        }

        Intent serviceIntent = new Intent(context, MusicPlayService.class);

        switch (event.getKeyCode())
        {
            case KeyEvent.KEYCODE_MEDIA_PLAY:
            case KeyEvent.KEYCODE_MEDIA_PAUSE:
            case KeyEvent.KEYCODE_MEDIA_PLAY_PAUSE:
            case KeyEvent.KEYCODE_HEADSETHOOK:
                serviceIntent.setAction(Actions.ACTION_MEDIA_PAUSE);
                context.startService(serviceIntent);
                break;
            case KeyEvent.KEYCODE_MEDIA_NEXT:
                serviceIntent.setAction(Actions.ACTION_MEDIA_NEXT);
                context.startService(serviceIntent);
                break;
            case KeyEvent.KEYCODE_MEDIA_PREVIOUS:
                serviceIntent.setAction(Actions.ACTION_MEDIA_PREVIOUS);
                context.startService(serviceIntent);
                break;
        }
    }
}
