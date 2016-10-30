package com.example.xiamin.musicplayer.Activity.Fragment;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
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
 * 在线音乐大纲界面
 */
public class SongListFragment extends BaseFragment implements AdapterView.OnItemClickListener {
    @Bind(R.id.lv_song_list)
    ListView mSongList;
    @Bind(R.id.ll_loading)
    LinearLayout mLoading;
    @Bind(R.id.ll_load_fail)
    LinearLayout mLoadFail;
    private List<SongListInfo> mSongListInfo = new ArrayList<SongListInfo>();
    private String[] types;

    public final static String LIST_POSITION = "list_postion";
    public final static String LIST_POSITION_INFO = "list_postion_info";
    public final static String LIST_POSITION_TYPE = "list_postion_TYPE";

    @Override
    public void initView() {
        if (!NetworkUtils.isNetworkAvailable(getContext())) {
            mSongList.setVisibility(View.GONE);
            mLoading.setVisibility(View.GONE);
            mLoadFail.setVisibility(View.VISIBLE);
            return;
        }

        /**
         * 加载本地定义好的uri 放入adapter去异步请求
         */
        String[] titles = getResources().getStringArray(R.array.online_music_list_title);
        types = getResources().getStringArray(R.array.online_music_list_type);
        for (int i = 0; i < titles.length; i++) {
            SongListInfo info = new SongListInfo();
            info.setTitle(titles[i]);
            info.setType(types[i]);
            mSongListInfo.add(info);
        }
        SongListAdapter adapter = new SongListAdapter(mSongListInfo, this.getContext());
        mSongList.setAdapter(adapter);
        mSongList.setOnItemClickListener(this);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_song_list, container, false);
    }


    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        if (types[i].equals("#")) {       //若点击的标志 则不跳转
            return;
        }

        Log.i("iii", "跳转到在线详细界面");
        Bundle bundle = new Bundle();
        bundle.putInt(LIST_POSITION, i);
        bundle.putString(LIST_POSITION_TYPE, mSongListInfo.get(i).getType());

        OnlineMusicListFragment online = new OnlineMusicListFragment();
        online.setArguments(bundle);
        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        fragmentManager.beginTransaction()
                .setCustomAnimations(R.anim.fragment_slide_up, 0)
                .replace(R.id.frame, online)
                .commit();
        //增加可以返回的方法
        fragmentManager.beginTransaction().addToBackStack(null);
    }
}
