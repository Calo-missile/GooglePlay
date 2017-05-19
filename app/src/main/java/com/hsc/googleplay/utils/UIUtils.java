package com.hsc.googleplay.utils;

import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.os.Process;
import android.view.View;

import com.hsc.googleplay.global.GoogleApplication;




/**
 * Created by 15827 on 2017/5/18.
 */

public class UIUtils {
    
    public static Context getContext(){
        return GoogleApplication.getmContext();
    }
    
    public static Handler getHandler(){
        return GoogleApplication.getmHandler();
    }
    
    public static int getMainThreadId(){
        return GoogleApplication.getmMainThreadId();
    }
    
    /**
     * 获取字符串
     * */
    public static String getString(int id){
        return getContext().getResources().getString(id);
    }

    /**
     * 获取字符串数组
     * */
    public static String[] getStringArray(int id){
        return getContext().getResources().getStringArray(id);
    }

    /**
     * 获取图片
     * */
    public static Drawable getDrawable(int id){
        return getContext().getResources().getDrawable(id);
    }

    /**
     * 获取颜色
     * */
    public static int getColor(int id){
        return getContext().getResources().getColor(id);
    }
    //根据id获取颜色选择器
    public static ColorStateList getColorStateList(int id) {
        return getContext().getResources().getColorStateList(id);
    }
    
    /**
     * 获取尺寸
     * */
    public static int getDimen(int id){
        return getContext().getResources().getDimensionPixelSize(id);
    }

    /**
     * dp和px转换  屏幕适配
     * */
    public static int dip2px(float dip){
        float density = getContext().getResources().getDisplayMetrics().density;
        return (int) (dip * density + 0.5f);
    }
    public static float px2dip(int px) {
        float density = getContext().getResources().getDisplayMetrics().density;
        return px / density;
    }

    /**
     * 加载布局文件
     *
     * */
    public static View inflate(int id){
        return View.inflate(getContext(), id, null);
    }

    /**
     * 判断是否在主线程
     * */
    public static boolean isRunOnUIThread(){
        int myTid = Process.myTid();
        if (myTid == getMainThreadId()) {
            return true;
        }
        return false;
    }

    public static void runOnUIThread(Runnable runnable) {
        if (isRunOnUIThread()) {
            //如果已在主线程，直接运行
            runnable.run();
        } else {
            //如果是子线程，借助Handler让其运行在主线程
            getHandler().post(runnable);
        }
    }

    
    
}
