package com.hsc.googleplay.ui.holder;

import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.hsc.googleplay.R;
import com.hsc.googleplay.domain.CategoryInfo;
import com.hsc.googleplay.http.HttpHelper;
import com.hsc.googleplay.utils.UIUtils;

/**
 * Created by 15827 on 2017/5/23.
 */

public class CategoryHolder extends BaseHolder<CategoryInfo> implements View.OnClickListener {

    private TextView tvName1;
    private TextView tvName2;
    private TextView tvName3;
    private ImageView ivIcon1;
    private ImageView ivIcon2;
    private ImageView ivIcon3;
    private LinearLayout llGrid1,llGrid2, llGrid3;

    @Override
    public View initView() {
        View view = UIUtils.inflate(R.layout.list_item_category);

        tvName1 = (TextView) view.findViewById(R.id.tv_name1);
        tvName2 = (TextView) view.findViewById(R.id.tv_name2);
        tvName3 = (TextView) view.findViewById(R.id.tv_name3);

        ivIcon1 = (ImageView) view.findViewById(R.id.iv_icon1);
        ivIcon2 = (ImageView) view.findViewById(R.id.iv_icon2);
        ivIcon3 = (ImageView) view.findViewById(R.id.iv_icon3);

        llGrid1 = (LinearLayout) view.findViewById(R.id.ll_grid1);
        llGrid2 = (LinearLayout) view.findViewById(R.id.ll_grid2);
        llGrid3 = (LinearLayout) view.findViewById(R.id.ll_grid3);
        
        llGrid1.setOnClickListener(this);
        llGrid2.setOnClickListener(this);
        llGrid3.setOnClickListener(this);
        return view;
    }

    @Override
    public void refreshView(CategoryInfo data) {
        tvName1.setText(data.name1);
        tvName2.setText(data.name2);
        tvName3.setText(data.name3);
        //LogUtils.d("CategoryHolder-------"+data.name1);

        Glide.with(UIUtils.getContext()).load(HttpHelper.URL + "image?name=" + data.url1).into(ivIcon1);
        Glide.with(UIUtils.getContext()).load(HttpHelper.URL + "image?name=" + data.url2).into(ivIcon2);
        Glide.with(UIUtils.getContext()).load(HttpHelper.URL + "image?name=" + data.url3).into(ivIcon3);
    }

    @Override
    public void onClick(View v) {
        CategoryInfo info = getData();
        switch (v.getId()) {
            case R.id.ll_grid1:
                Toast.makeText(UIUtils.getContext(), info.name1, Toast.LENGTH_SHORT).show();
                break;
            case R.id.ll_grid2:
                Toast.makeText(UIUtils.getContext(), info.name2, Toast.LENGTH_SHORT).show();
                break;
            case R.id.ll_grid3:
                Toast.makeText(UIUtils.getContext(), info.name3, Toast.LENGTH_SHORT).show();
                break;
            default:
                break;
        }
    }
}
