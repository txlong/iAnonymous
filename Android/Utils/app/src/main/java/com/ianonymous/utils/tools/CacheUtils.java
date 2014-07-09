package com.ianonymous.utils.tools;

import android.graphics.Bitmap;
import android.support.v4.util.LruCache;

/**
 * Created by Leo on 2014/7/9.
 */
public class CacheUtils {
    private static LruCache<String, Bitmap> mMemoryCache;

    private static void initMemoryCache() {
        if (mMemoryCache == null) {
            // 获取到可用内存的最大值，使用内存超出这个值会引起OutOfMemory异常。
            // LruCache通过构造函数传入缓存值，以KB为单位。
            int maxMemory = (int) (Runtime.getRuntime().maxMemory() / 1024);
            // 使用最大可用内存值的1/2作为缓存的大小。
            int cacheSize = maxMemory / 2;
            mMemoryCache = new LruCache<String, Bitmap>(cacheSize) {
                @Override
                protected int sizeOf(String key, Bitmap bitmap) {
                    // 重写此方法来衡量每张图片的大小，默认返回图片数量。
                    return bitmap.getRowBytes() * bitmap.getHeight() / 1024;
                }
            };
        }
    }

    public static void addBitmapToMemoryCache(String key, Bitmap bitmap) {
        if (mMemoryCache == null) initMemoryCache();
        if (getBitmapFromMemCache(key) == null) {
            mMemoryCache.put(key, bitmap);
        }
    }

    public static Bitmap getBitmapFromMemCache(String key) {
        if (mMemoryCache == null) initMemoryCache();
        return mMemoryCache.get(key);
    }
}
