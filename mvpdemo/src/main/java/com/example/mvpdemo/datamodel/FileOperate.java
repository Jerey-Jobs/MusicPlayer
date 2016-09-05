package com.example.mvpdemo.datamodel;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.mvpdemo.MainActivity;

/**
 * Created by Xiamin on 2016/9/5.
 */
public class FileOperate implements IGetString {
    @Override
    public String getName() {
        String name;
        SharedPreferences sharedPreferences;
        sharedPreferences = MainActivity.getAppContext().getSharedPreferences("data", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        name = sharedPreferences.getString("name","");
        return name;
    }

    @Override
    public void saveName(String name) {
        SharedPreferences sharedPreferences;
        sharedPreferences = MainActivity.getAppContext().getSharedPreferences("data", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("name",name);
        editor.commit();
    }
}
