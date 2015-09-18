package com.km.waterfallLWP.bean;

import java.util.List;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.ImageView;

import com.km.waterfallLWP.utils.AppConstant;

/*
 * Custom adapter for ViewPager, it use in ViewPagerActivity.java
 */
public class AdapterPager extends PagerAdapter {
	private Context mContext;
	private List<FrameInfo> frameInfoList;

	public AdapterPager(Context context, List<FrameInfo> list) {

		mContext = context;
		frameInfoList = list;
	}

	public int getCount() {
		return frameInfoList.size();
	}

	public FrameInfo getItem(int pos) {
		return frameInfoList.get(pos);
	}

	@Override
	public Object instantiateItem(View collection, final int pos) {
		final ImageView imageViewBackground = new ImageView(mContext);
        if(AppConstant.isLocalImage) {
            imageViewBackground.setImageResource(frameInfoList.get(pos).getFrameResourceId());
        } else {
            imageViewBackground.setImageBitmap(frameInfoList.get(pos).getBitmap());
        }

		((ViewPager) collection).addView(imageViewBackground);
		return imageViewBackground;
		
	}

	@Override
	public void destroyItem(View collection, int position, Object view) {
		((ViewPager) collection).removeView((ImageView) view);

	}

	public long getItemId(int pos) {
		return pos;
	}

	@Override
	public boolean isViewFromObject(View view, Object object) {
		return view == ((ImageView) object);
	}
}
