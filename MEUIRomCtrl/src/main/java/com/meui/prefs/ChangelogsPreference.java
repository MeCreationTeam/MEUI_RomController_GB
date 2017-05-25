package com.meui.prefs;
import android.app.*;
import android.content.*;
import android.content.pm.*;
import android.preference.*;
import android.util.*;
import android.widget.*;
import java.io.*;
import java.net.*;
import android.os.*;

public class ChangelogsPreference extends DialogPreference
{
	/**
	 * A DialogPreference which shows Changelog from GitHub.
	 * @author zhaozihanzzh
	 */
	
	private final String FRESHING="正在获取……";
	private String mChangelog=FRESHING;
	private Context context;
	private AlertDialog mDialog;
	private TextView mText;
	
	public ChangelogsPreference(Context c,AttributeSet attrs){
		super(c,attrs);
		context=c;
	}
	public ChangelogsPreference(Context c,AttributeSet attrs,int defStyle){
		super(c,attrs,defStyle);
		context=c;
	}

	@Override
	protected void onPrepareDialogBuilder(AlertDialog.Builder builder)
	{
		mText=new TextView(context);
		mText.setText(mChangelog);
		final int padding=Build.VERSION.SDK_INT>20 ? 48: 6;
		mText.setPadding(padding,8,padding-6,0);
		try
		{
		final int VERSION=context.getPackageManager().getPackageInfo(context.getPackageName(), 0).versionCode;
		
		if (mChangelog == FRESHING) loadChangelogs(VERSION);
		mDialog=builder.setCancelable(true)
			  					  .setNegativeButton("",null)
								  .setView(mText)
								  .create();
		}
		catch (PackageManager.NameNotFoundException e)
		{
			e.printStackTrace();
		}
	}
	/**
	 * This is used to get Changelogs according to version code from GitHub in a new thread.
	 * @param versionCode the version of changelogs that we want to load.
	 */
	private void loadChangelogs(final int versionCode){
		new Thread(new Runnable(){

				@Override
				public void run()
				{
					HttpURLConnection connection=null;
					BufferedReader reader=null;
					try
					{
						URL url=new URL("https://raw.githubusercontent.com/zhaozihanzzh/MEUI_OTA_Gingerbread/Changelogs/changelogs"+versionCode);
						connection=(HttpURLConnection)url.openConnection();
						connection.setReadTimeout(8000);
						connection.setRequestMethod("GET");
						InputStream in=connection.getInputStream();
						reader=new BufferedReader(new InputStreamReader(in));
						StringBuilder builder=new StringBuilder();
						String line;
						while((line=reader.readLine())!=null) builder.append(line);
					 	mChangelog=builder.toString().replace("&","\n");
						showChangelog();
					}
					catch (Exception e)
					{
						mText.setText("加载失败，请检查网络连接。");
						Log.e("MEUI",e.toString());
					}
					finally{
						try
						{
							if (reader != null)reader.close();
							if (connection!=null) connection.disconnect();
						}
						catch (IOException e)
						{
							e.printStackTrace();
						}
					}
				}
			}).start();
	}
	/**
	 * This is used to change the text of TextView in AlertDialog on Ui thread.
	 */
	private void showChangelog(){
		Activity activity=(Activity) context;
		activity.runOnUiThread(new Runnable(){

				@Override
				public void run()
				{
					mText.setText(mChangelog);
				}
		});
	}
}
