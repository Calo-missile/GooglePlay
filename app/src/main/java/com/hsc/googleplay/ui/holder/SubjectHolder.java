package com.hsc.googleplay.ui.holder;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.hsc.googleplay.R;
import com.hsc.googleplay.domain.SubjectInfo;
import com.hsc.googleplay.http.HttpHelper;
import com.hsc.googleplay.utils.UIUtils;

/**
 * Created by 15827 on 2017/5/21.
 */

public class SubjectHolder extends BaseHolder<SubjectInfo> {

    private ImageView mIvPic;
    private TextView mTvTitle;

    @Override
    public View initView() {
        View view = UIUtils.inflate(R.layout.list_item_subject);
        mIvPic = (ImageView) view.findViewById(R.id.iv_pic);
        mTvTitle = (TextView) view.findViewById(R.id.tv_title);
        
        return view;
    }

    @Override
    public void refreshView(SubjectInfo data) {
        mTvTitle.setText(data.des);
        Glide.with(UIUtils.getContext()).load(HttpHelper.URL + "image?name=" + data.url).into(mIvPic);
        //LogUtils.d(data.url);
    }
}
