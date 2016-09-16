package com.example.xiamin.musicplayer.Activity.Fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import com.example.xiamin.musicplayer.R;
import com.example.xiamin.musicplayer.adapter.LocalMusicAdapter;

import butterknife.Bind;

/**
 * Created by Xiamin on 2016/9/15.
 */
public class LocalMusicFragment extends BaseFragment{
    @Bind(R.id.lv_local_music)
    ListView mListLocalMusic;
    @Bind(R.id.tv_empty)
    TextView mTvEmpty;

    @Override
    public void initView() {
        mListLocalMusic.setAdapter(new LocalMusicAdapter());
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_local_music,container,false);
    }

}
