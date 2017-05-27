package com.meui.RomCtrl;
import android.os.*;
import android.preference.*;

/**
 * This is used to show about info.
 */

public class About extends PreferenceActivity
{
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		addPreferencesFromResource(R.xml.about);
		
	}
}
