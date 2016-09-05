package com.example.mvpdemo.presenter;

import com.example.mvpdemo.datamodel.FileOperate;
import com.example.mvpdemo.datamodel.IGetString;
import com.example.mvpdemo.view.IGetStringView;

/**
 * Created by Xiamin on 2016/9/5.
 */
public class GetStringPresenter {
    private IGetString getString;
    private IGetStringView getStringView;

    public GetStringPresenter(IGetStringView view)
    {
        this.getStringView = view;
        getString = new FileOperate();
    }

    public void saveName()
    {
        getString.saveName(getStringView.getName());
    }

    public void showName()
    {
        getStringView.showName(getString.getName());
    }
}
