package com.binpit.sprite;

import java.util.ArrayList;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.binpit.util.Utils;

public class SpriteHomeView extends ViewGroup
{
	public View view;
	private Context mContext;
	private SpriteViewPager mViewPager;

	public SpriteHomeView(Context context)
	{
		this(context, null);
		this.mContext = context;
	}

	public SpriteHomeView(Context context, AttributeSet attrs)
	{
		super(context, attrs);
		this.mContext = context;
		LayoutInflater inflater = LayoutInflater.from(context);
		view = inflater.inflate(R.layout.activity_main, null);
		view.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));

		addView(view);

		mViewPager = (SpriteViewPager) view.findViewById(R.id.viewPager);
		mViewPager.setAutoScroll(true);
		mViewPager.show(mContext, initData(), (ViewGroup) findViewById(R.id.pagerIndicator), initIndicator());
	}

	@Override
	protected void onLayout(boolean changed, int l, int t, int r, int b)
	{
		view.layout(l, t, r, b);
	}

	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec)
	{
		int widthSpecSize = MeasureSpec.getSize(widthMeasureSpec);
		int heightSpecSize = MeasureSpec.getSize(heightMeasureSpec);
		setMeasuredDimension(widthSpecSize, heightSpecSize);
		View child = getChildAt(0);
		child.measure(widthMeasureSpec, heightMeasureSpec);
	}

	private ArrayList<View> initData()
	{
		ArrayList<View> result = new ArrayList<View>();

		ImageView iv1 = new ImageView(mContext);
		iv1.setBackgroundResource(R.drawable.svp__pic1);
		result.add(iv1);
		iv1.setOnClickListener(new OnClickListener()
		{
			@Override
			public void onClick(View v)
			{
				Utils.showToast(mContext, "iv1");
			}
		});

		ImageView iv2 = new ImageView(mContext);
		iv2.setBackgroundResource(R.drawable.svp__pic2);
		result.add(iv2);
		iv2.setOnClickListener(new OnClickListener()
		{
			@Override
			public void onClick(View v)
			{
				Utils.showToast(mContext, "iv2");
			}
		});

		ImageView iv3 = new ImageView(mContext);
		iv3.setBackgroundResource(R.drawable.svp__pic3);
		result.add(iv3);
		iv3.setOnClickListener(new OnClickListener()
		{
			@Override
			public void onClick(View v)
			{
				Utils.cancelToast();
			}
		});

		return result;
	}

	private int[] initIndicator()
	{
		int[] result = new int[2];
		result[0] = R.drawable.svp__dot_normal;
		result[1] = R.drawable.svp__dot_focused;
		return result;
	}
}