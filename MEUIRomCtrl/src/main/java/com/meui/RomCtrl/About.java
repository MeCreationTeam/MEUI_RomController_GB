package com.meui.RomCtrl;
import android.os.*;
import android.preference.*;

public class About extends PreferenceActivity
{
	/**
	 * This is used to show about info.
	 */
	private Preference aboutPref;
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		addPreferencesFromResource(R.xml.about);
		aboutPref=findPreference("changelogs");
		aboutPref.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener(){
			@Override
			public boolean onPreferenceClick(Preference p1){
				
				return true;
			}
		});
	}
	
}
