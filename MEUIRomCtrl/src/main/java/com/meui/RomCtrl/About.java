package com.meui.RomCtrl;
import android.os.*;
import android.preference.*;

public class About extends PreferenceActivity
{

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		// TODO: Implement this method
		super.onCreate(savedInstanceState);
		addPreferencesFromResource(R.xml.about);
	}
	
}
