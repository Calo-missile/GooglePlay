package com.hsc.googleplay.ui.holder;

import android.view.View;

/**
 * Created by 15827 on 2017/5/19.
 */

public abstract class BaseHolder<T> {

    private final View mRootView;//一个item的根布局
    
    private T data;

    public BaseHolder() {
        mRootView = initView();
        
        //打一个标记Tag
        mRootView.setTag(this);
    }

    //1.加载布局文件
    //2.初始化控件 findViewById
    public abstract View initView();

    //返回item布局对象
    public View getRootView() {
        return mRootView;
    }
    
    //设置当前item的数据
    public void setData(T data){
        this.data = data;
        refreshView(data);
    }
    
    //获取当前item数据
    public T getData() {
        return data;
    }
    
    //根据数据刷新页面
    public abstract void refreshView(T data);
}   
