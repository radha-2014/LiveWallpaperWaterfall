package com.km.waterfallLWP.bean;


import android.graphics.Bitmap;

/**
 * 
 * @author leelaprasadsahu
 *
 */
public class FrameInfo {
	private int frameResourceId;
    private Bitmap mBitmap;

	public int getFrameResourceId() {
		return frameResourceId;
	}

	public void setFrameResourceId(int frameResourceId) {
		this.frameResourceId = frameResourceId;
	}

    public Bitmap getBitmap() { return  mBitmap;}

    public void setBitmap(Bitmap bitmap) {

        mBitmap = bitmap;

    }

}
