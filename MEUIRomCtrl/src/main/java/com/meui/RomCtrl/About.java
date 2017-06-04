package com.meui.RomCtrl;
import android.content.*;
import android.os.*;
import android.preference.*;
import android.util.*;

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
		sendMap.setSummary(null);
		sendMap.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener(){
			@Override
			public boolean onPreferenceClick(Preference p1){
				if(p1.getSummary()==null){
					p1.setTitle("隐藏Map");
					final String meui=getPreferenceManager().getSharedPreferences().getAll().toString();
					Log.d("MEUI",meui);
					p1.setSummary(meui);
				}
				else{
					p1.setTitle("显示Map");
					p1.setSummary(null);
				}
				return false;
			}
		});
	}
}
