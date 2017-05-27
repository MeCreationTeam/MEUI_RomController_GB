package com.meui.RomCtrl;
import android.content.*;
import android.os.*;
import android.preference.*;

/**
 * The main activity for MEUIRomCtrl.
 */

public class MainActivity extends PreferenceActivity 
{
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
		getPreferences(Context.MODE_WORLD_READABLE|Context.MODE_WORLD_WRITEABLE);
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.main);
	}
}
