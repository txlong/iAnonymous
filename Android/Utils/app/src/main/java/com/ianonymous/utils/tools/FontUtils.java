package com.ianonymous.utils.tools;

import android.content.Context;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.ianonymous.utils.R;

/**
 * Created by Leo on 2014/7/1.
 */
public class FontUtils {
    private static Typeface mFontTypeFace = null;

    public static Typeface getCustomFont(Context context) {
        if (mFontTypeFace == null) {
            mFontTypeFace = Typeface.createFromAsset(context.getAssets(), "fonts/jianzhiyi.ttf");
        }
        return mFontTypeFace;
    }

    public void setTextStyle(TextView textView) {
        textView.getPaint().setFlags(Paint.UNDERLINE_TEXT_FLAG);//下划线
        textView.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG); //中间横线
        textView.getPaint().setAntiAlias(true);// 抗锯齿
    }

    //遍历设置字体
    public static void changeViewSize(ViewGroup viewGroup, int screenWidth, int screenHeight) {//传入Activity顶层Layout,屏幕宽,屏幕高
        int adjustFontSize = adjustFontSize(screenWidth, screenHeight);
        for (int i = 0; i < viewGroup.getChildCount(); i++) {
            View v = viewGroup.getChildAt(i);
            if (v instanceof ViewGroup) {
                changeViewSize((ViewGroup) v, screenWidth, screenHeight);
            } else if (v instanceof Button) {//按钮加大这个一定要放在TextView上面，因为Button也继承了TextView
                ((Button) v).setTextSize(adjustFontSize + 2);
            } else if (v instanceof TextView) {
                if (v.getId() == R.id.title_msg) {//顶部标题
                    ((TextView) v).setTextSize(adjustFontSize + 4);
                } else {
                    ((TextView) v).setTextSize(adjustFontSize);
                }
            }
        }
    }


    //获取字体大小
    public static int adjustFontSize(int screenWidth, int screenHeight) {
        screenWidth = screenWidth > screenHeight ? screenWidth : screenHeight;
        /**
         * 1. 在视图的 onsizechanged里获取视图宽度，一般情况下默认宽度是320，所以计算一个缩放比率rate = (float) w/320   w是实际宽度
         * 2. 然后在设置字体尺寸时 paint.setTextSize((int)(8*rate));   8是在分辨率宽为320 下需要设置的字体大小实际字体大小 = 默认字体大小 x  rate
         */
        int rate = (int) (5 * (float) screenWidth / 320); //我自己测试这个倍数比较适合，当然你可以测试后再修改
        return rate < 15 ? 15 : rate; //字体太小也不好看的
    }

}
