package com.example.xiamin.musicplayer.utils.JsonCallBack;

import com.google.gson.Gson;
import com.zhy.http.okhttp.callback.Callback;

import okhttp3.Response;

/**
 * Created by Xiamin on 2016/9/19.
 */
public abstract class JsonCallBack<T> extends Callback<T>{
    private Gson mGson;
    private Class<T> mClass;

    public JsonCallBack(Class<T> tClass) {
        this.mClass = tClass;
        mGson = new Gson();
    }

    @Override
    public T parseNetworkResponse(Response response) throws Exception {
        String json = response.body().string();
        return mGson.fromJson(json,mClass);
    }

}
