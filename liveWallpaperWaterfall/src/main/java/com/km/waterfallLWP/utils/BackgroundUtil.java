package com.km.waterfallLWP.utils;

import java.io.File;
import java.util.ArrayList;

import com.km.waterfallLWP.R;
import com.km.waterfallLWP.downloader.WallpaperFrameDownloader;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.view.Display;
import android.view.WindowManager;

public class BackgroundUtil {
	private static SharedPreferences mSharedpreferences;
	private static int mWallpaperId;
	public static ArrayList<Bitmap> mListImages;
	public static final String MyPREFERENCES = "MyPrefs";
    private static WallpaperFrameDownloader mFrameDownloader;
    private static int mScreenHeight,mScreenWidth;

    public static void initializeImagesList(Context context) {

        File[] files = null;
        mSharedpreferences = context.getSharedPreferences(MyPREFERENCES,
                Context.MODE_PRIVATE);
        mWallpaperId = mSharedpreferences.getInt("background_wallpaper", 1);
        mListImages = new ArrayList<Bitmap>();
        mFrameDownloader = new WallpaperFrameDownloader();
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        Display display = wm.getDefaultDisplay();
        mScreenHeight = display.getHeight();
        mScreenWidth = display.getWidth();
        Log.d("Waterfall", "Got Wallpaperid:" + mWallpaperId);
        if(AppConstant.ROTATION ==0 || AppConstant.ROTATION == 180) {
            switch (mWallpaperId) {
                case 2:
                    files = mFrameDownloader.getWallpaperFrames(context, 2,true);
                    break;
                case 3:
                    files = mFrameDownloader.getWallpaperFrames(context, 3,true);
                    break;
                case 4:
                    files = mFrameDownloader.getWallpaperFrames(context, 4,true);
                    break;
                case 5:
                    files = mFrameDownloader.getWallpaperFrames(context, 5,true);
                    break;
                case 6:
                    files = mFrameDownloader.getWallpaperFrames(context, 6,true);
                    break;

              }
        } else {
            switch (mWallpaperId) {
                case 2:
                    files = mFrameDownloader.getWallpaperFrames(context, 2,false);
                    break;
                case 3:
                    files = mFrameDownloader.getWallpaperFrames(context, 3,false);
                    break;
                case 4:
                    files = mFrameDownloader.getWallpaperFrames(context, 4,false);
                    break;
                case 5:
                    files = mFrameDownloader.getWallpaperFrames(context, 5,false);
                    break;
                case 6:
                    files = mFrameDownloader.getWallpaperFrames(context, 6,false);
                    break;

            }
        }

        Log.d("Waterfall", "Files length:" + files);
        if (files != null) {
            int i = 0;
            while (i < files.length) {
                Log.d("Waterfall", "File Name:" + files[i].getPath());
                Bitmap bitmap =BitmapFactory.decodeFile(files[i].getPath());
                Bitmap bitmap1 = null;
                if(AppConstant.ROTATION==0 || AppConstant.ROTATION==180) {
                    //bitmap1  = BitmapUtil.fitToViewByRect(bitmap,mScreenWidth,mScreenHeight);
                    bitmap1 = bitmap;
                } else {
                 //   bitmap1  =  BitmapUtil.fitToViewByRect(bitmap,mScreenHeight,mScreenWidth);
                    bitmap1 = bitmap;
                }
                mListImages.add(bitmap1);
                bitmap = null;
                i++;
            }


        } else {
            if (mListImages != null && mListImages.size() > 0) {
                mListImages.clear();
            }

            if(mWallpaperId>1) {
                mWallpaperId = 1;
            }

            Log.d("Waterfall", "Wallpaperid:" + mWallpaperId);

            TypedArray imgs = null;
            switch (mWallpaperId) {
                case 1:
                    imgs = context.getResources()
                            .obtainTypedArray(R.array.background_1);
                    break;
            }


            for (int i = 0; i < imgs.length(); i++) {

                Bitmap bitmap1 = null;
                Bitmap bitmap2 = null;

                Bitmap bitmap = BitmapFactory.decodeResource(
                            context.getResources(),
                            imgs.getResourceId(i, R.drawable.waterfall1));

                    if (AppConstant.ROTATION == 0 || AppConstant.ROTATION == 180) {
                        bitmap1 = ScalingUtilities.createScaledBitmap(bitmap, 480, 800, ScalingUtilities.ScalingLogic.CROP);
                    } else {
                        bitmap1 = ScalingUtilities.createScaledBitmap(bitmap, 800, 480, ScalingUtilities.ScalingLogic.CROP);
                    }

                mListImages.add(bitmap1);



            }


            imgs.recycle();
        }
    }
	}


