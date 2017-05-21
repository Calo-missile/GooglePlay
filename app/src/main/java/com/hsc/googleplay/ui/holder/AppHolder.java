package com.hsc.googleplay.ui.holder;

import android.text.format.Formatter;
import android.view.View;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.hsc.googleplay.R;
import com.hsc.googleplay.domain.AppInfo;
import com.hsc.googleplay.http.HttpHelper;
import com.hsc.googleplay.utils.UIUtils;

import org.xutils.image.ImageOptions;

/**
 * Created by 15827 on 2017/5/21.
 */

public class AppHolder extends BaseHolder<AppInfo> {
    private TextView tvName;
    private TextView tvSize;
    private TextView tvDes;
    private ImageView ivIcon;
    private RatingBar rbStar;

    private ImageOptions mBitmapUitls;

    @Override
    public View initView() {
        //1.加载布局
        View view = UIUtils.inflate(R.layout.list_item_home);
        //2.初始化控件
        tvName = (TextView) view.findViewById(R.id.tv_name);
        tvSize = (TextView) view.findViewById(R.id.tv_size);
        tvDes = (TextView) view.findViewById(R.id.tv_des);
        ivIcon = (ImageView) view.findViewById(R.id.iv_icon);
        rbStar = (RatingBar)view.findViewById(R.id.rb_star);


        return view;
    }

    @Override
    public void refreshView(AppInfo data) {
        tvName.setText(data.name);
        tvSize.setText(Formatter.formatFileSize(UIUtils.getContext(), data.size));
        tvDes.setText(data.des);

        rbStar.setRating(data.stars);
        Glide.with(UIUtils.getContext()).load(HttpHelper.URL + "image?name=" + data.iconUrl).into(ivIcon);
    }
}
