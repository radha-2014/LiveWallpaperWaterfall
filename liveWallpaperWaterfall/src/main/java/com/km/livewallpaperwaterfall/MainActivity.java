package com.km.livewallpaperwaterfall;

import android.app.Activity;
import android.app.WallpaperManager;
import android.content.ComponentName;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;

import com.km.livewallpaperwaterfall.service.GLWallpaperService;
import com.km.livewallpaperwaterfall.R;

public class MainActivity extends Activity {

	private ImageView mBtnSetWallpaper;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		mBtnSetWallpaper = (ImageView) findViewById(R.id.btn_set_wallpaper);
		mBtnSetWallpaper.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				Intent intent = null;
				if (android.os.Build.VERSION.SDK_INT > android.os.Build.VERSION_CODES.JELLY_BEAN) {

					intent = new Intent(
							WallpaperManager.ACTION_CHANGE_LIVE_WALLPAPER);
					intent.putExtra(
							WallpaperManager.EXTRA_LIVE_WALLPAPER_COMPONENT,
							new ComponentName(MainActivity.this,
									GLWallpaperService.class));
				} else {

					intent = new Intent(
							WallpaperManager.ACTION_LIVE_WALLPAPER_CHOOSER);

				}
				startActivity(intent);
				finish();

			}
		});

	}

}
