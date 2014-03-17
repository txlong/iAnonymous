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

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		SpriteViewPager mViewPager = (SpriteViewPager) findViewById(R.id.viewPager);
		mViewPager.show(this, initData(), (ViewGroup) findViewById(R.id.pagerIndicator), initIndicator());
	}

	private ArrayList<View> initData()
	{
		ArrayList<View> result = new ArrayList<View>();

		ImageView iv1 = new ImageView(this);
		iv1.setBackgroundResource(R.drawable.svp__pic1);
		result.add(iv1);

		ImageView iv2 = new ImageView(this);
		iv2.setBackgroundResource(R.drawable.svp__pic2);
		result.add(iv2);

		ImageView iv3 = new ImageView(this);
		iv3.setBackgroundResource(R.drawable.svp__pic3);
		result.add(iv3);

		ImageView iv4 = new ImageView(this);
		iv4.setBackgroundResource(R.drawable.ic_launcher);
		iv4.setOnClickListener(new OnClickListener()
		{
			@Override
			public void onClick(View v)
			{
				Toast.makeText(MainActivity.this, "ic_launcher", Toast.LENGTH_SHORT).show();
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
