package com.km.waterfallLWP.downloader;

import android.app.ProgressDialog;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Environment;
import android.util.Log;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.util.zip.ZipInputStream;

public class WallpaperFrameDownloader {
    public String mZipPath;

	private File getWallpaperFrameOutputZipFile(Context context,int selectedFrame) {
        String path =  null;
        switch(selectedFrame) {
            case 2:
                path = Environment.getExternalStorageDirectory().getPath() + File.separatorChar + "dexatiframes" + File.separatorChar + "WaterfallLWP_1.zip";
                break;
            case 3:
                path = Environment.getExternalStorageDirectory().getPath() + File.separatorChar + "dexatiframes" + File.separatorChar + "WaterfallLWP_2.zip";
                break;
            case 4:
                path = Environment.getExternalStorageDirectory().getPath() + File.separatorChar + "dexatiframes" + File.separatorChar + "WaterfallLWP_3.zip";
                break;
            case 5:
                path = Environment.getExternalStorageDirectory().getPath() + File.separatorChar + "dexatiframes" + File.separatorChar + "WaterfallLWP_4.zip";
                break;
            case 6:
                path = Environment.getExternalStorageDirectory().getPath() + File.separatorChar + "dexatiframes" + File.separatorChar + "WaterfallLWP_5.zip";
                break;

        }
		File file = new File(path);
		if (!file.getParentFile().exists()) {
			file.getParentFile().mkdirs();
		}
        mZipPath = file.getPath();
		return file;
	}

	public void downloadWallpaperFrame(Context context, DownloadListener listener,
			boolean showDialog,int selectedFrame) {
		File[] files = getWallpaperFrames(context,selectedFrame,true);
        String url = "";
        switch(selectedFrame) {
            case 2:
                url = "https://nwls.s3.amazonaws.com/temp/dexati/WaterfallLWP_1.zip";
                break;
            case 3:
                url ="https://nwls.s3.amazonaws.com/temp/dexati/WaterfallLWP_2.zip";
                break;
            case 4:
                url = "https://nwls.s3.amazonaws.com/temp/dexati/WaterfallLWP_3.zip";
                break;
            case 5:
                url = "https://nwls.s3.amazonaws.com/temp/dexati/WaterfallLWP_4.zip";
                break;
            case 6:
                url = "https://nwls.s3.amazonaws.com/temp/dexati/WaterfallLWP_5.zip";
                break;

        }
		if (files == null || files.length <= 0) {
			ZipFileDownloader downloader = new ZipFileDownloader(context,
					listener, showDialog, getWallpaperFrameOutputZipFile(context,selectedFrame));
			downloader
					.execute(url);
		} else {
			listener.onFileDownloaded(getWallpaperFrameOutputZipFile(context,selectedFrame)
					.getParentFile().getPath());
		}

	}




	public File[] getWallpaperFrames(Context context,int background_id,boolean isPotrait) {

        File file = null;
        if(isPotrait) {
            Log.d("Waterfall", "Got Background:" +background_id);
            switch (background_id) {
                case 2:
                    file = new File(getWallpaperFrameOutputZipFile(context,background_id).getParentFile()
                            .getPath() + File.separatorChar + "WaterfallLWP_1" + File.separatorChar + "waterfall", "Portrait");
                    Log.d("Waterfall", "File path:" +file.getPath());
                    break;
                case 3:
                    file = new File(getWallpaperFrameOutputZipFile(context,background_id).getParentFile()
                            .getPath() + File.separatorChar + "WaterfallLWP_2" + File.separatorChar + "waterfall", "Portrait");
                    break;
                case 4:
                    file = new File(getWallpaperFrameOutputZipFile(context,background_id).getParentFile()
                            .getPath() + File.separatorChar + "WaterfallLWP_3" + File.separatorChar + "waterfall", "Portrait");
                    break;
                case 5:
                    file = new File(getWallpaperFrameOutputZipFile(context,background_id).getParentFile()
                            .getPath() + File.separatorChar + "WaterfallLWP_4" + File.separatorChar + "waterfall", "Portrait");
                    break;
                case 6:
                    file = new File(getWallpaperFrameOutputZipFile(context,background_id).getParentFile()
                            .getPath() + File.separatorChar + "WaterfallLWP_5" + File.separatorChar + "waterfall", "Portrait");
                    break;


            }
        } else {
            switch (background_id) {
                case 2:
                    file = new File(getWallpaperFrameOutputZipFile(context,background_id).getParentFile()
                            .getPath() + File.separatorChar + "WaterfallLWP_1" + File.separatorChar + "waterfall", "Landscape");
                    break;
                case 3:
                    file = new File(getWallpaperFrameOutputZipFile(context,background_id).getParentFile()
                            .getPath() + File.separatorChar + "WaterfallLWP_2" + File.separatorChar + "waterfall", "Landscape");
                    break;
                case 4:
                    file = new File(getWallpaperFrameOutputZipFile(context,background_id).getParentFile()
                            .getPath() + File.separatorChar + "WaterfallLWP_3" + File.separatorChar + "waterfall", "Landscape");
                    break;
                case 5:
                    file = new File(getWallpaperFrameOutputZipFile(context,background_id).getParentFile()
                            .getPath() + File.separatorChar + "WaterfallLWP_4" + File.separatorChar + "waterfall", "Landscape");
                    break;
                case 6:
                    file = new File(getWallpaperFrameOutputZipFile(context,background_id).getParentFile()
                            .getPath() + File.separatorChar + "WaterfallLWP_5" + File.separatorChar + "waterfall", "Landscape");
                    break;

            }
        }
        Log.d("Waterfall", "Got files:" +file);
		if (file != null && file.exists()) {
			File[] files = file.listFiles();
			if (files != null && files.length > 0) {
				return files;
			}
		}
		return null;
	}


	public interface DownloadListener {
		public void onFileDownloaded(String result);

		public void onDownloadProgressUpdate(int progress);

		public void onDownloadFailed();

		public void onNetworkProblem();

	}

	private class ZipFileDownloader extends AsyncTask<String, Integer, String> {

		private Context mContext;
		private DownloadListener downloadListener;
		private boolean isDialogShown = false;
		private ProgressDialog dialog;
		private File fileOutput;

		public ZipFileDownloader(Context context, DownloadListener listener,
				boolean showProgressDialog, File outputFile) {
			fileOutput = outputFile;
			mContext = context;
			downloadListener = listener;
			isDialogShown = showProgressDialog;

			if (isDialogShown) {
				dialog = new ProgressDialog(mContext);
				dialog.setMessage("Downloading...");
				dialog.setCancelable(false);
				dialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
				dialog.setProgress(0);

			}
		}

		@Override
		protected void onPreExecute() {

			if (isDialogShown && dialog != null) {
                dialog.show();
			}
			super.onPreExecute();
		}

		@Override
		protected String doInBackground(String... params) {
			int count;
			if (isNetworkAvailable()) {
				try {

					URL url = new URL(params[0]);
					URLConnection conection = url.openConnection();
					conection.connect();
					// getting file length
					int lenghtOfFile = conection.getContentLength();

					// input stream to read file - with 8k buffer
					InputStream input = new BufferedInputStream(
							url.openStream(), 8192);

					// Output stream to write file
					OutputStream output = new FileOutputStream(fileOutput);

					byte data[] = new byte[1024];

					long total = 0;

					while ((count = input.read(data)) != -1) {
						total += count;
						// publishing the progress....
						// After this onProgressUpdate will be called
						publishProgress((int) ((total * 100) / lenghtOfFile));

						// writing data to file
						output.write(data, 0, count);
					}

					// flushing output
					output.flush();

					// closing streams
					output.close();
					input.close();

					unZip(fileOutput, fileOutput.getParentFile());

					return fileOutput.getParentFile().getPath();
				} catch (Exception exception) {
					exception.printStackTrace();
					// delete downloading temp file if downloading failled.
					if (fileOutput != null && fileOutput.exists()) {
						// fileOutput.delete();
					}
					downloadListener.onDownloadFailed();
				}

			} else {
				downloadListener.onNetworkProblem();
			}
			return null;
		}

		@Override
		protected void onProgressUpdate(Integer... values) {
			if (dialog != null && dialog.isShowing()) {
				dialog.setProgress(values[0]);
			}
			if (downloadListener != null) {
				downloadListener.onDownloadProgressUpdate(values[0]);
			}
			super.onProgressUpdate(values);
		}

		@Override
		protected void onPostExecute(String result) {
			if (isDialogShown && dialog != null && dialog.isShowing()) {
				dialog.dismiss();
			}
			if (result != null) {
				downloadListener.onFileDownloaded(result);
			}

			super.onPostExecute(result);
		}

		private boolean isNetworkAvailable() {
			ConnectivityManager connectivityManager = (ConnectivityManager) mContext
					.getSystemService(Context.CONNECTIVITY_SERVICE);
			NetworkInfo activeNetworkInfo = connectivityManager
					.getActiveNetworkInfo();
			return activeNetworkInfo != null && activeNetworkInfo.isConnected();
		}

		private void unZip(File file, File destination) {
			try {
				ZipInputStream zin = new ZipInputStream(new FileInputStream(
						file));
				String workingDir = destination.getPath() + File.separatorChar;

				byte buffer[] = new byte[4096];
				int bytesRead;
				ZipEntry entry = null;

				while ((entry = zin.getNextEntry()) != null) {

					if (entry.isDirectory()) {
						File dir = new File(workingDir, entry.getName());
						if (!dir.exists()) {
							dir.mkdirs();
						}
						Log.e("zip entry dir", entry.getName() + "");
					} else {
						File file2 = new File(workingDir + entry.getName());
						if (file2 != null && !file2.getParentFile().exists())
							file2.getParentFile().mkdirs();
						FileOutputStream fos = new FileOutputStream(file2);
						while ((bytesRead = zin.read(buffer)) != -1) {
							fos.write(buffer, 0, bytesRead);
						}
						Log.e("zip entry file", entry.getName() + "");
						fos.close();

					}
				}
              	zin.close();
			} catch (Exception exception) {
				exception.printStackTrace();
			}
		}

	}

    public void deleteZipFile(String path) {
        try {
            ZipFile zFile = new ZipFile(path);
            zFile.close();
            File file = new File(path);
            file.delete();
        }catch(Exception ee) {
            Log.d("WallpaperFrame" ,ee.toString());
        }
    }

}
