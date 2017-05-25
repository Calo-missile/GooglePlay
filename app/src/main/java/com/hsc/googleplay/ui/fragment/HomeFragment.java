package com.hsc.googleplay.ui.fragment;

import android.content.Intent;
import android.view.View;
import android.widget.AdapterView;

import com.hsc.googleplay.domain.AppInfo;
import com.hsc.googleplay.http.protocol.HomeProtocol;
import com.hsc.googleplay.ui.activity.HomeDetailActivity;
import com.hsc.googleplay.ui.adapter.MyBaseAdapter;
import com.hsc.googleplay.ui.holder.BaseHolder;
import com.hsc.googleplay.ui.holder.HomeHeaderHolder;
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
    private ArrayList<String> mPicturesList;

    //如果加载成功，就回调此方法，在主线程运行
    @Override
    public View onCreateSuccessView() {
        
        MyListView view = new MyListView(UIUtils.getContext());
        
        //给listView添加头布局，展示轮播条
        HomeHeaderHolder header = new HomeHeaderHolder();
        view.addHeaderView(header.getRootView());
        view.setAdapter(new HomeAdapter(data));

        if (mPicturesList != null) {
            //设置轮播数据
            header.setData(mPicturesList);
        }
        
        view.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                AppInfo appInfo = data.get(position - 1);//对象传递，包名，要减去头布局
                if (appInfo != null) {
                    Intent intent = new Intent(UIUtils.getContext(), HomeDetailActivity.class);
                    intent.putExtra("packageName", appInfo.packageName);//传递参数
                    startActivity(intent);
                }
                
            }
        });
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

        mPicturesList = protocol.getPicturesList();

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
