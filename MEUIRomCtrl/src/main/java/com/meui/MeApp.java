package com.meui;
import android.app.*;
import android.content.*;
import android.util.*;
import com.meui.RomCtrl.*;

/**
 * Application class for catching unexpected exceptions.
 * @author zhaozihanzzh
 * Original thread:
 * http://www.open-open.com/lib/view/open1373897468607.html
 */

public class MeApp extends Application implements Thread.UncaughtExceptionHandler {
	@Override
	public void onCreate() {
		super.onCreate();
		//设置Thread Exception Handler
		Thread.setDefaultUncaughtExceptionHandler(this);
	}

	@Override
	public void uncaughtException(Thread thread, Throwable ex) {
		final Intent intent = new Intent(this, Crash.class);
		intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP |  
						Intent.FLAG_ACTIVITY_NEW_TASK);
		intent.putExtra("log",ex.toString());
		Log.w("MEUI",ex);
		startActivity(intent);
		System.exit(0);
	}
 }
