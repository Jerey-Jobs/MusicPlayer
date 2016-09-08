package com.example.xiamin.musicplayer.MVP;

/**
 * Created by Xiamin on 2016/9/8.
 */
public interface IMainView {
    public void setMusicTitle(String name);
    public void setArtistName(String name);
    public void setSeekBarProgress(int progress);
    public void setMusicPassTime(String time);
    public void setMusicWholeTime(String time);
}
