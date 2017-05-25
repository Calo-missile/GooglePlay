package com.hsc.googleplay.ui.fragment;

import android.view.View;

import com.hsc.googleplay.domain.CategoryInfo;
import com.hsc.googleplay.http.protocol.CategoryProtocol;
import com.hsc.googleplay.ui.adapter.MyBaseAdapter;
import com.hsc.googleplay.ui.holder.BaseHolder;
import com.hsc.googleplay.ui.holder.CategoryHolder;
import com.hsc.googleplay.ui.holder.TitleHolder;
import com.hsc.googleplay.ui.view.LoadingPager;
import com.hsc.googleplay.ui.view.MyListView;
import com.hsc.googleplay.utils.UIUtils;

import java.util.ArrayList;

/**
 * Created by 15827 on 2017/5/18.
 */

public class CategoryFragment extends BaseFragment {

    private ArrayList<CategoryInfo> mData;

    @Override
    public View onCreateSuccessView() {
        MyListView view = new MyListView(UIUtils.getContext());
        view.setAdapter(new CategoryAdapter(mData));
        return view;
    }

    @Override
    public LoadingPager.ResultState onLoad() {
        CategoryProtocol protocol = new CategoryProtocol();
        mData = protocol.getData(0);
        return check(mData);
    }

    class CategoryAdapter extends MyBaseAdapter<CategoryInfo> {
        public CategoryAdapter(ArrayList<CategoryInfo> data) {
            super(data);
        }

        @Override
        public int getViewTypeCount() {
            return super.getViewTypeCount() + 1;//在原来基础上加一种类型
        }

        @Override
        public int getInnerType(int position) {
            //判断是标题类型还是普通类型
            CategoryInfo info = mData.get(position);
            //LogUtils.d("CategoryFragment--------"+position);
            if (info.isTitle) {
                //返回标题类型
                return super.getInnerType(position) + 1;//原来类型基础上+1，
                                                        //注意：将TYPE_NORMAL改为1
            } else {
                //返回普通类型
                return super.getInnerType(position);
            }
        }
        
        @Override
        public boolean hasMore() {
            return false;//没有更多数据，需要隐藏更多布局
        }

        @Override
        public BaseHolder<CategoryInfo> getHolder(int position) {
            //判断是标题类型还是普通类型,来返回不同的holder
            CategoryInfo info = mData.get(position);
            if (info.isTitle) {
                return new TitleHolder();
            } else {
                return new CategoryHolder();
            }
           
        }

        @Override
        public ArrayList<CategoryInfo> onLoadMore() {
            return null;
        }
    }
    
}
