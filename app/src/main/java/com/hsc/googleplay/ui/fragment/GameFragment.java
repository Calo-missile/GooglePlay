package com.hsc.googleplay.ui.fragment;

import android.graphics.Color;
import android.view.View;
import android.widget.TextView;

import com.hsc.googleplay.ui.view.LoadingPager;
import com.hsc.googleplay.utils.UIUtils;

/**
 * Created by 15827 on 2017/5/18.
 */

public class GameFragment extends BaseFragment {
    @Override
    public View onCreateSuccessView() {
        TextView view = new TextView(UIUtils.getContext());
        view.setText(getClass().getName());
        view.setTextColor(Color.parseColor("#FF00FF"));
        return view;
    }

    @Override
    public LoadingPager.ResultState onLoad() {
        return LoadingPager.ResultState.STATE_SUCCESS;
    }
}
