package com.hsc.googleplay.global;

import android.app.Application;
import android.content.Context;
import android.os.Handler;
import android.os.Process;


/**
 * 自定义Application  进行全局初始化
 * Created by 15827 on 2017/5/18.
 */

public class GoogleApplication extends Application {

    private static Context mContext;
    private static Handler mHandler;
    private static int mMainThreadId;

    @Override
    public void onCreate() {
        super.onCreate();

        mContext = getApplicationContext();
        mHandler = new Handler();
        
        //获取主线程id
        mMainThreadId = Process.myTid();
    }

    public static Context getmContext() {
        return mContext;
    }

    public static Handler getmHandler() {
        return mHandler;
    }

    public static int getmMainThreadId() {
        return mMainThreadId;
    }
}
