package com.example.xiamin.musicplayer.Activity.Fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.xiamin.musicplayer.Activity.MusicActivity;
import com.example.xiamin.musicplayer.Bean.MusicDownLoadBean;
import com.example.xiamin.musicplayer.Bean.MusicInfoBean;
import com.example.xiamin.musicplayer.Bean.OnlineMuiscBean;
import com.example.xiamin.musicplayer.Bean.SongListInfo;
import com.example.xiamin.musicplayer.R;
import com.example.xiamin.musicplayer.adapter.OnlineMusicListAdapter;
import com.example.xiamin.musicplayer.utils.Constants;
import com.example.xiamin.musicplayer.utils.JsonCallBack.JsonCallBack;
import com.example.xiamin.musicplayer.utils.JsonCallBack.JsonOnlineMusicList;
import com.example.xiamin.musicplayer.utils.ScreenUtils;
import com.zhy.http.okhttp.OkHttpUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import okhttp3.Call;

/**
 * Created by Xiamin on 2016/9/21.
 */
public class OnlineMusicListFragment extends BaseFragment implements View.OnClickListener
        , AdapterView.OnItemClickListener {
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
    private List<OnlineMuiscBean> mOnlineMusicList;
    private JsonOnlineMusicList mJsonList;
    private String mType;
    private OnlineMusicListAdapter mAdapter;

    @Override
    public void initView() {

        vHeader = LayoutInflater.from(getContext()).inflate(R.layout.activity_online_music_list_header, null);
        AbsListView.LayoutParams params = new AbsListView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT
                , ScreenUtils.dp2px(200));
        vHeader.setLayoutParams(params);
        mlvOnlineMusic.addHeaderView(vHeader, null, false);
        mBackHome.setOnClickListener(this);      //在没有设置adapter前headview 是不显示的

        mllLoading.setVisibility(View.VISIBLE);
        mllLoadFail.setVisibility(View.GONE);
        mlvOnlineMusic.setVisibility(View.GONE);
        mOnlineMusicList = new ArrayList<OnlineMuiscBean>();

        mAdapter = new OnlineMusicListAdapter(getContext(), mOnlineMusicList);

        mlvOnlineMusic.setAdapter(mAdapter);
        mlvOnlineMusic.setOnItemClickListener(this);
        getMusic();
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {


        return inflater.inflate(R.layout.activity_online_music, container, false);
    }

    private void getMusic() {
        Bundle bundle = getArguments();
        mType = bundle.getString(SongListFragment.LIST_POSITION_TYPE);
        log("song " + bundle.getInt(SongListFragment.LIST_POSITION) + mType);

        /**
         * 这些是发送类型请求拿到的json数据demo
         * {
         "song_list": [
         {
         "artist_id": "55356",
         "language": "\u56fd\u8bed",
         "pic_big": "http:\/\/musicdata.baidu.com\/data2\/pic\/4d47a8c4056eb8802670dee0fa2d29e6\/268591168\/268591168.jpg",
         "pic_small": "http:\/\/musicdata.baidu.com\/data2\/pic\/2a356dbf08b7039ebb385a1399e54f04\/268591171\/268591171.jpg",
         "country": "\u5185\u5730",
         "area": "0",
         "publishtime": "2016-08-09",
         "album_no": "1",
         "lrclink": "http:\/\/musicdata.baidu.com\/data2\/lrc\/d5da6ea9a8f508fb1707226d742a4cbd\/268591314\/268591314.lrc",
         "copy_type": "1",
         "hot": "944230",
         "all_artist_ting_uid": "2914",
         "resource_type": "0",
         "is_new": "0",
         "rank_change": "0",
         "rank": "1",
         "all_artist_id": "55356",
         "style": "\u5f71\u89c6\u539f\u58f0",
         "del_status": "0",
         "relate_status": "0",
         "toneid": "0",
         "all_rate": "64,128,256,320,flac",
         "sound_effect": "0",
         "file_duration": 205,
         "has_mv_mobile": 0,
         "versions": "",
         "bitrate_fee": "{\"0\":\"0|0\",\"1\":\"0|0\"}",
         "song_id": "268591208",
         "title": "\u5fae\u5fae\u4e00\u7b11\u5f88\u503e\u57ce",
         "ting_uid": "2914",
         "author": "\u6768\u6d0b",
         "album_id": "268591211",
         "album_title": "\u5fae\u5fae\u4e00\u7b11\u5f88\u503e\u57ce",
         "is_first_publish": 0,
         "havehigh": 2,
         "charge": 0,
         "has_mv": 0,
         "learn": 0,
         "song_source": "web",
         "piao_id": "0",
         "korean_bb_song": "0",
         "resource_type_ext": "0",
         "mv_provider": "0000000000",
         "artist_name": "\u6768\u6d0b"
         },
         {
         "artist_id": "88",
         "language": "\u56fd\u8bed",
         "pic_big": "http:\/\/musicdata.baidu.com\/data2\/pic\/76dc8dc35a361ef018c2c52befabfb03\/267709259\/267709259.jpg",
         "pic_small": "http:\/\/musicdata.baidu.com\/data2\/pic\/eede55e93e4f0353b1eea0a7627e7be1\/267709262\/267709262.jpg",
         "country": "\u5185\u5730",
         "area": "0",
         "publishtime": "2015-05-20",
         "album_no": "4",
         "lrclink": "http:\/\/musicdata.baidu.com\/data2\/lrc\/ac66a881bd5cb97ad351936606c37495\/266097259\/266097259.lrc",
         "copy_type": "1",
         "hot": "887940",
         "all_artist_ting_uid": "2517",
         "resource_type": "0",
         "is_new": "0",
         "rank_change": "0",
         "rank": "2",
         "all_artist_id": "88",
         "style": "\u6d41\u884c",
         "del_status": "0",
         "relate_status": "0",
         "toneid": "0",
         "all_rate": "64,128,192,256,320,flac",
         "sound_effect": "0",
         "file_duration": 261,
         "has_mv_mobile": 0,
         "versions": "",
         "bitrate_fee": "{\"0\":\"0|0\",\"1\":\"0|0\"}",
         "song_id": "242078437",
         "title": "\u6f14\u5458",
         "ting_uid": "2517",
         "author": "\u859b\u4e4b\u8c26",
         "album_id": "241838068",
         "album_title": "\u521d\u5b66\u8005",
         "is_first_publish": 0,
         "havehigh": 2,
         "charge": 0,
         "has_mv": 0,
         "learn": 1,
         "song_source": "web",
         "piao_id": "0",
         "korean_bb_song": "0",
         "resource_type_ext": "0",
         "mv_provider": "0000000000",
         "artist_name": "\u859b\u4e4b\u8c26"
         },
         */
        OkHttpUtils.get().url(Constants.BASE_URL)
                .addParams(Constants.PARAM_METHOD, Constants.METHOD_GET_MUSIC_LIST)
                .addParams(Constants.PARAM_TYPE, mType)
                .addParams(Constants.PARAM_SIZE, "20")
                .build()
                .execute(new JsonCallBack<JsonOnlineMusicList>(JsonOnlineMusicList.class) {
                    @Override
                    public void onError(Call call, Exception e) {
                        mllLoading.setVisibility(View.GONE);
                        mllLoadFail.setVisibility(View.VISIBLE);
                        mlvOnlineMusic.setVisibility(View.GONE);
                    }

                    @Override
                    public void onResponse(JsonOnlineMusicList response) {
                        if (response == null || response.getSong_list() == null) {
                            //        Log.i("iii", "response == null ");
                            return;
                        }
                        mllLoading.setVisibility(View.GONE);
                        mllLoadFail.setVisibility(View.GONE);
                        mlvOnlineMusic.setVisibility(View.VISIBLE);

                        List<OnlineMuiscBean> jsonlist = response.getSong_list();
                        if (jsonlist == null)
                        {
                            log("jsonlist == null");
                        }

                        Log.i("iii", "OnlineMusicListFragment" + jsonlist.get(0).getArtist_name());
                        mOnlineMusicList.addAll(jsonlist);
                        initHeadView(response);
                        for (OnlineMuiscBean k : mOnlineMusicList
                                ) {
                            log(k.getArtist_name() + " " + k.getTitle());
                        }
                        mAdapter.notifyDataSetChanged();
                    }
                });

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
                transaction.setCustomAnimations(0, R.anim.fragment_slide_down)
                        .hide(this)
                        .commit();

                return;
            }
        }
    }


    private void initHeadView(JsonOnlineMusicList res) {
        ImageView ivHeaderBg = (ImageView) vHeader.findViewById(R.id.iv_header_bg);
        ImageView ivCover = (ImageView) vHeader.findViewById(R.id.iv_cover);
        TextView tvTitle = (TextView) vHeader.findViewById(R.id.tv_title);
        TextView tvUpdateDate = (TextView) vHeader.findViewById(R.id.tv_update_date);
        TextView tvComment = (TextView) vHeader.findViewById(R.id.tv_comment);
        tvTitle.setText(res.getBillboard().getName());
        tvUpdateDate.setText(res.getBillboard().getUpdate_date());
        tvComment.setText(res.getBillboard().getComment());
        Glide.with(this)
                .load(res.getBillboard().getPic_s260())
                .error(R.drawable.default_cover)
                .animate(android.R.anim.fade_in)
                .into(ivCover);
        Glide.with(this)
                .load(res.getBillboard().getPic_s640())
                .animate(android.R.anim.fade_in)     //解决了加载卡顿一下的问题
                .override(300, 270)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .centerCrop()
                .into(ivHeaderBg);

        mOnlineTitle.setText(res.getBillboard().getName());
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        log("" + i + " info:" + mOnlineMusicList.get(i).getTitle());
        playMusic(mOnlineMusicList.get(i - 1));
    }

    private void playMusic(final OnlineMuiscBean onlineMuiscBean) {


        OkHttpUtils.get().url(Constants.BASE_URL)

                .addParams(Constants.PARAM_METHOD, Constants.METHOD_DOWNLOAD_MUSIC)
                .addParams(Constants.PARAM_SONG_ID, onlineMuiscBean.getSong_id())
                .build()
                .execute(new JsonCallBack<MusicDownLoadBean>(MusicDownLoadBean.class) {
                    @Override
                    public void onError(Call call, Exception e) {

                    }

                    @Override
                    public void onResponse(MusicDownLoadBean response) {
                        if (response == null) {
                            log("response == null");
                            return;
                        }
                        log("开始播放在线音乐");


                        MusicInfoBean musicInfoBean = new MusicInfoBean();
                        musicInfoBean.setUri(response.getBitrate().getFile_link());
                        musicInfoBean.setDuration(response.getBitrate().getFile_duration());
                        musicInfoBean.setAlbum(onlineMuiscBean.getAlbum_title());
                        musicInfoBean.setTitle(onlineMuiscBean.getTitle());
                        musicInfoBean.setArtist(onlineMuiscBean.getArtist_name());
                        musicInfoBean.setCoverUri(onlineMuiscBean.getPic_big());
                        getPlayService().play(musicInfoBean);
                        ((MusicActivity) getActivity()).setPlayBar(musicInfoBean);
                    }

                });

        //    getPlayService().playUrl(onlineMuiscBean.get);
    }
}
