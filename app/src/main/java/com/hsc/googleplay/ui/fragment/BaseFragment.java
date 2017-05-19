package com.hsc.googleplay.ui.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.hsc.googleplay.ui.view.LoadingPager;
import com.hsc.googleplay.utils.UIUtils;

/**
 * Created by 15827 on 2017/5/18.
 */

public abstract class BaseFragment extends Fragment {

    private LoadingPager mLoadingPage;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,  Bundle savedInstanceState) {

        /*TextView view = new TextView(UIUtils.getContext());
        view.setText(getClass().getSimpleName());*/
        mLoadingPage = new LoadingPager(UIUtils.getContext()) {
            @Override
            public View onCreateSuccessView() {
                //此处一定要调用BaseFragment的onCreateSuccessView，否则栈溢出
                return BaseFragment.this.onCreateSuccessView();
            }

            @Override
            public ResultState onLoad() {
                return BaseFragment.this.onLoad();
            }
        };
        
        return mLoadingPage;
    }
    
    //加载成功的布局必须由子类来实现
    public abstract View onCreateSuccessView();

    //加载网络数据，必须由子类来实现
    public abstract LoadingPager.ResultState onLoad();
    
    //开始加载数据
    public void loadData(){
        if (mLoadingPage != null) {
            mLoadingPage.loadDate();
        }
    }
}
