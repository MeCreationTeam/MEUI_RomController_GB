package com.meui.RomCtrl;
import android.preference.*;
import android.os.*;
import android.content.*;

/**
 * This is used as the superclass of the classes which are used for putting values in Settings.System
 * @author zhaozihanzzh
 */

abstract public class BaseSettings extends PreferenceActivity {
    protected ContentResolver mResolver;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(getXmlId());
        mResolver = getContentResolver();
    }
    /**
     * This abstract method is used to get the xml id of preference.
     * @return The id of xml file.
     */
    abstract protected int getXmlId();
    /**
     * This abstract method will be called when the SharedPreference changes.
     */
    abstract protected void save(String key);
    private final SharedPreferences.OnSharedPreferenceChangeListener mListener = new SharedPreferences.OnSharedPreferenceChangeListener() {
        @Override
        public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
            save(key);
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
