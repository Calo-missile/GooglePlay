package com.hsc.googleplay.ui.holder;

import android.view.View;
import android.widget.TextView;

import com.hsc.googleplay.R;
import com.hsc.googleplay.domain.CategoryInfo;
import com.hsc.googleplay.utils.UIUtils;

/**
 * Created by 15827 on 2017/5/23.
 */

public class TitleHolder extends BaseHolder<CategoryInfo> {

    private TextView tvTitle;

    @Override
    public View initView() {
        View view = UIUtils.inflate(R.layout.list_item_title);
        tvTitle = (TextView)view.findViewById(R.id.tv_cate_title);
        return view;
    }

    @Override
    public void refreshView(CategoryInfo data) {
        tvTitle.setText(data.title);
    }
}
