package com.ianonymous.utils.tools;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Build;
import android.telephony.TelephonyManager;

import java.io.InputStream;
import java.util.Iterator;
import java.util.Properties;

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
        }
        return versionName;
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
        }
        return versionCode;
    }

    /**
     * @return 当前手机的Android版本
     */
    public static String getDeviceVersion() {
        return Build.VERSION.RELEASE;
    }

    public static String getDeviceId(Context context) {
        String imei = "";
        TelephonyManager tm = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
        imei = tm.getDeviceId();
        return imei;
    }

    public static String getChannelId(Context context) {
        String channelId = "";
        Properties pro = new Properties();
        try {
            InputStream is = context.getAssets().open("channel.properties");
            pro.load(is);
            Iterator<Object> keySet = pro.keySet().iterator();
            while (keySet.hasNext()) {
                String key = String.valueOf(keySet.next());
                String value = pro.getProperty(key);

                if (key.equals("channel")) {
                    channelId = value;
                }
            }
            is.close();
        } catch (Exception e) {
        }
        return channelId;
    }
}
