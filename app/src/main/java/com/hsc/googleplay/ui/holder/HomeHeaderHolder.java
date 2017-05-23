package com.hsc.googleplay.ui.holder;

import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.bumptech.glide.Glide;
import com.hsc.googleplay.R;
import com.hsc.googleplay.http.HttpHelper;
import com.hsc.googleplay.utils.UIUtils;

import java.util.ArrayList;

/**
 * 首页轮播条holder
 * Created by 15827 on 2017/5/23.
 */

public class HomeHeaderHolder extends BaseHolder<ArrayList<String>> {

    private ViewPager mViewPager;
    private ArrayList<String> data ;
    private LinearLayout mLlContainer;

    private int mPreviousPos;// 上个圆点位置

    @Override
    public View initView() {
        //创建根布局，相对布局
        RelativeLayout rlLayout = new RelativeLayout(UIUtils.getContext());
        //初始化根布局，根布局上层控件是listView，所以使用listView定义的LayoutParams
        AbsListView.LayoutParams params = new AbsListView.LayoutParams(
                AbsListView.LayoutParams.MATCH_PARENT, UIUtils.dip2px(150));
        rlLayout.setLayoutParams(params);
        
        //ViewPager
        mViewPager = new ViewPager(UIUtils.getContext());
        RelativeLayout.LayoutParams vpParams = new RelativeLayout.LayoutParams(
                RelativeLayout.LayoutParams.MATCH_PARENT,
                RelativeLayout.LayoutParams.MATCH_PARENT);
        //mViewPager.setLayoutParams(vpParams);
        //把ViewPager返回给相对布局
        rlLayout.addView(mViewPager, vpParams);//用了此方法，就不用使用mViewPager.setLayoutParams(vpParams);
        
        //初始化指示器
        mLlContainer = new LinearLayout(UIUtils.getContext());
        mLlContainer.setOrientation(LinearLayout.HORIZONTAL);//水平方向

        RelativeLayout.LayoutParams llParams = new RelativeLayout.LayoutParams(
                RelativeLayout.LayoutParams.WRAP_CONTENT,
                RelativeLayout.LayoutParams.WRAP_CONTENT);
        
        //设置内边距
        int padding = UIUtils.dip2px(10);
        mLlContainer.setPadding(padding, padding, padding, padding);
        //添加规则，设定展示位置
        llParams.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);// 底部对齐
        llParams.addRule(RelativeLayout.ALIGN_PARENT_RIGHT); //右边对齐
        //添加布局
        rlLayout.addView(mLlContainer, llParams);
        
        return rlLayout;
    }

    @Override
    public void refreshView(final ArrayList<String> data) {
        this.data = data;

        //填充ViewPager数据
        mViewPager.setAdapter(new HomeHeaderAdapter());
        mViewPager.setCurrentItem(data.size() * 10000);//循环滑动
        
        //初始化指示器
        for (int i = 0; i < data.size(); i++) {
            ImageView point = new ImageView(UIUtils.getContext());

            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            
            if (i == 0) {//第一个默认选中
                point.setImageResource(R.drawable.indicator_selected);
            } else {
                point.setImageResource(R.drawable.indicator_normal);
                params.leftMargin = UIUtils.dip2px(4);//左边距
            }
            point.setLayoutParams(params);
            
            mLlContainer.addView(point);
        }
        
        mViewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                
            }

            @Override
            public void onPageSelected(int position) {
                position = position % data.size();
                ImageView point = (ImageView) mLlContainer.getChildAt(position);
                point.setImageResource(R.drawable.indicator_selected);
                
                //上一个点变为不选中
                ImageView prePoint = (ImageView) mLlContainer.getChildAt(mPreviousPos);
                prePoint.setImageResource(R.drawable.indicator_normal);

                mPreviousPos = position;
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        HomeHeaderTask task = new HomeHeaderTask();
        task.start();
    }
    
    class HomeHeaderTask implements Runnable{

        public void start() {
            //移除所有消息，初始化的时候，每当回到首页都会初始化（不移除前面的消息可能还在
            UIUtils.getHandler().removeCallbacksAndMessages(null);
            UIUtils.getHandler().postDelayed(this, 3000);
        }
        @Override
        public void run() {
            int currentItem = mViewPager.getCurrentItem();
            currentItem++;
            mViewPager.setCurrentItem(currentItem);
            
            //继续发 延时3秒消息，实现内循环
            UIUtils.getHandler().postDelayed(this, 3000);
        }
    }

    class HomeHeaderAdapter extends PagerAdapter {
        @Override
        public int getCount() {
            //return data.size();
            return Integer.MAX_VALUE;
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            position = position % data.size();
            
            String url = data.get(position);

            ImageView view = new ImageView(UIUtils.getContext());
            view.setScaleType(ImageView.ScaleType.FIT_XY);
            
            Glide.with(UIUtils.getContext()).load(HttpHelper.URL + "image?name=" + url).into(view);
            container.addView(view);
            return view;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }
    }
    
}
