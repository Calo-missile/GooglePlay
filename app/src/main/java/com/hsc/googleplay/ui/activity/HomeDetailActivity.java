package com.hsc.googleplay.ui.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;

import com.hsc.googleplay.R;
import com.hsc.googleplay.domain.AppInfo;
import com.hsc.googleplay.http.protocol.HomeDetailProtocol;
import com.hsc.googleplay.ui.holder.DetailAppInfoHolder;
import com.hsc.googleplay.ui.holder.DetailSafeHolder;
import com.hsc.googleplay.ui.view.LoadingPager;
import com.hsc.googleplay.utils.LogUtils;
import com.hsc.googleplay.utils.UIUtils;

/**
 * 首页应用详情页
 * Created by 15827 on 2017/5/24.
 */

public class HomeDetailActivity extends BaseActivity {

    private LoadingPager mLoadingPager;
    private String mPackageName;
    private AppInfo mData;

    @Override
    protected void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mLoadingPager = new LoadingPager(this) {
            @Override
            public View onCreateSuccessView() {
                return HomeDetailActivity.this.onCreateSuccessView();
            }

            @Override
            public ResultState onLoad() {
                return HomeDetailActivity.this.onLoad();
            }
        };

        setContentView(mLoadingPager);

        //获取从HomeFragment传过来的包名
        mPackageName = getIntent().getStringExtra("packageName");
        //开始加载网络数据
        mLoadingPager.loadDate();
        
    }
    
    public View onCreateSuccessView() {
        //初始化成功的布局
        View view = UIUtils.inflate(R.layout.page_home_detail);
        
        //初始化应用模块信息
        FrameLayout flDetailAppInfo = (FrameLayout) view.findViewById(R.id.fl_detail_app);
        //动态给帧布局填充
        DetailAppInfoHolder appInfoHolder = new DetailAppInfoHolder();
        flDetailAppInfo.addView(appInfoHolder.getRootView());
        appInfoHolder.setData(mData);//刷新
        
        //初始化安全模块
        FrameLayout flDetailSafe = (FrameLayout) view.findViewById(R.id.fl_detail_safe);
        DetailSafeHolder safeHolder = new DetailSafeHolder();
        flDetailSafe.addView(safeHolder.getRootView());
        safeHolder.setData(mData);
        
        return view;
    }
    
    public LoadingPager.ResultState onLoad() {
        //请求网络数据，加载数据
        HomeDetailProtocol protocol = new HomeDetailProtocol(mPackageName);
        mData = protocol.getData(0);
        LogUtils.d("HomeDetailActivity"+mData);

        if (mData != null) {
            return LoadingPager.ResultState.STATE_SUCCESS;
        } else {
            return LoadingPager.ResultState.STATE_ERROR;
        }
    }
}
