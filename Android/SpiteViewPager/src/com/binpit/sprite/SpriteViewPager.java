package com.binpit.sprite;

import java.util.ArrayList;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.binpit.views.IView;

public class SpriteViewPager extends ViewPager implements IView
{
	private static final int BASE_NO = Integer.MAX_VALUE / 2;

	private final int FLAG_AUTO_SCROLL = 0;

	private ArrayList<View> arrayList;
	private SpriteViewPagerAdapter spriteViewPagerAdapter;
	private int mCurrentPosition;
	private boolean mAutoScroolFlag = false;
	private Thread mThread = new Thread(new Runnable()
	{
		@Override
		public void run()
		{
			while (mAutoScroolFlag)
			{
				try
				{
					Log.d("txl", "isContinue:" + isContinue);
					Thread.sleep(2000);
					if (isContinue)
					{
						Thread.sleep(2000);
						mCurrentPosition++;

						Message msg = new Message();
						msg.what = FLAG_AUTO_SCROLL;
						msg.arg1 = mCurrentPosition;
						handler.sendMessage(msg);
					}
				}
				catch (InterruptedException e)
				{
					e.printStackTrace();
				}
			}
		}
	});

	@SuppressLint("HandlerLeak")
	private Handler handler = new Handler()
	{
		public void handleMessage(Message msg)
		{
			switch (msg.what)
			{
			case FLAG_AUTO_SCROLL:
				getView().setCurrentItem(mCurrentPosition);
				break;
			default:
				break;
			}
		};
	};

	public SpriteViewPager(Context context)
	{
		super(context);
	}

	public SpriteViewPager(Context context, AttributeSet attrs)
	{
		super(context, attrs);
	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec)
	{
		/*int height = 0;
		for (int i = 0; i < getChildCount(); i++)
		{
			View child = getChildAt(i);
			child.measure(widthMeasureSpec, MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED));
			int h = child.getMeasuredHeight();
			if (h > height)
			{
				height = h;
			}
		}

		heightMeasureSpec = MeasureSpec.makeMeasureSpec(height, MeasureSpec.EXACTLY);
*/
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);
	}

	public void addViewItem(View view)
	{
		this.arrayList.add(view);
	}

	public void show(Activity activity, ArrayList<View> views, ViewGroup viewGroup, int[] indicatorIDs)
	{
		spriteViewPagerAdapter = new SpriteViewPagerAdapter(views);
		this.setAdapter(spriteViewPagerAdapter);
		this.setPagerIndicator(activity, views, viewGroup, indicatorIDs);
		// 此行代码控制指示器显示在第一个位置
		mCurrentPosition = BASE_NO - BASE_NO % views.size();
		this.setCurrentItem(mCurrentPosition);
		this.mThread.start();
	}

	public void setPagerIndicator(Activity activity, ArrayList<View> views, ViewGroup viewGroup, int[] indicatorIDs)
	{
		this.setOnPageChangeListener(new SpriteViewPageChangeListener(activity, views, viewGroup, indicatorIDs));
	}

	public void setAutoScroll(boolean autoScroolFlag)
	{
		this.mAutoScroolFlag = autoScroolFlag;
	}

	private boolean isContinue = true;

	@Override
	public boolean onTouchEvent(MotionEvent event)
	{
		switch (event.getAction())
		{
		case MotionEvent.ACTION_DOWN:
		case MotionEvent.ACTION_MOVE:
			isContinue = false;
			break;
		case MotionEvent.ACTION_UP:
			isContinue = true;
			break;
		default:
			isContinue = true;
			break;
		}
		return super.onTouchEvent(event);
	}

	@Override
	public SpriteViewPager getView()
	{
		return this;
	}

	@Override
	public void reset()
	{
		arrayList.clear();
		spriteViewPagerAdapter.notifyDataSetChanged();
	}

	@Override
	public void update()
	{
		spriteViewPagerAdapter.notifyDataSetChanged();
	}

	@Override
	public void release()
	{
		// TODO Auto-generated method stub
	}

	class SpriteViewPageChangeListener implements OnPageChangeListener
	{
		private Activity mActivity;
		private ArrayList<View> mViews;
		private ViewGroup mViewGroup;
		private int[] mIndicatorIDs;

		public SpriteViewPageChangeListener(Activity activity, ArrayList<View> views, ViewGroup viewGroup, int[] indicatorIDs)
		{
			this.mActivity = activity;
			this.mViews = views;
			this.mViewGroup = viewGroup;
			this.mIndicatorIDs = indicatorIDs;
		}

		@Override
		public void onPageSelected(int arg0)
		{
			mCurrentPosition = arg0;
			mViewGroup.removeAllViews();
			for (int i = 0; i < mViews.size(); i++)
			{
				ImageView iv = new ImageView(mActivity);
				if (i == mCurrentPosition % mViews.size())
				{
					iv.setImageResource(mIndicatorIDs[0]);
				}
				else
				{
					iv.setImageResource(mIndicatorIDs[1]);
				}
				mViewGroup.addView(iv);
			}
		}

		@Override
		public void onPageScrolled(int arg0, float arg1, int arg2)
		{
		}

		@Override
		public void onPageScrollStateChanged(int arg0)
		{
		}
	}

	class SpriteViewPagerAdapter extends PagerAdapter
	{
		ArrayList<View> mViews;

		public SpriteViewPagerAdapter(ArrayList<View> views)
		{
			this.mViews = views;
		}

		@Override
		public int getCount()
		{
			return Integer.MAX_VALUE;
		}

		@Override
		public boolean isViewFromObject(View arg0, Object arg1)
		{
			return arg0 == arg1;
		}

		@Override
		public Object instantiateItem(View container, int position)
		{
			try
			{
				// 此处专门处理异常-->java.lang.IllegalStateException: The specified
				// child already has a parent. You must call removeView() on
				// the child's parent first.
				((ViewPager) container).addView(mViews.get(position % mViews.size()), 0);
			}
			catch (Exception e)
			{
			}
			return mViews.get(position % mViews.size());
		}

		@Override
		public void destroyItem(View container, int position, Object object)
		{
			// 循环滑动时此处不能销毁
			// ((ViewPager) container).removeView(arrayList.get(position %
			// arrayList.size()));
		}
	}
}