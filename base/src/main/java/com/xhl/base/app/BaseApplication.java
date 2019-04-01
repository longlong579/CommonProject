package com.xhl.base.app;

import android.app.Application;

import com.blankj.utilcode.util.Utils;

/**
 * @author xhl
 * @desc 2019/4/1 14:26
 */
public abstract class BaseApplication extends Application {
    public static BaseApplication instance;

    @Override
    public void onCreate() {
        super.onCreate();
        instance=this;
        Utils.init(this);//初始化工具类
    }
}
