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

/**
 * Created by 15827 on 2017/5/24.
 */

public class DetailAppInfoHolder extends BaseHolder<AppInfo> {

    private ImageView ivIcon;
    private TextView tvName;
    private TextView tvSize;
    private TextView tvDate;
    private TextView tvDownloadNum;
    private TextView tvVersion;
    private RatingBar rbStar;

    @Override
    public View initView() {
        View view = UIUtils.inflate(R.layout.layout_detail_appinfo);
        
        tvName = (TextView) view.findViewById(R.id.tv_name);
        tvSize = (TextView) view.findViewById(R.id.tv_size);
        tvDate = (TextView) view.findViewById(R.id.tv_date);
        ivIcon = (ImageView) view.findViewById(R.id.iv_icon);
        tvDownloadNum = (TextView) view.findViewById(R.id.tv_download_num);
        tvVersion = (TextView) view.findViewById(R.id.tv_version);
        rbStar = (RatingBar) view.findViewById(R.id.rb_star);
        
        return view;
    }

    @Override
    public void refreshView(AppInfo data) {

        Glide.with(UIUtils.getContext()).load(HttpHelper.URL + "image?name=" + data.iconUrl).into(ivIcon);
        tvName.setText(data.name);
        tvDownloadNum.setText("下载量:" + data.downloadNum);
        tvVersion.setText("版本号:" + data.version);
        tvDate.setText(data.date);
        tvSize.setText(Formatter.formatFileSize(UIUtils.getContext(), data.size));
        rbStar.setRating(data.stars);
        
    }
}
