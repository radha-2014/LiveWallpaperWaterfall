/**
 * 
 */
package com.km.waterfallLWP.service;

import android.content.Context;
import android.graphics.Bitmap;
import android.opengl.GLSurfaceView.Renderer;
import android.opengl.GLU;
import android.util.Log;
import android.view.Display;
import android.view.WindowManager;

import com.km.waterfallLWP.utils.BackgroundUtil;

import java.lang.ref.WeakReference;
import java.util.ArrayList;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

/**
 * @author impaler
 *
 */
public class WallpaperRenderer implements Renderer {

	private TextureBase textureBase; // the square
	private Context context;
	private long mLastTime;
	private ArrayList<Bitmap> mImages;
	private WeakReference<Bitmap> mBitmapReference = null;
	private Bitmap mBitmap = null;

	private int mImageCounter = 0;
	private boolean isActive;
	public int mScreenHeight, mScreenWidth;

	/** Constructor to set the handed over context */
	public WallpaperRenderer(Context context , boolean isActive) {
		this.context = context;
		Log.d("Run", "GlRenderer()");
		this.isActive = isActive;
		Bitmap bitmap = BackgroundUtil.mListImages.get(0);
		mLastTime = System.currentTimeMillis();
		mBitmapReference = new WeakReference<Bitmap>(bitmap);
		this.textureBase = new TextureBase(mBitmapReference);
		WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
		Display display = wm.getDefaultDisplay();
		mScreenHeight = display.getHeight();
		mScreenWidth = display.getWidth();

	}

	@Override
	public void onDrawFrame(GL10 gl) {
		Log.d("Run", "onDrawFrame()");

		if(isActive) {
		// clear Screen and Depth Buffer
		gl.glClear(GL10.GL_COLOR_BUFFER_BIT | GL10.GL_DEPTH_BUFFER_BIT);

		// Reset the Modelview Matrix
		gl.glLoadIdentity();

		// Drawing
		gl.glTranslatef(0.0f, -0.0f, -2.9f); // move 5 units INTO the screen
		
		

		long currentTime = System.currentTimeMillis();
		if (currentTime - mLastTime >= 100) {
			if (mImageCounter < BackgroundUtil.mListImages.size()) {
				mBitmap = BackgroundUtil.mListImages.get(mImageCounter);
				mBitmapReference = new WeakReference<Bitmap>(mBitmap);
				// mSprite = mBitmapReference.get();
				if (mBitmapReference != null) {
					textureBase.loadBitmap(mBitmapReference);

					// update_coordinates();
					mLastTime = currentTime;
				}
				mImageCounter++;
			} else {
				mImageCounter = 0;
			}

		}

		textureBase.loadGLTexture(gl, context);
		textureBase.draw(gl);
		
		}

	}

	@Override
	public void onSurfaceChanged(GL10 gl, int width, int height) {
		if (height == 0) { // Prevent A Divide By Zero By
			height = 1; // Making Height Equal One
		}

		gl.glViewport(0, 0, width, height); // Reset The Current Viewport
		gl.glMatrixMode(GL10.GL_PROJECTION); // Select The Projection Matrix
		gl.glLoadIdentity(); // Reset The Projection Matrix

		// Calculate The Aspect Ratio Of The Window
		GLU.gluPerspective(gl, 45.0f, (float) width / (float) height, 0.1f,
                100.0f);

		gl.glMatrixMode(GL10.GL_MODELVIEW); // Select The Modelview Matrix
		gl.glLoadIdentity(); // Reset The Modelview Matrix
	}

	@Override
	public void onSurfaceCreated(GL10 gl, EGLConfig config) {
		// Load the texture for the square

		// square.loadGLTexture(gl, context);
		gl.glEnable(GL10.GL_TEXTURE_2D); // Enable Texture Mapping ( NEW )
		gl.glShadeModel(GL10.GL_SMOOTH); // Enable Smooth Shading
		gl.glClearColor(0.0f, 0.0f, 0.0f, 0.5f); // Black Background
		gl.glClearDepthf(1.0f); // Depth Buffer Setup
		gl.glEnable(GL10.GL_DEPTH_TEST); // Enables Depth Testing
		gl.glDepthFunc(GL10.GL_LEQUAL); // The Type Of Depth Testing To Do

		// Really Nice Perspective Calculations
		gl.glHint(GL10.GL_PERSPECTIVE_CORRECTION_HINT, GL10.GL_NICEST);

	}
	
	public void setActive(boolean isActive) {
		this.isActive = isActive;
	}
}
