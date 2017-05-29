package com.meui.RomCtrl;
import android.os.*;
import android.preference.*;
import android.content.*;
import android.widget.*;

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
		Preference sendMap=findPreference("send_map");
		sendMap.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener(){
			@Override
			public boolean onPreferenceClick(Preference p1){
				SharedPreferences meui=getSharedPreferences("com.meui.RomCtrl_preferences", Context.MODE_WORLD_READABLE);
				Toast.makeText(About.this, meui.getAll().toString(),Toast.LENGTH_SHORT).show();
				return true;
			}
		});
	}
}
