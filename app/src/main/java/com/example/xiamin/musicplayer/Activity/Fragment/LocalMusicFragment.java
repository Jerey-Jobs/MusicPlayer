package com.example.xiamin.musicplayer.Activity.Fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.xiamin.musicplayer.Activity.MusicActivity;
import com.example.xiamin.musicplayer.R;
import com.example.xiamin.musicplayer.Service.MusicPlayService;
import com.example.xiamin.musicplayer.adapter.LocalMusicAdapter;

import butterknife.Bind;

/**
 * Created by Xiamin on 2016/9/15.
 * 本地音乐fragment，位于主界面的viewpager中
 */
public class LocalMusicFragment extends BaseFragment implements AdapterView.OnItemClickListener{
    @Bind(R.id.lv_local_music)
    ListView mListLocalMusic;
    @Bind(R.id.tv_empty)
    TextView mTvEmpty;

    private LocalMusicAdapter adapter;

    @Override
    public void initView() {
        adapter = new LocalMusicAdapter();
        mListLocalMusic.setAdapter(adapter);
        mListLocalMusic.setOnItemClickListener(this);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_local_music,container,false);
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        /**
         * 让service播放该音乐
         * 改变activity上的playbar信息
         */
        getPlayService().play(i);
        ((MusicActivity)getActivity()).setPlayBar(MusicPlayService.getMusicList().get(i));
    }
}
