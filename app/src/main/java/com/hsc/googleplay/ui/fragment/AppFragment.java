package com.hsc.googleplay.ui.fragment;

import android.view.View;

import com.hsc.googleplay.domain.AppInfo;
import com.hsc.googleplay.http.protocol.AppProtocol;
import com.hsc.googleplay.ui.adapter.MyBaseAdapter;
import com.hsc.googleplay.ui.holder.AppHolder;
import com.hsc.googleplay.ui.holder.BaseHolder;
import com.hsc.googleplay.ui.view.LoadingPager;
import com.hsc.googleplay.ui.view.MyListView;
import com.hsc.googleplay.utils.UIUtils;

import java.util.ArrayList;

/**
 * Created by 15827 on 2017/5/18.
 */

public class AppFragment extends BaseFragment {

    private ArrayList<AppInfo> data;

    //只有成功才用到此方法
    @Override
    public View onCreateSuccessView() {
        MyListView view = new MyListView(UIUtils.getContext());
        view.setAdapter(new AppAdapter(data));
        return view;
    }

    @Override
    public LoadingPager.ResultState onLoad() {
        AppProtocol protocol = new AppProtocol();
        data = protocol.getData(0);

        return check(data);
    }

    class AppAdapter extends MyBaseAdapter<AppInfo> {
        public AppAdapter(ArrayList<AppInfo> data) {
            super(data);
        }

        @Override
        public BaseHolder<AppInfo> getHolder(int position) {
            return new AppHolder();
        }

        @Override
        public ArrayList<AppInfo> onLoadMore() {
            AppProtocol protocol = new AppProtocol();
            ArrayList<AppInfo> moreData = protocol.getData(getSize());
            
            return moreData;
        }
    }
    
}
