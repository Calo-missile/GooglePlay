package com.hsc.googleplay.ui.fragment;

import android.graphics.Color;
import android.view.View;
import android.widget.TextView;

import com.hsc.googleplay.ui.view.LoadingPager;
import com.hsc.googleplay.utils.UIUtils;

/**
 * Created by 15827 on 2017/5/18.
 */

public class HomeFragment extends BaseFragment {

    //如果加载成功，就回调此方法，在主线程运行
    @Override
    public View onCreateSuccessView() {
        TextView view = new TextView(UIUtils.getContext());
        view.setText(getClass().getName());
        view.setTextColor(Color.parseColor("#FF00FF"));
        return view;
    }

    //运行在子线程，可以执行耗时操作
    @Override
    public LoadingPager.ResultState onLoad() {
        //请求网络
        return LoadingPager.ResultState.STATE_SUCCESS;
    }
}
