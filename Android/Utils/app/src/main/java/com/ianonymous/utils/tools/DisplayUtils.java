package com.ianonymous.utils.tools;

import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PixelFormat;
import android.graphics.Point;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Shader;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.StateListDrawable;
import android.os.Build;
import android.os.Environment;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.lang.reflect.Method;

/**
 * Created by Leo on 2014/6/24.
 */
public class DisplayUtils {

    /**
     * 获得屏幕的长宽
     *
     * @param activity 需要页面对象，才能知道页面的长宽
     * @return 用Point的形式将屏幕尺寸存入Point.x和Point.y中
     */
    public static Point getScreenSize(Activity activity) {
        Point screenSize = new Point();
        if (screenSize.x == 0 || screenSize.y == 0) {
            Display display = activity.getWindowManager().getDefaultDisplay();
            if (Build.VERSION.SDK_INT < Build.VERSION_CODES.HONEYCOMB_MR2) {
                screenSize.x = display.getWidth();
                screenSize.y = display.getHeight();
            } else {
                Class<?> cls = display.getClass();
                try {
                    Method method = cls.getMethod("getSize", new Class[]
                            {
                                    Point.class
                            });
                    method.invoke(display, screenSize);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        return screenSize;
    }

    /**
     * 设置View控件的背景颜色，这里是通过RGB进行渐变颜色设置的，通过代码可以更灵活的设置RGB值，可以大于三种颜色，并且可以设置某一种颜色的具体位置
     *
     * @param view 需要设置渐变的View对象
     */
    public static void setViewBgColor(final View view) {
        view.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                // 直接移除吧
                view.getViewTreeObserver().removeGlobalOnLayoutListener(this);
                StateListDrawable sd = new StateListDrawable();
                LinearGradient shader = new LinearGradient(0, 0, view.getMeasuredWidth(), 0, new int[]
                        {
                                // 注意这里是渐变的颜色
                                0xFF6E6063, 0xFF806F72, 0xFF7D8281, 0xFF797C7B
                        }, new float[]
                        {
                                // 注意这里是渐变的颜色对应的位置，范围是0~1f
                                0f, 0.2f, 0.9f, 1f
                        }, Shader.TileMode.REPEAT);
                ShapeDrawable normal = new ShapeDrawable();
                normal.getPaint().setShader(shader);

                ShapeDrawable selected = new ShapeDrawable();
                selected.getPaint().setColor(Color.BLACK);
                sd.addState(new int[]
                        {
                                android.R.attr.state_enabled, android.R.attr.state_focused
                        }, selected);
                sd.addState(new int[]
                        {
                                android.R.attr.state_pressed, android.R.attr.state_enabled
                        }, selected);
                sd.addState(new int[]
                        {
                                android.R.attr.state_focused
                        }, selected);
                sd.addState(new int[]
                        {
                                android.R.attr.state_pressed
                        }, selected);
                sd.addState(new int[]
                        {
                                android.R.attr.state_enabled
                        }, normal);
                sd.addState(new int[]
                        {}, normal);
                view.setBackgroundDrawable(sd);
            }
        });
    }

    /**
     * 合并两张bitmap为一张
     *
     * @param background
     * @param foreground
     * @return Bitmap
     */
    public static Bitmap combineBitmap(Bitmap background, Bitmap foreground) {
        if (background == null) {
            return null;
        }
        int bgWidth = background.getWidth();
        int bgHeight = background.getHeight();
        int fgWidth = foreground.getWidth();
        int fgHeight = foreground.getHeight();
        Bitmap newmap = Bitmap.createBitmap(bgWidth, bgHeight, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(newmap);
        canvas.drawBitmap(background, 0, 0, null);
        canvas.drawBitmap(foreground, (bgWidth - fgWidth) / 2,
                (bgHeight - fgHeight) / 2, null);
        canvas.save(Canvas.ALL_SAVE_FLAG);
        canvas.restore();
        return newmap;
    }

    /**
     * Bitmap转换Drawable
     *
     * @param bm Bitmap对象
     * @return Drawable对象
     */
    public static Drawable bitmap2Drawable(Bitmap bm) {
        return new BitmapDrawable(bm);
    }

    /**
     * 从资源中获取Bitmap
     *
     * @param activity 页面
     * @param resId    资源ID
     * @return Bitmap对象
     */
    public static Bitmap bitmapFromRes(Activity activity, int resId) {
        Resources res = activity.getResources();
        return BitmapFactory.decodeResource(res, resId);
    }

    /**
     * byte[] → Bitmap
     *
     * @param bytes byte数组对象
     * @return Bitmap
     */
    public static Bitmap bytes2Bitmap(byte[] bytes) {
        if (bytes.length == 0) {
            return null;
        }
        return BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
    }

    /**
     * Bitmap → byte[]
     *
     * @param bm bitmap对象
     * @return byte数组
     */
    public static byte[] bitmap2Bytes(Bitmap bm) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bm.compress(Bitmap.CompressFormat.PNG, 100, baos);
        return baos.toByteArray();
    }


    /**
     * 1)Drawable → Bitmap最简单的直接从BitmapDrawable中获得
     *
     * @param drawable Drawable对象
     * @return Bitmap对象
     */
    public static Bitmap drawable2BitmapSimple(Drawable drawable) {
        BitmapDrawable bd = (BitmapDrawable) drawable;
        return bd.getBitmap();
    }

    /**
     * 2)Drawable → Bitmap使用面板获得Bitmap对象
     *
     * @param drawable Drawable对象
     * @return Bitmap对象
     */
    public static Bitmap drawable2BitmapByCanvas(Drawable drawable) {
        Bitmap bitmap = Bitmap.createBitmap(
                drawable.getIntrinsicWidth(),
                drawable.getIntrinsicHeight(),
                drawable.getOpacity() != PixelFormat.OPAQUE ? Bitmap.Config.ARGB_8888 : Bitmap.Config.RGB_565
        );
        Canvas canvas = new Canvas(bitmap);
        // canvas.setBitmap(bitmap);
        drawable.setBounds(0, 0, drawable.getIntrinsicWidth(),
                drawable.getIntrinsicHeight());
        drawable.draw(canvas);
        return bitmap;
    }

    /**
     * 放大缩小图片
     *
     * @param bitmap    要放大的图片
     * @param dstWidth  目标宽
     * @param dstHeight 目标高
     * @return
     */
    public static Bitmap zoomBitmap(Bitmap bitmap, int dstWidth, int dstHeight) {
        int width = bitmap.getWidth();
        int height = bitmap.getHeight();
        Matrix matrix = new Matrix();
        float scaleWidht = ((float) dstWidth / width);
        float scaleHeight = ((float) dstHeight / height);
        matrix.postScale(scaleWidht, scaleHeight);
        Bitmap newbmp = Bitmap.createBitmap(bitmap, 0, 0, width, height, matrix, true);
        return newbmp;
    }

    /**
     * 获得圆角图片的方法
     *
     * @param bitmap
     * @param roundPx 4脚幅度
     * @return
     */
    public static Bitmap getRoundedCornerBitmap(Bitmap bitmap, float roundPx) {
        Bitmap output = Bitmap.createBitmap(bitmap.getWidth(), bitmap.getHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(output);

        final int color = 0xff424242;
        final Paint paint = new Paint();
        final Rect rect = new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight());
        final RectF rectF = new RectF(rect);

        paint.setAntiAlias(true);
        canvas.drawARGB(0, 0, 0, 0);
        paint.setColor(color);
        canvas.drawRoundRect(rectF, roundPx, roundPx, paint);

        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        canvas.drawBitmap(bitmap, rect, rect, paint);

        return output;
    }

    /**
     * 获得带倒影的图片方法
     *
     * @param bitmap
     * @return
     */
    public static Bitmap createReflectionImageWithOrigin(Bitmap bitmap) {
        final int reflectionGap = 4;
        int width = bitmap.getWidth();
        int height = bitmap.getHeight();

        Matrix matrix = new Matrix();
        matrix.preScale(1, -1);

        Bitmap reflectionImage = Bitmap.createBitmap(bitmap, 0, height / 2, width, height / 2, matrix, false);

        Bitmap bitmapWithReflection = Bitmap.createBitmap(width, (height + height / 2), Bitmap.Config.ARGB_8888);

        Canvas canvas = new Canvas(bitmapWithReflection);
        canvas.drawBitmap(bitmap, 0, 0, null);
        Paint deafalutPaint = new Paint();
        canvas.drawRect(0, height, width, height + reflectionGap, deafalutPaint);

        canvas.drawBitmap(reflectionImage, 0, height + reflectionGap, null);

        Paint paint = new Paint();
        LinearGradient shader = new LinearGradient(0, bitmap.getHeight(), 0, bitmapWithReflection.getHeight() + reflectionGap, 0x70ffffff, 0x00ffffff, Shader.TileMode.CLAMP);
        paint.setShader(shader);
        // Set the Transfer mode to be porter duff and destination in
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.DST_IN));
        // Draw a rectangle using the paint with our linear gradient
        canvas.drawRect(0, height, width, bitmapWithReflection.getHeight() + reflectionGap, paint);

        return bitmapWithReflection;
    }

    public static String saveBitmapToFile(Bitmap bm, String fileName) {
        String absPath = "";
        String str = getSDPath();
        try {
            File localFile1 = new File(str);
            if (!localFile1.exists()) {
                Log.d("MagicMirror", "Dir not exist create it " + str);
                localFile1.mkdirs();
                Log.d("MagicMirror", "Make dir success: " + str);
            }
            absPath = str + "/" + fileName;
            File localFile2 = new File(absPath);
            localFile2.createNewFile();
            FileOutputStream localFileOutputStream = new FileOutputStream(localFile2);
            bm.compress(Bitmap.CompressFormat.PNG, 100, localFileOutputStream);
            localFileOutputStream.flush();
            localFileOutputStream.close();
            //Toast.makeText(this, "图片保存成功", Toast.LENGTH_SHORT).show();
        } catch (FileNotFoundException localFileNotFoundException) {
            localFileNotFoundException.printStackTrace();
        } catch (Exception localException) {
            localException.printStackTrace();
        } finally {
            bm.recycle();
        }
        return absPath;
    }

    public static String getSDPath() {
        if (Environment.getExternalStorageState().equals("mounted"))
            return Environment.getExternalStorageDirectory().toString() + "/FeitaoFace/";
        return "ERROR:没有SD卡";
    }

    private static Toast mToast;

    public static void showTip(Context context, String msg) {
        if (mToast == null) {
            mToast = Toast.makeText(context, msg, Toast.LENGTH_SHORT);
        } else {
            mToast.setText(msg);
            mToast.setDuration(Toast.LENGTH_SHORT);
        }
        mToast.show();
    }
}
