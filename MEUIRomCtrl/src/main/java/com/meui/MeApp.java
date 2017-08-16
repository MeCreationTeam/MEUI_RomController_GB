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

public final class MeApp extends Application implements Thread.UncaughtExceptionHandler {
    @Override
    public void onCreate() {
        super.onCreate();
        //设置Thread Exception Handler
        Thread.setDefaultUncaughtExceptionHandler(this);
    }

    @Override
    public void uncaughtException(Thread thread, Throwable ex) {
        Log.e("MEUI","MEUIRomCtrl:",ex);
        
        final Intent intent = new Intent(this, Crash.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP |  
                        Intent.FLAG_ACTIVITY_NEW_TASK);

        String logWrongInfo = "Thread: "+thread.getName() + "\n" + ex.toString();
        for(StackTraceElement e : ex.getStackTrace()){
            logWrongInfo += "\n     at " +e.toString();
        }
        intent.putExtra("log", logWrongInfo);
        startActivity(intent);
        System.exit(0);
    }
 }
