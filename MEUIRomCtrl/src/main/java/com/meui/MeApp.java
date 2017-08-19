package com.meui;
import android.app.*;
import android.content.*;
import android.util.*;
import com.meui.RomCtrl.*;
import android.os.*;

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

        StringBuilder logWrongInfo = new StringBuilder();
        logWrongInfo.append("Model: " + Build.MODEL + "    SDK: " + Build.VERSION.SDK_INT
                            + "\n" + "Fingerprint: " + Build.FINGERPRINT
                            + "\n" + ex.toString());
        for(StackTraceElement e : ex.getStackTrace()){
            logWrongInfo.append("\n     at " +e.toString());
        }
        intent.putExtra("log", logWrongInfo.toString());
        startActivity(intent);
        System.exit(0);
    }
 }
