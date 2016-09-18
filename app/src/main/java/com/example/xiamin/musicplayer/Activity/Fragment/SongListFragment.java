package com.example.xiamin.musicplayer.Activity.Fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.example.xiamin.musicplayer.Bean.SongListInfo;
import com.example.xiamin.musicplayer.R;
import com.example.xiamin.musicplayer.adapter.SongListAdapter;
import com.example.xiamin.musicplayer.utils.NetworkUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;

/**
 * Created by Xiamin on 2016/9/16.
 */
public class SongListFragment extends BaseFragment {
    @Bind(R.id.lv_song_list)
    ListView mSongList;
    @Bind(R.id.ll_loading)
    LinearLayout mLoading;
    @Bind(R.id.ll_load_fail)
    LinearLayout mLoadFail;
    private List<SongListInfo> mSongListInfo = new ArrayList<SongListInfo>();

    @Override
    public void initView() {
        if(!NetworkUtils.isNetworkAvailable(getContext()))
        {
            mSongList.setVisibility(View.GONE);
            mLoading.setVisibility(View.GONE);
            mLoadFail.setVisibility(View.VISIBLE);
            return;
        }

        String[] titles = getResources().getStringArray(R.array.online_music_list_title);
        String[] types = getResources().getStringArray(R.array.online_music_list_type);
        for (int i = 0; i < titles.length; i++) {
            SongListInfo info = new SongListInfo();
            info.setTitle(titles[i]);
            info.setType(types[i]);
            mSongListInfo.add(info);
        }
        SongListAdapter adapter = new SongListAdapter(mSongListInfo);
        mSongList.setAdapter(adapter);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_song_list, container, false);
    }


}
