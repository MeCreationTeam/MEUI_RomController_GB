package com.meui.RomCtrl;
import android.content.*;
import android.os.*;
import android.preference.*;

/**
 * The main activity for MEUIRomCtrl.
 * @author zhaozihanzzh
 */

public class MainActivity extends PreferenceActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.main);
    }
}
