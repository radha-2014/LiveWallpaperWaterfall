package com.km.livewallpaperwaterfall.utils;

import java.util.ArrayList;

import com.km.livewallpaperwaterfall.R;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

public class BackgroundUtil {
	private static SharedPreferences mSharedpreferences;
	private static int mWallpaperId;
	public static ArrayList<Bitmap> mListImages;
	public static final String MyPREFERENCES = "MyPrefs";

	public static void initializeImagesList(Context context) {
		mSharedpreferences = context.getSharedPreferences(MyPREFERENCES,
				Context.MODE_PRIVATE);
		mWallpaperId = mSharedpreferences.getInt("background_wallpaper", 1);
		mListImages = new ArrayList<Bitmap>();

		if (mListImages != null && mListImages.size() > 0) {
			mListImages.clear();
		}
		Log.d("Waterfall", "Wallpaperid:" + mWallpaperId);

		TypedArray imgs = null;
		switch (mWallpaperId) {
		case 1:
			imgs = context.getResources()
					.obtainTypedArray(R.array.background_1);
			break;
		case 2:
			imgs = context.getResources()
					.obtainTypedArray(R.array.background_2);
			break;
		}

		for (int i = 0; i < imgs.length(); i++) {
			Bitmap bitmap = BitmapFactory.decodeResource(
					context.getResources(),
					imgs.getResourceId(i, R.drawable.waterfall1));
			Bitmap bitmap1 = BitmapUtil.fitToViewByScale(bitmap, 400, 800);

			mListImages.add(bitmap1);

		}
		imgs.recycle();

	}

}
