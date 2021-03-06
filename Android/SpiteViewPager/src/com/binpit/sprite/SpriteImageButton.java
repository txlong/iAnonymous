package com.binpit.sprite;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.ImageButton;

/**
 * <b>不规则图形按钮控件</b>实现，设置图片为透明，并且边界清晰的可以设置点击不规则按钮的效果，主要实现原理是根据点击的区域的的透明度是不是100%
 * 
 * @author Leo
 * 
 */
public class SpriteImageButton extends ImageButton
{

	public SpriteImageButton(Context context, AttributeSet attrs, int defStyle)
	{
		super(context, attrs, defStyle);
	}

	public SpriteImageButton(Context context, AttributeSet attrs)
	{
		super(context, attrs);
	}

	public SpriteImageButton(Context context)
	{
		super(context);
	}

	@Override
	public boolean onTouchEvent(MotionEvent event)
	{
		if (isTouchPointInView(event.getX(), event.getY()) && event.getAction() != MotionEvent.ACTION_DOWN)
		{
			return super.onTouchEvent(event);
		}
		else
		{
			return false;
		}
	}

	/**
	 * 判断传入的坐标的点是否是透明的，也就是使用这个方法判断这个点是否在这个不规则按钮内
	 * 
	 * @param localX
	 *            对应点击的x轴坐标
	 * @param localY
	 *            对应点击的y轴坐标
	 * @return
	 * 
	 */
	protected boolean isTouchPointInView(float localX, float localY)
	{
		Bitmap bitmap = Bitmap.createBitmap(getWidth(), getHeight(), Config.ARGB_8888);
		Canvas canvas = new Canvas(bitmap);
		draw(canvas);
		int x = (int) localX;
		int y = (int) localY;
		if (x < 0 || x >= getWidth())
			return false;
		if (y < 0 || y >= getHeight())
			return false;
		int pixel = bitmap.getPixel(x, y);
		if ((pixel & 0xff000000) != 0)
		{ // 点在非透明区
			return true;
		}
		else
		{
			return false;
		}
	}
}