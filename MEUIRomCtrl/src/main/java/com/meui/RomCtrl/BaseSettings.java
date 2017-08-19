package com.meui.RomCtrl;
import android.preference.*;
import android.os.*;
import android.content.*;

/**
 * This is used as the superclass of the classes which are used for putting values in Settings.System
 * @author zhaozihanzzh
 */

abstract public class BaseSettings extends PreferenceActivity {
    protected SharedPreferences meuiPrefs;
    protected ContentResolver mResolver;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(getXmlId());

        mResolver = getContentResolver();
        meuiPrefs = getPreferenceManager().getSharedPreferences();
        meuiPrefs.registerOnSharedPreferenceChangeListener(new SharedPreferences. OnSharedPreferenceChangeListener(){
                @Override
                public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
                    save();
                }
            });
    }
    abstract protected int getXmlId();
    abstract protected void save();
    private final SharedPreferences.OnSharedPreferenceChangeListener mListener = new SharedPreferences.OnSharedPreferenceChangeListener() {
        @Override
        public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
            save();
        }
    };
    @Override
    protected void onResume() {
        PreferenceManager.getDefaultSharedPreferences(getApplicationContext()).registerOnSharedPreferenceChangeListener(mListener);
        super.onResume();
    }

    @Override
    protected void onPause() {
        PreferenceManager.getDefaultSharedPreferences(getApplicationContext()).unregisterOnSharedPreferenceChangeListener(mListener);
        super.onPause();
    }
}
