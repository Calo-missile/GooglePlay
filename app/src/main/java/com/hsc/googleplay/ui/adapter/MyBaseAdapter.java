package com.hsc.googleplay.ui.adapter;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Toast;

import com.hsc.googleplay.ui.holder.BaseHolder;
import com.hsc.googleplay.ui.holder.MoreHolder;
import com.hsc.googleplay.utils.UIUtils;

import java.util.ArrayList;

/**
 * 对 Adapter的封装
 * Created by 15827 on 2017/5/19.
 */

public abstract class MyBaseAdapter<T> extends BaseAdapter {

    //注意: 此处必须要从0开始写
    private static final int TYPE_NORMAL = 1;// 正常布局类型
    private static final int TYPE_MORE = 0;// 加载更多类型

    private ArrayList<T> data;
    
    public MyBaseAdapter(ArrayList<T> data) {
        this.data = data;
    }

    @Override
    public int getCount() {
        return data.size() + 1; //增加 加载更多布局的数量
    }

    @Override
    public T getItem(int position) {
        return data.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    //返回布局类型个数
    @Override
    public int getViewTypeCount() {
        return 2;
    }

    //返回当前位置应该显示哪种类型布局
    @Override
    public int getItemViewType(int position) {
        if (position == getCount() - 1) {//最后一个
            return TYPE_MORE;
        } else {
            return getInnerType(position);
        }
    }
    
    //子类可以重写此方法来更改返回的布局类型
    public int getInnerType(int postion){
        return TYPE_NORMAL;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        BaseHolder holder = null;
        if (convertView == null) {
            /**
             * 1 加载布局
             * 2 初始化控件
             * 3 打一个标记 Tag*/
            //判断是否加载更多布局
            if (getItemViewType(position) == TYPE_MORE) {
                //加载更多布局
                holder = new MoreHolder(hasMore());
            } else {
                holder = getHolder(position);//子类返回具体对象
            }
        } else {
            holder = (BaseHolder) convertView.getTag();
        }
        
        //根据数据来源刷新 界面
        if (getItemViewType(position) != TYPE_MORE) {

            holder.setData(getItem(position));
        } else {
            //加载更多布局
            //一旦加载更多布局展示出来，就开始加载更多
            //只有更多数据的状态下才加载更多
            MoreHolder moreHolder = (MoreHolder) holder;
            if (moreHolder.getData() == MoreHolder.STATE_MORE_MORE) {
                loadMore(moreHolder);
            }
        }
        
        return holder.getRootView();
    }
    
    //子类可以重写此方法来决定是否加载更多
    public boolean hasMore(){
        return true;
    }
    
    //返回当前页面holder对象，必须由子类实现
    public abstract BaseHolder<T> getHolder(int position);

    private boolean isLoadMore = false;

    //加载更多数据
    public void loadMore(final MoreHolder holder) {
        if (!isLoadMore) {
            isLoadMore = true;
            new Thread(){
                @Override
                public void run() {
                    final ArrayList<T> moreDate = onLoadMore();
                    UIUtils.runOnUIThread(new Runnable() {
                        @Override
                        public void run() {
                            if (moreDate != null) {
                                //每一页有20条数据，如果返回的数据 小于20条，就是最后一页了
                                if (moreDate.size() < 20) {
                                    holder.setData(MoreHolder.STATE_MORE_NONE);
                                    Toast.makeText(UIUtils.getContext(), "没有更多数据了", Toast.LENGTH_SHORT).show();
                                } else {
                                    //还有数据
                                    holder.setData(MoreHolder.STATE_MORE_MORE);
                                }
                                data.addAll(moreDate);
                                MyBaseAdapter.this.notifyDataSetChanged();
                                
                            } else {
                                //加载失败
                                holder.setData(MoreHolder.STATE_MORE_ERROR);
                            }
                            isLoadMore = false;
                        }
                    });
                }
            }.start();
        }
    }
    
    //加载更多数据，必须由子类实现
    public abstract ArrayList<T> onLoadMore();
    
    //获取当前集合大小
    public int getSize() {
        return data.size();
    }
}
