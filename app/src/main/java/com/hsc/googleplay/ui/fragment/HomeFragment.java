package com.hsc.googleplay.ui.fragment;

import android.view.View;
import android.widget.ListView;

import com.hsc.googleplay.ui.adapter.MyBaseAdapter;
import com.hsc.googleplay.ui.holder.BaseHolder;
import com.hsc.googleplay.ui.holder.HomeHolder;
import com.hsc.googleplay.ui.view.LoadingPager;
import com.hsc.googleplay.utils.UIUtils;

import java.util.ArrayList;

/**
 * Created by 15827 on 2017/5/18.
 */

public class HomeFragment extends BaseFragment {
    
    private ArrayList<String> data;

    //如果加载成功，就回调此方法，在主线程运行
    @Override
    public View onCreateSuccessView() {
        /*TextView view = new TextView(UIUtils.getContext());
        view.setText(getClass().getName());
        view.setTextColor(Color.parseColor("#FF00FF"));*/
        ListView view = new ListView(UIUtils.getContext());
        view.setAdapter(new HomeAdapter(data));
        return view;
    }

    //运行在子线程，可以执行耗时操作
    @Override
    public LoadingPager.ResultState onLoad() {

        data = new ArrayList<String>();
        for (int i = 0; i < 20; i++) {
            data.add("测试数据:" + i);
        }

        //请求网络
        return LoadingPager.ResultState.STATE_SUCCESS;
    }
    
    class HomeAdapter extends MyBaseAdapter<String>{

        public HomeAdapter(ArrayList<String> data) {
            super(data);
        }

        @Override
        public BaseHolder<String> getHolder() {
            return new HomeHolder();
        }


    }
    
    
}
