package com.hsc.googleplay.ui.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.widget.FrameLayout;

import com.hsc.googleplay.R;
import com.hsc.googleplay.utils.LogUtils;

/**
 * 自定义控件，按比例来决定布局高度
 * Created by 15827 on 2017/5/22.
 */

public class RatioLayout extends FrameLayout {

    private float mRatio;

    public RatioLayout(Context context) {
        super(context);
    }

    public RatioLayout( Context context,  AttributeSet attrs) {
        super(context, attrs);
        //获取属性值
        //当自定义属性时，系统会自动生成属性相关id，此id通过R.styleable来引用
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.RatioLayout);

        // id = 属性名_具体属性字段名称
        //系统自动生成RatioLayout_ratio，根据两个name值
        mRatio = typedArray.getFloat(R.styleable.RatioLayout_ratio, -1);//宽高比例值
        typedArray.recycle();//回收，提高具体性能
        //LogUtils.i("ratio=" + mRatio);
    }

    public RatioLayout( Context context,  AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        // 1 获取宽度
        // 2 根据宽度和比例ratio,计算控件的高度
        // 3 重新测量控件
        LogUtils.d("widthMeasureSpec: "+widthMeasureSpec);

        // MeasureSpec.AT_MOST; 至多模式, 控件有多大显示多大, wrap_content
        // MeasureSpec.EXACTLY; 确定模式, 类似宽高写死成dip, match_parent
        // MeasureSpec.UNSPECIFIED; 未指定模式.
        
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);//获取宽度模式
        int width = MeasureSpec.getSize(widthMeasureSpec); // 获取宽度值

        int heightMode = MeasureSpec.getMode(heightMeasureSpec);//获取高度模式
        int height = MeasureSpec.getSize(heightMeasureSpec); // 获取高度值

        //宽度确定，高度不确定，mRatio合法，才计算高度值
        if (widthMode == MeasureSpec.EXACTLY && heightMode != MeasureSpec.EXACTLY && mRatio > 0) {
            //图片宽度 = 控件宽度 - 左侧内边距 - 右侧内边距
            int imageWidth = width - getPaddingLeft() - getPaddingRight();

            //图片高度 = 图片宽度 / 宽高比例
            int imageHeight = (int) (imageWidth / mRatio + 0.5f);

            // 控件高度 = 图片高度 + 上侧内边距 + 下侧内边距
            height = imageHeight + getPaddingTop() + getPaddingBottom();

            //根据最新的高度重新生成 heightMeasureSpec （高度模式是确定模式）
            heightMeasureSpec = MeasureSpec.makeMeasureSpec(height, MeasureSpec.EXACTLY);
        }
        //按照最新的高度测量控件
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        
    }
}
