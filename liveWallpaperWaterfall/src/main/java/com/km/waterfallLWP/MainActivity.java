package com.km.waterfallLWP;

import android.app.Activity;
import android.app.WallpaperManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.km.waterfallLWP.reciever.NotificationReceiver;
import com.km.waterfallLWP.service.GLWallpaperService;

public class MainActivity extends Activity {


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.activity_main);
		SharedPreferences prefs = getSharedPreferences(
				getString(R.string.app_name), Context.MODE_PRIVATE);
		if (!prefs.getBoolean("isAlarmSet", false)) {
			Log.d("MainActivity", "start");
			NotificationReceiver.schedule(this, true);
			prefs.edit().putBoolean("isAlarmSet", true).commit();
		}


	}

	public void onClickSetWallpaper(View v) {
		Intent intent;
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

	public void onClickHelp(View v) {
		Intent intent = new Intent(this, HelpActivity.class);
		startActivity(intent);
	}

	public void onClickChooseWallpaper(View v) {
		Intent intent = new Intent(this, SettingsActivity.class);

		startActivity(intent);
	}


}
