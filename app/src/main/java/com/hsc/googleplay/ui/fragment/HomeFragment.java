package com.hsc.googleplay.ui.fragment;

import android.view.View;

import com.hsc.googleplay.domain.AppInfo;
import com.hsc.googleplay.http.protocol.HomeProtocol;
import com.hsc.googleplay.ui.adapter.MyBaseAdapter;
import com.hsc.googleplay.ui.holder.BaseHolder;
import com.hsc.googleplay.ui.holder.HomeHolder;
import com.hsc.googleplay.ui.view.LoadingPager;
import com.hsc.googleplay.ui.view.MyListView;
import com.hsc.googleplay.utils.UIUtils;

import java.util.ArrayList;

/**
 * Created by 15827 on 2017/5/18.
 */

public class HomeFragment extends BaseFragment {
    
    //private ArrayList<String> data;
    private ArrayList<AppInfo> data;

    //如果加载成功，就回调此方法，在主线程运行
    @Override
    public View onCreateSuccessView() {
        /*TextView view = new TextView(UIUtils.getContext());
        view.setText(getClass().getName());
        view.setTextColor(Color.parseColor("#FF00FF"));*/
        MyListView view = new MyListView(UIUtils.getContext());
        view.setAdapter(new HomeAdapter(data));
        return view;
    }

    //运行在子线程，可以执行耗时操作
    @Override
    public LoadingPager.ResultState onLoad() {

        /*data = new ArrayList<String>();
        for (int i = 0; i < 20; i++) {
            data.add("测试数据:" + i);
        }*/
        HomeProtocol protocol = new HomeProtocol();
        //加载首页的数据
        data = protocol.getData(0);
        

        //请求网络
        return check(data);
    }
    
    
    
    class HomeAdapter extends MyBaseAdapter<AppInfo>{

        public HomeAdapter(ArrayList<AppInfo> data) {
            super(data);
        }

        @Override
        public BaseHolder<AppInfo> getHolder(int position) {
            return new HomeHolder();
        }

        //此方法在子线程 刷新更多
        @Override
        public ArrayList<AppInfo> onLoadMore() {
            /*ArrayList<AppInfo> moreData = new ArrayList<AppInfo>();

            for (int i = 0; i < 20; i++) {
                moreData.add("测试更多数据：" + i);
            }
            SystemClock.sleep(2000);*/
            HomeProtocol protocol = new HomeProtocol();
            //20,40,60...
            //下一页数据的位置 等于当前集合的大小
            ArrayList<AppInfo> moreResult = protocol.getData(getSize());
            return moreResult;
        }


    }
    
    
}
