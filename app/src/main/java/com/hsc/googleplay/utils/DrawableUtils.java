package com.hsc.googleplay.utils;

import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.StateListDrawable;

/**
 * Created by 15827 on 2017/5/23.
 */

public class DrawableUtils {

    //获取一个shape对象
    public static GradientDrawable getGradientDrawable(int color, int radius) {
        //xml中定义的shape标签 对应此类
        GradientDrawable gradientDrawable = new GradientDrawable();
        gradientDrawable.setShape(GradientDrawable.RECTANGLE);// 矩形

        gradientDrawable.setCornerRadius(radius);
        gradientDrawable.setColor(color);
        
        return gradientDrawable;
    }

    //状态选择器
    public static StateListDrawable getSelector(Drawable normal, Drawable press) {
        StateListDrawable selector = new StateListDrawable();
        selector.addState(new int[]{android.R.attr.state_pressed}, press);//按下图片
        selector.addState(new int[]{}, normal);//默认状态
        
        return selector;
    }

    //重载 封装
    public static StateListDrawable getSelector(int normal, int press, int radius) {

        GradientDrawable bgNormal = getGradientDrawable(normal,radius);

        GradientDrawable bgPress = getGradientDrawable(press, radius);

        StateListDrawable selector = DrawableUtils.getSelector(bgNormal, bgPress);

        return selector;
    }
}
