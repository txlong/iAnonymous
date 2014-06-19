package com.ianonymous.utils.tools;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;

/**
 * Created by Administrator on 2014/6/19.
 */
public class VersionUtils {
    public static String getVersionName(Context context) {
        String version = "NAN";
        PackageManager packageManager = context.getPackageManager();
        PackageInfo packInfo = null;
        try {
            // getPackageName()是你当前类的包名，0代表是获取版本信息
            packInfo = packageManager.getPackageInfo(context.getPackageName(), 0);
            version = packInfo.versionName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return version;
    }
}
