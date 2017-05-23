package com.hsc.googleplay.ui.fragment;

import android.graphics.Color;
import android.graphics.drawable.StateListDrawable;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.hsc.googleplay.http.protocol.HotProtocol;
import com.hsc.googleplay.ui.view.FlowLayout;
import com.hsc.googleplay.ui.view.LoadingPager;
import com.hsc.googleplay.utils.DrawableUtils;
import com.hsc.googleplay.utils.UIUtils;

import java.util.ArrayList;
import java.util.Random;

/**
 * Created by 15827 on 2017/5/18.
 */

public class HotFragment extends BaseFragment {

    private ArrayList<String> mData;

    @Override
    public View onCreateSuccessView() {
        //支持上下滑动
        ScrollView scrollView = new ScrollView(UIUtils.getContext());
        FlowLayout flowLayout = new FlowLayout(UIUtils.getContext());

        //设置内边距
        int padding = UIUtils.dip2px(10);
        flowLayout.setPadding(padding, padding, padding, padding);//内边距

        flowLayout.setHorizontalSpacing(UIUtils.dip2px(6));//水平间距
        flowLayout.setVerticalSpacing(UIUtils.dip2px(8));//竖直间距
        for (int i = 0; i < mData.size(); i++) {
            String keyword = mData.get(i);
            TextView view = new TextView(UIUtils.getContext());
            //view.setTextColor(Color.parseColor("#FF00FF"));
            view.setText(keyword);

            view.setTextColor(Color.WHITE);
            view.setTextSize(TypedValue.COMPLEX_UNIT_SP, 18);//18sp
            view.setPadding(padding, padding, padding, padding);//边距
            view.setGravity(Gravity.CENTER);
            
            Random random = new Random();
            //随机颜色，0-255  ->30 - 230，颜色值不能太小或太大，避免过亮或者过暗（美观
            int r = 30 + random.nextInt(200);
            int g = 30 + random.nextInt(200);
            int b = 30 + random.nextInt(200);
            
            int color = 0xffcecece;// 默认颜色

            StateListDrawable selector = DrawableUtils.getSelector(Color.rgb(r, g, b), color, UIUtils.dip2px(6));
            //背景颜色 随机
            view.setBackground(selector);
            
            flowLayout.addView(view);
            
            //设置点击事件，选择器才起作用
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(UIUtils.getContext(), "Hot Hot Hot", Toast.LENGTH_SHORT).show();
                }
            });
        }
        
        scrollView.addView(flowLayout);
        return scrollView;
    }

    @Override
    public LoadingPager.ResultState onLoad() {
        HotProtocol protocol = new HotProtocol();
        mData = protocol.getData(0);
        
        return check(mData);
    }
}
