package com.example.xiamin.musicplayer.utils.JsonCallBack;

import com.example.xiamin.musicplayer.Bean.OnlineMuiscBean;

import java.util.List;

/**
 * Created by Xiamin on 2016/9/19.
 * 因为为了泛型callback 只能写一个list类，将json数据解析至此
 */
public class JsonOnlineMusicList {
    private List<OnlineMuiscBean> song_list;

    public List<OnlineMuiscBean> getSong_list() {
        return song_list;
    }

    public void setSong_list(List<OnlineMuiscBean> song_list) {
        this.song_list = song_list;
    }
}
