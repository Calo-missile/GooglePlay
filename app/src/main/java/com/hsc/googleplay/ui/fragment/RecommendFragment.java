package com.hsc.googleplay.ui.fragment;

import android.graphics.Color;
import android.util.TypedValue;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.hsc.googleplay.http.protocol.RecommendProtocol;
import com.hsc.googleplay.ui.view.LoadingPager;
import com.hsc.googleplay.ui.view.fly.ShakeListener;
import com.hsc.googleplay.ui.view.fly.StellarMap;
import com.hsc.googleplay.utils.LogUtils;
import com.hsc.googleplay.utils.UIUtils;

import java.util.ArrayList;
import java.util.Random;

/**
 * Created by 15827 on 2017/5/18.
 */

public class RecommendFragment extends BaseFragment {

    private ArrayList<String> mData;

    @Override
    public View onCreateSuccessView() {

        final StellarMap stellarMap = new StellarMap(UIUtils.getContext());
        stellarMap.setAdapter(new RecommendAdapter());
        //随机方式，将控件划分为9行6列的格子，然后在格子中展示
        stellarMap.setRegularity(6, 9);
        
        //设置内边距
        int padding = UIUtils.dip2px(10);
        stellarMap.setInnerPadding(padding, padding, padding, padding);
        
        //设置默认界面，第一页
        stellarMap.setGroup(0, true);

        ShakeListener shake = new ShakeListener(UIUtils.getContext());
        //摇一摇监听
        shake.setOnShakeListener(new ShakeListener.OnShakeListener() {
            @Override
            public void onShake() {
                stellarMap.zoomIn();//跳到下一页数据
            }
        });
        return stellarMap;
    }

    @Override
    public LoadingPager.ResultState onLoad() {
        RecommendProtocol protocol = new RecommendProtocol();
        mData = protocol.getData(0);
        
        return check(mData);
    }
    
    class RecommendAdapter implements StellarMap.Adapter {

        //返回组的个数
        @Override
        public int getGroupCount() {
            return 2;
        }

        //返回某组item个数
        @Override
        public int getCount(int group) {
            int count = mData.size() / getGroupCount();
            if (count == getGroupCount() - 1) {
                //最后一页，将除不尽，余下来的数量追加到最后一页，保证数据不丢失
                count += mData.size() % getGroupCount();
            }
            return count;
        }

        //初始化布局
        @Override
        public View getView(int group, int position, View convertView) {

            //因为position每组都会从0开始计数，所以需要将前面几组数据的个数加起来，
            //才能确定当前组获取数据的角标位置
            position += group * getCount(group - 1);
            //LogUtils.d("RecommendFragment_item pos:" + position);
            String keyword = mData.get(position);

            TextView view = new TextView(UIUtils.getContext());
            //view.setTextColor(Color.parseColor("#FF00FF"));
            view.setText(keyword);

            Random random = new Random();
            //随机大小, 16-25
            int size = 16 + random.nextInt(10);
            view.setTextSize(TypedValue.COMPLEX_UNIT_SP, size);
            
            //随机颜色，0-255  ->30 - 230，颜色值不能太小或太大，避免过亮或者过暗（美观
            int r = 30 + random.nextInt(200);
            int g = 30 + random.nextInt(200);
            int b = 30 + random.nextInt(200);
            view.setTextColor(Color.rgb(r, g, b));
            
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(UIUtils.getContext(), "推荐推荐", Toast.LENGTH_SHORT).show();
                }
            });
            
            return view;
        }

        //返回下一组的id
        @Override
        public int getNextGroupOnZoom(int group, boolean isZoomIn) {
            LogUtils.d("RecommendFragment_isZoomIn" + isZoomIn);
            //上滑false  下滑true
            if (isZoomIn) {
                //往下滑加载上一页
                if (group > 0) {
                    group--;
                } else {
                    //跳到最后一页
                    group = getGroupCount() - 1;
                }
            } else {
                // 往上滑加载下一页
                if (group < getGroupCount() - 1) {
                    group++;
                } else {
                    //跳至第一页
                    group = 0; 
                }
            }
            return group;
        }
    }
}
