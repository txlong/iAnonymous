package com.ianonymous.utils.tools;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Build;

/**
 * Created by Leo on 2014/6/19.
 */
public class VersionUtils {
    /**
     * 根据应用上下文获得当前应用的Version Name
     *
     * @param context 应用上下文
     * @return 当前应用的版本名称
     */
    public static String getVersionName(Context context) {
        String versionName = "NAN";
        PackageManager packageManager = context.getPackageManager();
        PackageInfo packInfo = null;
        try {
            // getPackageName()是你当前类的包名，0代表是获取版本信息
            packInfo = packageManager.getPackageInfo(context.getPackageName(), 0);
            versionName = packInfo.versionName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        } finally {
            return versionName;
        }
    }

    /**
     * 根据应用上下文获得当前应用的Version Code
     *
     * @param context 应用上下文
     * @return 当前应用的版本号
     */
    public static int getVersionCode(Context context) {
        int versionCode = 0;
        PackageManager packageManager = context.getPackageManager();
        PackageInfo packInfo = null;
        try {
            // getPackageName()是你当前类的包名，0代表是获取版本信息
            packInfo = packageManager.getPackageInfo(context.getPackageName(), 0);
            versionCode = packInfo.versionCode;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        } finally {
            return versionCode;
        }
    }

    /**
     * @return 当前手机的Android版本
     */
    public static String getDeviceVersion() {
        return Build.VERSION.RELEASE;
    }
}
