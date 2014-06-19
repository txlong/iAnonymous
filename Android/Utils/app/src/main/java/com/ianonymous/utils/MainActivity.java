package com.ianonymous.utils;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

import com.ianonymous.utils.tools.VersionUtils;


public class MainActivity extends Activity {
    private TextView mVersionTv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mVersionTv = (TextView) findViewById(R.id.versionTv);
        mVersionTv.setText("当前应用的版本名称是：" + VersionUtils.getVersionName(this));
        mVersionTv.append("\n当前应用的版本号是：" + VersionUtils.getVersionCode(this));
        mVersionTv.append("\n当前设备的Android版本为：" + VersionUtils.getDeviceVersion());
    }
}
