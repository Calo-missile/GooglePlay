package com.hsc.googleplay.ui.adapter;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.hsc.googleplay.ui.holder.BaseHolder;

import java.util.ArrayList;

/**
 * 对 Adapter的封装
 * Created by 15827 on 2017/5/19.
 */

public abstract class MyBaseAdapter<T> extends BaseAdapter {

    private ArrayList<T> data;
    
    public MyBaseAdapter(ArrayList<T> data) {
        this.data = data;
    }

    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public T getItem(int position) {
        return data.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        BaseHolder holder;
        if (convertView == null) {
            /**
             * 1 加载布局
             * 2 初始化控件
             * 3 打一个标记 Tag*/
            holder = getHolder();//子类返回具体对象
        } else {
            holder = (BaseHolder) convertView.getTag();
        }
        
        /**
         * 根据数据来源刷新 界面*/
        holder.setData(getItem(position));
        
        return holder.getRootView();
    }
    
    //返回当前页面holder对象，必须由子类实现
    public abstract BaseHolder<T> getHolder();
}
