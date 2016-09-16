package com.example.xiamin.musicplayer.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.xiamin.musicplayer.Bean.MusicInfoBean;
import com.example.xiamin.musicplayer.R;
import com.example.xiamin.musicplayer.Service.MusicPlayService;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Xiamin on 2016/9/16.
 */
public class LocalMusicAdapter extends BaseAdapter {
    @Override
    public int getCount() {
        return MusicPlayService.getMusicList().size();
    }

    @Override
    public Object getItem(int i) {
        return MusicPlayService.getMusicList().get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder holder;
        if (view == null) {
            view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.view_holder_music, viewGroup, false);
            holder = new ViewHolder(view);
            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }
        final MusicInfoBean music = MusicPlayService.getMusicList().get(i);
        holder.tvTitle.setText(music.getTitle());
        holder.tvArtist.setText(music.getArtist());
        holder.vDivider.setVisibility(isShowDivider(i) ? View.VISIBLE : View.GONE);
        /**
         * Glide加载图片，就是这么简单。。
         */
        Glide.with(viewGroup.getContext())
                .load(music.getCoverUri())
                .error(R.drawable.default_cover)
                .into(holder.ivCover);
        return view;
    }

    private boolean isShowDivider(int position) {
        return position != MusicPlayService.getMusicList().size() - 1;
    }
    class ViewHolder {
        @Bind(R.id.v_playing)
        View vPlaying;
        @Bind(R.id.iv_cover)
        ImageView ivCover;
        @Bind(R.id.tv_title)
        TextView tvTitle;
        @Bind(R.id.tv_artist)
        TextView tvArtist;
        @Bind(R.id.iv_more)
        ImageView ivMore;
        @Bind(R.id.v_divider)
        View vDivider;

        public ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
