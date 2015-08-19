package com.km.livewallpaperwaterfall;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;

import com.km.livewallpaperwaterfall.bean.AdapterPager;
import com.km.livewallpaperwaterfall.bean.FrameInfo;
import com.km.livewallpaperwaterfall.utils.AppConstant;
import com.km.livewallpaperwaterfall.utils.BackgroundUtil;
import com.km.livewallpaperwaterfall.R;
import com.viewpagerindicator.PageIndicator;

public class SettingsActivity extends Activity {

	public static final String MyPREFERENCES = "MyPrefs";
	private ImageView mBtnDone;
	private SharedPreferences mSharedpreferences;
	private SharedPreferences.Editor mEditor;
	private int mWallpaperId;
	private ViewPager mPager;
	List<FrameInfo> list;
	private PageIndicator mIndicator;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_settings);
		mSharedpreferences = getSharedPreferences(MyPREFERENCES,
				Context.MODE_PRIVATE);

		mBtnDone = (ImageView) findViewById(R.id.btnDone);
		init();
		mSharedpreferences = getSharedPreferences(MyPREFERENCES,
				Context.MODE_PRIVATE);
		mWallpaperId = mSharedpreferences.getInt("background_wallpaper", 1);
		if(mWallpaperId == 1) {
			mPager.setCurrentItem(0);
		} else {
			mPager.setCurrentItem(1);
		}
		
		initListeners();
	}

	public void initListeners() {
		mBtnDone.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				int currentItem = mPager.getCurrentItem();
				mEditor = mSharedpreferences.edit();
				mEditor.putInt("background_wallpaper", currentItem+1);
				mEditor.commit();
				BackgroundUtil.initializeImagesList(getBaseContext());
				finish();

			}
		});

	}
	
	private void loadBackground() {

		list = new ArrayList<FrameInfo>();
		FrameInfo info = new FrameInfo();
		// adding frames
		for (int i = 0; i < AppConstant.BACKGROUND.length; i++) {
			info = new FrameInfo();
			info.setFrameResourceId(AppConstant.BACKGROUND[i]);

			list.add(info);

		}
	}

	private void init() {
		mPager = (ViewPager) findViewById(R.id.pager);
		AdapterPager adapter = null;
		loadBackground();
		adapter = new AdapterPager(this, list);
		mPager.setAdapter(adapter);
		mIndicator = (PageIndicator) findViewById(R.id.indicator);
		mIndicator.setViewPager(mPager);

	}

	public void onNext(View v) {
		mPager.setCurrentItem(mPager.getCurrentItem() + 1);

	}

	public void onPrevious(View v) {
		mPager.setCurrentItem(mPager.getCurrentItem() - 1);
	}


}
