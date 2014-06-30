package com.ianonymous.utils.tools;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.content.res.Resources;

import java.util.Locale;

public class LanguageUtils {
    public static final int TYPE_LANGUAGE_CH = 0;
    public static final int TYPE_LANGUAGE_EN = 1;

    /**
     * 根据标识设置选择的语言环境
     */
    public static boolean setLanguage(Context context, int flag) {
        boolean changed;
        SharedPreferences sharedPreferences = context.getSharedPreferences("binpit", Context.MODE_PRIVATE);
        switch (sharedPreferences.getInt("locale", -1)) {
            case TYPE_LANGUAGE_CH:
                if (TYPE_LANGUAGE_CH == flag) {
                    changed = false;
                    return changed;
                }
                break;
            case TYPE_LANGUAGE_EN:
                if (TYPE_LANGUAGE_EN == flag) {
                    changed = false;
                    return changed;
                }
                break;
            default:
                break;
        }

        Resources resources = context.getResources();
        Configuration configuration = resources.getConfiguration();

        if (flag == TYPE_LANGUAGE_CH) {
            // 设置本地语言为中文
            configuration.locale = Locale.CHINA;
        }
        if (flag == TYPE_LANGUAGE_EN) {
            // 设置本地语言为英文
            configuration.locale = Locale.ENGLISH;
        }
        resources.updateConfiguration(configuration, resources.getDisplayMetrics());

        // TODO 为了确保下次进入程序记住当前选择的语言，可以使用SharedPreferences来记住设置。
        sharedPreferences.edit().putInt("locale", flag).commit();
        changed = true;
        return changed;
    }

    /**
     * 初始化语言调用，最好写到onResume()中，这个方法会从SharedPreferenced中读取locale信息，如果为：
     *
     * <li>{@link #TYPE_LANGUAGE_CH}</li>
     * <li>{@link #TYPE_LANGUAGE_EN}</li>
     *
     * @param context 应用上下文
     */
    public static void initLanguage(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences("binpit", Context.MODE_PRIVATE);
        switch (sharedPreferences.getInt("locale", -1)) {
            case TYPE_LANGUAGE_CH:
                setLanguage(context, TYPE_LANGUAGE_CH);
                break;
            case TYPE_LANGUAGE_EN:
                setLanguage(context, TYPE_LANGUAGE_EN);
                break;
            default:
                break;
        }
    }
}