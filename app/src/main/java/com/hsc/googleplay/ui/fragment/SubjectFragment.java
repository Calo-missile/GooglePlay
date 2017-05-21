package com.hsc.googleplay.ui.fragment;

import android.view.View;

import com.hsc.googleplay.domain.SubjectInfo;
import com.hsc.googleplay.http.protocol.SubjectProtocol;
import com.hsc.googleplay.ui.adapter.MyBaseAdapter;
import com.hsc.googleplay.ui.holder.BaseHolder;
import com.hsc.googleplay.ui.holder.SubjectHolder;
import com.hsc.googleplay.ui.view.LoadingPager;
import com.hsc.googleplay.ui.view.MyListView;
import com.hsc.googleplay.utils.UIUtils;

import java.util.ArrayList;

/**
 * Created by 15827 on 2017/5/18.
 */

public class SubjectFragment extends BaseFragment {

    private ArrayList<SubjectInfo> mData;

    @Override
    public View onCreateSuccessView() {

        MyListView listView = new MyListView(UIUtils.getContext());
        listView.setAdapter(new SubjectAdapter(mData));
        return listView;
    }

    @Override
    public LoadingPager.ResultState onLoad() {
        SubjectProtocol protocol = new SubjectProtocol();
        mData = protocol.getData(0);
        return check(mData);
    }

    class SubjectAdapter extends MyBaseAdapter<SubjectInfo> {
        
        public SubjectAdapter(ArrayList<SubjectInfo> data) {
            super(data);
        }

        @Override
        public BaseHolder<SubjectInfo> getHolder() {
            return new SubjectHolder();
        }

        @Override
        public ArrayList<SubjectInfo> onLoadMore() {
            SubjectProtocol protocol = new SubjectProtocol();
            ArrayList<SubjectInfo> moreData = protocol.getData(getSize());
            return moreData;
        }
    }
    
}
