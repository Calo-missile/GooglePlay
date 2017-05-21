package com.hsc.googleplay.ui.holder;

import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.hsc.googleplay.R;
import com.hsc.googleplay.utils.UIUtils;

/**
 * Created by 15827 on 2017/5/20.
 */

public class MoreHolder extends BaseHolder<Integer> {

    // 加载更多的几种状态
    // 1. 可以加载更多
    // 2. 加载更多失败
    // 3. 没有更多数据
    public static final int STATE_MORE_MORE = 1;
    public static final int STATE_MORE_ERROR = 2;
    public static final int STATE_MORE_NONE = 3;
    private LinearLayout mLloadMore;
    private TextView mLoadError;

    public MoreHolder(boolean hasMore) {

        //如果有更多数据，状态为STATE_MORE_MORE，否则为STATE_MORE_NONE，将此状态传递给父类的setData，同时刷新界面
        setData(hasMore ? STATE_MORE_MORE : STATE_MORE_NONE);
    }

    @Override
    public View initView() {
        View view = UIUtils.inflate(R.layout.list_item_more);
        mLloadMore = (LinearLayout) view.findViewById(R.id.ll_load_more);
        mLoadError = (TextView) view.findViewById(R.id.tv_load_error);
        return view;
    }

    @Override
    public void refreshView(Integer data) {
        switch (data) {
            case STATE_MORE_MORE:
                //加载更多
                mLloadMore.setVisibility(View.VISIBLE);
                mLoadError.setVisibility(View.GONE);
                break;
            case STATE_MORE_NONE:
                //隐藏加载更多
                mLloadMore.setVisibility(View.GONE);
                mLoadError.setVisibility(View.GONE);
                break;
            case STATE_MORE_ERROR:
                //加载失败
                mLloadMore.setVisibility(View.GONE);
                mLoadError.setVisibility(View.VISIBLE);
                break;
        }
    }
}
