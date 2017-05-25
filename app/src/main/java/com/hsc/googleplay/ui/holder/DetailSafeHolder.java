package com.hsc.googleplay.ui.holder;

import android.animation.Animator;
import android.animation.ValueAnimator;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.hsc.googleplay.R;
import com.hsc.googleplay.domain.AppInfo;
import com.hsc.googleplay.http.HttpHelper;
import com.hsc.googleplay.utils.LogUtils;
import com.hsc.googleplay.utils.UIUtils;

import java.util.ArrayList;

/**
 * 应用详情-安全模块
 * Created by 15827 on 2017/5/24.
 */

public class DetailSafeHolder extends BaseHolder<AppInfo> {
    
    private ImageView[] mSafeIcons;//安全表示图片
    private ImageView[] mDesIcons;//安全描述图片
    private TextView[] mSafeDes;//安全文字描述
    
    private LinearLayout[] mSafeDesBar;//安全描述条数（图片+文字
    private RelativeLayout rlDesRoot;
    private LinearLayout llDesRoot;
    private int mDesHeight;
    private LinearLayout.LayoutParams mParams;
    private ImageView ivArrow;

    @Override
    public View initView() {

        View view = UIUtils.inflate(R.layout.layout_detail_safeinfo);
        mSafeIcons = new ImageView[4];
        mSafeIcons[0] = (ImageView) view.findViewById(R.id.iv_safe1);
        mSafeIcons[1] = (ImageView) view.findViewById(R.id.iv_safe2);
        mSafeIcons[2] = (ImageView) view.findViewById(R.id.iv_safe3);
        mSafeIcons[3] = (ImageView) view.findViewById(R.id.iv_safe4);

        mDesIcons = new ImageView[4];
        mDesIcons[0] = (ImageView) view.findViewById(R.id.iv_des1);
        mDesIcons[1] = (ImageView) view.findViewById(R.id.iv_des2);
        mDesIcons[2] = (ImageView) view.findViewById(R.id.iv_des3);
        mDesIcons[3] = (ImageView) view.findViewById(R.id.iv_des4);

        mSafeDes = new TextView[4];
        mSafeDes[0] = (TextView) view.findViewById(R.id.tv_des1);
        mSafeDes[1] = (TextView) view.findViewById(R.id.tv_des2);
        mSafeDes[2] = (TextView) view.findViewById(R.id.tv_des3);
        mSafeDes[3] = (TextView) view.findViewById(R.id.tv_des4);

        mSafeDesBar = new LinearLayout[4];
        mSafeDesBar[0] = (LinearLayout) view.findViewById(R.id.ll_des1);
        mSafeDesBar[1] = (LinearLayout) view.findViewById(R.id.ll_des2);
        mSafeDesBar[2] = (LinearLayout) view.findViewById(R.id.ll_des3);
        mSafeDesBar[3] = (LinearLayout) view.findViewById(R.id.ll_des4);

        ivArrow = (ImageView) view.findViewById(R.id.iv_arrow);

        llDesRoot = (LinearLayout) view.findViewById(R.id.ll_des_root);

        rlDesRoot = (RelativeLayout) view.findViewById(R.id.rl_des_root);
        rlDesRoot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toggle();
            }
        });
        return view;
    }
    
    private boolean isOpen = false;//标记安全描述开关状态 默认为关

    //打开或者关闭安全描述信息
    protected void toggle() {
        ValueAnimator animator = null;
        if (isOpen) {
            //关闭
            isOpen = false;
            animator = ValueAnimator.ofInt(mDesHeight, 0); //从某个值变化到某个值
        } else {
            //kaiq
            isOpen = true;
            animator = ValueAnimator.ofInt(0, mDesHeight);
        }
        
        //动画更新监听
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            //启动动画之后，会不断回调此方法来获取最新的值
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                //获取最新高度值
                Integer height = (Integer) animation.getAnimatedValue();

                LogUtils.d("最新高度：" + height);
                
                //重新修改布局高度
                mParams.height = height;
                llDesRoot.setLayoutParams(mParams);
                
            }
        });
        
        animator.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {
                
            }

            @Override
            public void onAnimationEnd(Animator animation) {
                //动画结束事件
                //更新箭头方向
                if (isOpen) {
                    ivArrow.setImageResource(R.drawable.arrow_up);// 关闭向下
                } else {
                    ivArrow.setImageResource(R.drawable.arrow_down);
                }

            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });

        animator.setDuration(200); //动画时间
        animator.start(); // 启动动画
        

    }

    @Override
    public void refreshView(AppInfo data) {
        ArrayList<AppInfo.SafeInfo> safe = data.safe;
        
        for (int i = 0; i < 4; i++) {
            //LogUtils.d("DetailSafeHolder--------" + safe.size());
            if (i < safe.size()) {
                AppInfo.SafeInfo safeInfo = safe.get(i);
                //安全表示图片
                Glide.with(UIUtils.getContext()).load(
                        HttpHelper.URL + "image?name=" + safeInfo.safeUrl).into(mSafeIcons[i]);

                //安全文字描述
                mSafeDes[i].setText(safeInfo.safeDes);
                //安全文字描述
                Glide.with(UIUtils.getContext()).load(
                        HttpHelper.URL + "image?name=" + safeInfo.safeDesUrl).into(mDesIcons[i]);
            } else {
                //剩下不应该显示的图片
                mSafeIcons[i].setVisibility(View.GONE);

                //隐藏多余的文字描述
                mSafeDesBar[i].setVisibility(View.GONE);
            }

        }
        
        //获取安全描述(文字描述部分)的完整高度
        llDesRoot.measure(0, 0);
        mDesHeight = llDesRoot.getMeasuredHeight();
        
        //修改安全描述布局高度为0，达到隐藏效果
        mParams = (LinearLayout.LayoutParams) llDesRoot.getLayoutParams();
        mParams.height = 0;
        llDesRoot.setLayoutParams(mParams);
        
        LogUtils.d("mDesHeight--------" + mDesHeight);
    }
}
