package com.meui.RomCtrl;
import android.preference.*;
import android.os.*;
import android.content.*;

/**
 * This is used as the superclass of the classes which are used for putting values in Settings.System
 * @author zhaozihanzzh
 */

abstract public class BaseSettings extends PreferenceActivity
{
	private SharedPreferences meui;
	private ContentResolver CR;
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		addPreferencesFromResource(getXmlId());
		
		CR=getContentResolver();
		meui=getPreferenceManager().getSharedPreferences();
		meui.registerOnSharedPreferenceChangeListener(new SharedPreferences. OnSharedPreferenceChangeListener(){
				@Override
				public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key)
				{
					save(CR,meui);
				}
			});
	}
    abstract protected int getXmlId();
	abstract protected void save(final ContentResolver CR, final SharedPreferences meui);
	private final SharedPreferences.OnSharedPreferenceChangeListener mListener = new SharedPreferences.OnSharedPreferenceChangeListener()
	{
		@Override
		public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key)
		{
			save(CR,meui);
		}
	};
	@Override
	protected void onResume()
	{
		PreferenceManager.getDefaultSharedPreferences(getApplicationContext()).registerOnSharedPreferenceChangeListener(mListener);
		super.onResume();
	}

	@Override
	protected void onPause()
	{
		PreferenceManager.getDefaultSharedPreferences(getApplicationContext()).unregisterOnSharedPreferenceChangeListener(mListener);
		super.onPause();
	}
}
