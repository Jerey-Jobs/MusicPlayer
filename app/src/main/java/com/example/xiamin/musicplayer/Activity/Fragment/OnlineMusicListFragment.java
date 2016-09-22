package com.example.xiamin.musicplayer.Activity.Fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.example.xiamin.musicplayer.Bean.SongListInfo;
import com.example.xiamin.musicplayer.R;

import butterknife.Bind;

/**
 * Created by Xiamin on 2016/9/21.
 */
public class OnlineMusicListFragment extends BaseFragment implements View.OnClickListener {
    @Bind(R.id.lv_online_music_list)
    ListView mlvOnlineMusic;
    @Bind(R.id.ll_loading)
    LinearLayout mllLoading;
    @Bind(R.id.ll_load_fail)
    LinearLayout mllLoadFail;
    @Bind(R.id.bar_iv_menu)
    ImageView mBackHome;
    @Bind(R.id.bar_tv_online_music)
    TextView mOnlineTitle;

    private View vHeader;
    private SongListInfo mListInfo;

    @Override
    public void initView() {

        vHeader = LayoutInflater.from(getContext()).inflate(R.layout.activity_online_music_list_header, null);
        AbsListView.LayoutParams params = new AbsListView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 150);
        vHeader.setLayoutParams(params);
        mlvOnlineMusic.addHeaderView(vHeader, null, false);
        mBackHome.setOnClickListener(this);

        mllLoading.setVisibility(View.GONE);
        mllLoadFail.setVisibility(View.GONE);
        mlvOnlineMusic.setVisibility(View.VISIBLE);
    }



    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.activity_online_music, container, false);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.bar_iv_menu: {
                log("onClick R.id.bar_iv_menu");

                /**
                 * 使用hide 完美解决销毁问题
                 */
                FragmentTransaction transaction = getFragmentManager()
                        .beginTransaction();
                transaction.setCustomAnimations(0,R.anim.fragment_slide_down)
                        .hide(this)
                        .commit();

                return;
            }
        }
    }
}