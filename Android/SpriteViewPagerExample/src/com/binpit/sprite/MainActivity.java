package com.binpit.sprite;

import java.util.ArrayList;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

public class MainActivity extends FragmentActivity
{

	private SpriteViewPager mViewPager;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		mViewPager = (SpriteViewPager) findViewById(R.id.viewPager);
		mViewPager.show(this, initData(), (ViewGroup) findViewById(R.id.pagerIndicator), initIndicator());
		mViewPager.setAutoScroll(true);
	}

	private ArrayList<View> initData()
	{
		ArrayList<View> result = new ArrayList<View>();

		ImageView iv1 = new ImageView(this);
		iv1.setBackgroundResource(R.drawable.svp__pic1);
		result.add(iv1);
		iv1.setOnClickListener(new OnClickListener()
		{
			@Override
			public void onClick(View v)
			{
				Toast.makeText(MainActivity.this, "iv1", Toast.LENGTH_SHORT).show();
			}
		});

		ImageView iv2 = new ImageView(this);
		iv2.setBackgroundResource(R.drawable.svp__pic2);
		result.add(iv2);
		iv2.setOnClickListener(new OnClickListener()
		{
			@Override
			public void onClick(View v)
			{
				Toast.makeText(MainActivity.this, "iv2", Toast.LENGTH_SHORT).show();
			}
		});

		ImageView iv3 = new ImageView(this);
		iv3.setBackgroundResource(R.drawable.svp__pic3);
		result.add(iv3);
		iv3.setOnClickListener(new OnClickListener()
		{
			@Override
			public void onClick(View v)
			{
				Toast.makeText(MainActivity.this, "iv3", Toast.LENGTH_SHORT).show();
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