package com.example.xiamin.musicplayer.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.xiamin.musicplayer.Bean.OnlineMuiscBean;
import com.example.xiamin.musicplayer.Bean.SongListInfo;
import com.example.xiamin.musicplayer.R;
import com.example.xiamin.musicplayer.utils.Constants;
import com.example.xiamin.musicplayer.utils.JsonCallBack.JsonCallBack;
import com.example.xiamin.musicplayer.utils.JsonCallBack.JsonOnlineMusicList;
import com.zhy.http.okhttp.OkHttpUtils;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import okhttp3.Call;

/**
 * Created by Xiamin on 2016/9/18.
 */
public class SongListAdapter extends BaseAdapter {
    private static final int TYPE_PROFILE = -100;
    private static final int TYPE_MUSIC_LIST = -99;
    private Context mContext;
    private List<SongListInfo> mData;

    public SongListAdapter(List<SongListInfo> data, Context context) {
        mData = data;
        mContext = context;
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

    /**
     * 在两种view切换加载时，易出view因重复加载导致viewholder错误问题
     * 暂时采用这种办法
     *
     * @param i
     * @param view
     * @param viewGroup
     * @return
     */
    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolderMusicList viewHolderMusicList;
        ViewHolderProfile viewHolderProfile;
        /**
         * 必须复写该方法 才能解决view重复使用导致viewholder问题
         */
        int itemType = getItemViewType(i);

        switch (itemType) {
            case TYPE_PROFILE: {
                Log.w("iii", "mData.get(i).getType().equals(#)");
                if (view == null) {
                    view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.view_holder_song_list_profile, viewGroup, false);
                    viewHolderProfile = new ViewHolderProfile(view);
                    view.setTag(viewHolderProfile);
                } else {
                    viewHolderProfile = (ViewHolderProfile) view.getTag();
                }

                viewHolderProfile.tvProfile.setText(mData.get(i).getTitle());

                break;
            }

            case TYPE_MUSIC_LIST: {
                if (view == null) {
                    view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.view_holder_song_list, viewGroup, false);
                    viewHolderMusicList = new ViewHolderMusicList(view);
                    view.setTag(viewHolderMusicList);
                } else {
                    viewHolderMusicList = (ViewHolderMusicList) view.getTag();
                }
                viewHolderMusicList.vDivider.setVisibility(View.VISIBLE);
                viewHolderMusicList.tvMusic1.setText(mData.get(i).getTitle());
                setMusicListItemInfo(mData.get(i), viewHolderMusicList);
            }

        }
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

    class ViewHolderProfile {
        @Bind(R.id.tv_profile)
        TextView tvProfile;

        public ViewHolderProfile(View view) {
            ButterKnife.bind(this, view);
        }
    }


    /**
     * 1.判断是否有数据，若没有 网络请求加载  有 直接加载
     *
     * @param songListInfo
     * @param viewHolderMusicList
     */
    private void setMusicListItemInfo(final SongListInfo songListInfo, final ViewHolderMusicList viewHolderMusicList) {
        if (songListInfo.getCoverUrl() == null) {
            viewHolderMusicList.ivCover.setTag(R.id.tag_songlistviewholder, songListInfo.getTitle());
            viewHolderMusicList.ivCover.setImageResource(R.drawable.default_cover);
            viewHolderMusicList.tvMusic1.setText("加载中...");
            viewHolderMusicList.tvMusic2.setText("");
            viewHolderMusicList.tvMusic3.setText("");
            OkHttpUtils.get().url(Constants.BASE_URL)
                    .addHeader("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,*/*;q=0.8")
                    .addHeader("Accept-Encoding","gzip, deflate, sdch")
                    .addHeader("Accept-Language", "zh-CN,zh;q=0.8,en-US;q=0.6,en;q=0.4")
                    .addHeader("User-Agent", "Mozilla/5.0 (X11; Linux x86_64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/57.0.2987.110 Safari/537.36")
                    .addHeader("Host", "tingapi.ting.baidu.com")
                    .addParams(Constants.PARAM_METHOD, Constants.METHOD_GET_MUSIC_LIST)
                    .addParams(Constants.PARAM_TYPE, songListInfo.getType())
                    .addParams(Constants.PARAM_SIZE, "3")
                    .build()
                    .execute(new JsonCallBack<JsonOnlineMusicList>(JsonOnlineMusicList.class) {
                        @Override
                        public void onError(Call call, Exception e) {
                        }

                        @Override
                        public void onResponse(JsonOnlineMusicList response) {
                            if (response == null || response.getSong_list() == null) {
                                //        Log.i("iii", "response == null ");
                                return;
                            }

                            if (!songListInfo.getTitle().equals(viewHolderMusicList.ivCover.getTag(R.id.tag_songlistviewholder))) {
                                return;
                            }
                            List<OnlineMuiscBean> jsonlist = response.getSong_list();
                            Log.i("iii", response.getSong_list().get(0).getArtist_name() + "|" +
                                    response.getSong_list().get(0).getTitle());

                            songListInfo.setCoverUrl(response.getBillboard().getPic_s260());


                            /**
                             * java.lang.IndexOutOfBoundsException: Invalid index 1, size is 1
                             */
                            if (response.getSong_list().size() >= 1) {
                                songListInfo.setMusic1(jsonlist.get(0).getTitle());
                            } else {
                                songListInfo.setMusic1("");
                            }
                            if (response.getSong_list().size() >= 2) {
                                songListInfo.setMusic2(jsonlist.get(1).getTitle());
                            } else {
                                songListInfo.setMusic2("");
                            }
                            if (response.getSong_list().size() >= 3) {
                                songListInfo.setMusic3(jsonlist.get(2).getTitle());
                            } else {
                                songListInfo.setMusic3("");
                            }


                            Glide.with(mContext).load(songListInfo.getCoverUrl())
                                    .error(R.drawable.default_cover)
                                    .into(viewHolderMusicList.ivCover);
                            viewHolderMusicList.tvMusic1.setText(songListInfo.getMusic1());
                            viewHolderMusicList.tvMusic2.setText(songListInfo.getMusic2());
                            viewHolderMusicList.tvMusic3.setText(songListInfo.getMusic3());
                        }
                    });

        } else {
            viewHolderMusicList.ivCover.setTag(R.id.tag_songlistviewholder, null);
            Glide.with(mContext).load(songListInfo.getCoverUrl())
                    .error(R.drawable.default_cover)
                    .into(viewHolderMusicList.ivCover);
            viewHolderMusicList.tvMusic1.setText(songListInfo.getMusic1());
            viewHolderMusicList.tvMusic2.setText(songListInfo.getMusic2());
            viewHolderMusicList.tvMusic3.setText(songListInfo.getMusic3());
        }
    }

    /**
     *
     * @param position
     * @return
     */
    @Override
    public int getItemViewType(int position) {
        if (mData.get(position).getType().equals("#")) {
            return TYPE_PROFILE;
        } else {
            return TYPE_MUSIC_LIST;
        }
    }
}
