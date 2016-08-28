package com.example.xiamin.musicplayer.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.xiamin.musicplayer.Bean.MusicBean;
import com.example.xiamin.musicplayer.CircleImage.CircleImageView;
import com.example.xiamin.musicplayer.R;

import java.util.List;

/**
 * Created by Xiamin on 2016/8/28.
 */
public class MusicListAdapter extends BaseAdapter {

    private Context mContext;
    private LayoutInflater mLayoutInflater;
    private List<MusicBean> mList;

    public MusicListAdapter(Context context, List<MusicBean> list)
    {
        mContext = context;
        mLayoutInflater = LayoutInflater.from(mContext);

        mList = list;
    }


    @Override
    public int getCount() {
        return mList.size();
    }

    @Override
    public Object getItem(int i) {
        return mList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder viewHolder;
        if(view == null)
        {
            view = mLayoutInflater.inflate(R.layout.music_item, null);
            viewHolder = new ViewHolder();
            viewHolder.image = (CircleImageView)view.findViewById(R.id.image_list_item);
            viewHolder.title = (TextView) view.findViewById(R.id.tv_title_item);
            viewHolder.artist = (TextView) view.findViewById(R.id.tv_artist_item);
            view.setTag(viewHolder);
        }else {
            viewHolder = (ViewHolder) view.getTag();
        }

        viewHolder.image.setImageResource(R.drawable.qq_music);
        viewHolder.title.setText(mList.get(i).title);
        viewHolder.artist.setText(mList.get(i).artist);

        return view;
    }

    class ViewHolder{
        ImageView image;
        TextView title;
        TextView artist;
    }
}
