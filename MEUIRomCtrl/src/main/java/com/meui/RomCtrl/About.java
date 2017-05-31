package com.meui.RomCtrl;
import android.content.*;
import android.os.*;
import android.preference.*;
import android.widget.*;

/**
 * This is used to show about info and debug info.
 * @author zhaozihanzzh
 */

public class About extends PreferenceActivity
{
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		addPreferencesFromResource(R.xml.about);
		final Preference sendMap=findPreference("send_map");
		sendMap.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener(){
			@Override
			public boolean onPreferenceClick(Preference p1){
				final SharedPreferences meui=getPreferenceManager().getSharedPreferences();
				Toast.makeText(About.this, meui.getAll().toString(),Toast.LENGTH_LONG).show();
				return true;
			}
		});
	}
}
