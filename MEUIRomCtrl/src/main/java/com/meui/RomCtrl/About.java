package com.meui.RomCtrl;
import android.os.*;
import android.preference.*;

public class About extends PreferenceActivity
{
	/**
	 * This is used to show about info.
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		addPreferencesFromResource(R.xml.about);
	}
	
}
