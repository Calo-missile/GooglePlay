package com.hsc.googleplay.ui.holder;

import android.view.View;
import android.widget.TextView;

import com.hsc.googleplay.R;
import com.hsc.googleplay.domain.AppInfo;
import com.hsc.googleplay.utils.UIUtils;

/**
 * Created by 15827 on 2017/5/19.
 */

public class HomeHolder extends BaseHolder<AppInfo> {

    private TextView tvContent;

    @Override
    public View initView() {
        //1.加载布局
        View view = UIUtils.inflate(R.layout.list_item_home);
        //2.初始化控件
        tvContent = (TextView) view.findViewById(R.id.tv_item_home);
        return view;
    }

    @Override
    public void refreshView(AppInfo data) {
        tvContent.setText(data.name);
    }
}
