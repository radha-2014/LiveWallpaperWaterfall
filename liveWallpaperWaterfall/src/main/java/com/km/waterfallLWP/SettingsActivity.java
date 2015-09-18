package com.km.waterfallLWP;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.Toast;

import com.km.waterfallLWP.bean.AdapterPager;
import com.km.waterfallLWP.bean.FrameInfo;
import com.km.waterfallLWP.downloader.WallpaperFrameDownloader;
import com.km.waterfallLWP.utils.AppConstant;
import com.km.waterfallLWP.utils.BackgroundUtil;
import com.viewpagerindicator.PageIndicator;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class SettingsActivity extends Activity {

	public static final String MyPREFERENCES = "MyPrefs";
	private ImageView mBtnDone;
	private SharedPreferences mSharedpreferences;
	private SharedPreferences.Editor mEditor;
	private int mWallpaperId;
	private ViewPager mPager;
	List<FrameInfo> list;
	private PageIndicator mIndicator;
    private WallpaperFrameDownloader puzzleFrameDownloader;
    private int mNoOfFrames;

    @Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_settings);
		mSharedpreferences = getSharedPreferences(MyPREFERENCES,
				Context.MODE_PRIVATE);

		mBtnDone = (ImageView) findViewById(R.id.btnDone);
        SharedPreferences prefs = getSharedPreferences(
                getString(R.string.app_name), Context.MODE_PRIVATE);
        int s1 = getIntent().getExtras().getInt("notifyId");
        if (s1 == 3215) {
            mNoOfFrames = AppConstant.BACKGROUND.length-1;
        } else if (s1 == 3220) {
            mNoOfFrames = AppConstant.BACKGROUND.length;
        } else {
            mNoOfFrames = AppConstant.BACKGROUND.length-2;
        }


	}

    @Override
    protected void onStart() {

        init();
        mSharedpreferences = getSharedPreferences(MyPREFERENCES,
                Context.MODE_PRIVATE);
        mWallpaperId = mSharedpreferences.getInt("background_wallpaper", 1);
        switch(mWallpaperId) {
            case 1:
                mPager.setCurrentItem(0);
                break;
            case 2:
                mPager.setCurrentItem(1);
                break;
            case 3:
                mPager.setCurrentItem(2);
                break;
            case 4:
                mPager.setCurrentItem(3);
                break;
            case 5:
                mPager.setCurrentItem(4);
                break;
            case 6:
                mPager.setCurrentItem(5);
                break;
          }
        initListeners();
        super.onStart();
    }

    private void startFunPhotoDownload(int id) {



        puzzleFrameDownloader.downloadWallpaperFrame(this, new WallpaperFrameDownloader.DownloadListener() {

            @Override
            public void onNetworkProblem() {

                SettingsActivity.this.runOnUiThread(new Runnable() {
                    public void run() {
                        Toast.makeText(SettingsActivity.this,
                                "Please check your internet connection",
                                Toast.LENGTH_SHORT).show();
                    }
                });




            }

            @Override
            public void onFileDownloaded(String result) {

                Log.d("SettingsActivity","download complete");
                BackgroundUtil.initializeImagesList(SettingsActivity.this);
                puzzleFrameDownloader.deleteZipFile(puzzleFrameDownloader.mZipPath);
                Log.d("SettingsActivity",puzzleFrameDownloader.mZipPath);
                finish();

            }

            @Override
            public void onDownloadProgressUpdate(int progress) {
                Log.e("state fun", "progress " + progress);

            }

            @Override
            public void onDownloadFailed() {
                SettingsActivity.this.runOnUiThread(new Runnable() {
                    public void run() {
                        Toast.makeText(SettingsActivity.this,
                                "Download Failed",
                                Toast.LENGTH_SHORT).show();
                    }
                });

                mEditor = mSharedpreferences.edit();
                mEditor.putInt("background_wallpaper", 1);
                mEditor.commit();

            }
        }, true,id);

    }


    public void initListeners() {
		mBtnDone.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				int currentItem = mPager.getCurrentItem();
				mEditor = mSharedpreferences.edit();
				mEditor.putInt("background_wallpaper", currentItem+1);
				mEditor.commit();
                puzzleFrameDownloader =  new WallpaperFrameDownloader();
                File [] file = puzzleFrameDownloader.getWallpaperFrames(SettingsActivity.this,currentItem+1,true);
                if(file == null && currentItem+1 >1) {
                    startFunPhotoDownload(currentItem + 1);

               } else {
                    BackgroundUtil.initializeImagesList(SettingsActivity.this);
                    finish();
                }



			}
		});

	}
	
	private void loadBackground() {

        list = new ArrayList<FrameInfo>();
        FrameInfo info = new FrameInfo();
        // adding frames
        int i = 0;
        boolean flag = false;

                 AppConstant.isLocalImage = true;
            for (i = 0; i < mNoOfFrames; i++) {
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

    @Override
    protected void onDestroy() {
        if(list!=null && list.size()>0) {
            list = null;
        }
        super.onDestroy();
    }
}
