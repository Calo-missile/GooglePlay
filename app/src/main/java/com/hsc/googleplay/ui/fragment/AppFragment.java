package com.hsc.googleplay.ui.fragment;

import android.view.View;

import com.hsc.googleplay.ui.view.LoadingPager;

/**
 * Created by 15827 on 2017/5/18.
 */

public class AppFragment extends BaseFragment {

    //只有成功才用到此方法
    @Override
    public View onCreateSuccessView() {
        return null;
    }

    @Override
    public LoadingPager.ResultState onLoad() {
        return LoadingPager.ResultState.STATE_ERROR;
    }
}
