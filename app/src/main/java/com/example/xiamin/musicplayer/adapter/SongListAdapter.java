package com.example.xiamin.musicplayer.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.xiamin.musicplayer.Bean.SongListInfo;
import com.example.xiamin.musicplayer.R;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Xiamin on 2016/9/18.
 */
public class SongListAdapter extends BaseAdapter {

    private Context mContext;
    private List<SongListInfo> mData;

    public SongListAdapter(List<SongListInfo> data) {
        mData = data;
    }

    @Override
    public int getCount() {
        return mData.size();
    }

    @Override
    public Object getItem(int i) {
        return mData.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolderMusicList viewHolderMusicList;

        if (view == null) {
            view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.view_holder_song_list, viewGroup, false);
            viewHolderMusicList = new ViewHolderMusicList(view);
            view.setTag(viewHolderMusicList);
        } else {
            viewHolderMusicList = (ViewHolderMusicList) view.getTag();
        }

        viewHolderMusicList.vDivider.setVisibility(View.VISIBLE);
        viewHolderMusicList.tvMusic1.setText(mData.get(i).getTitle());

        return view;
    }

    class ViewHolderMusicList {
        @Bind(R.id.iv_cover)
        ImageView ivCover;
        @Bind(R.id.tv_music_1)
        TextView tvMusic1;
        @Bind(R.id.tv_music_2)
        TextView tvMusic2;
        @Bind(R.id.tv_music_3)
        TextView tvMusic3;
        @Bind(R.id.v_divider)
        View vDivider;

        public ViewHolderMusicList(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
