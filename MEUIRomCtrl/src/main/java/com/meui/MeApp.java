package com.meui;
import android.app.*;
import android.content.*;
import com.meui.RomCtrl.*;

public class MeApp extends Application implements Thread.UncaughtExceptionHandler {  
	@Override  
	public void onCreate() {  
		super.onCreate();  
		//设置Thread Exception Handler  
		Thread.setDefaultUncaughtExceptionHandler(this);  
	}  

	@Override  
	public void uncaughtException(Thread thread, Throwable ex) {  
		//System.out.println("uncaughtException");  
		
		Intent intent = new Intent(this, Crash.class);  
		intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP |  
						Intent.FLAG_ACTIVITY_NEW_TASK);  
		startActivity(intent);
		System.exit(0);
	}  
 }
