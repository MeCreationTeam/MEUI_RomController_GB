package com.meui.RomCtrl;
import android.content.*;
import android.os.*;
import android.preference.*;
import android.net.*;
import java.util.prefs.*;
import java.util.*;
import android.provider.*;
import android.widget.*;
import android.util.*;

public class RoundProgress extends PreferenceActivity
{
	/**
	 * This Preference activity is used to control the Materialish ProgressBar in MEUI.
	 * When the preferences change, it will save settings to system.
	 * @author zhaozihanzzh
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		SharedPreferences meui=getSharedPreferences("com.meui.RomCtrl_preferences", Context.MODE_WORLD_READABLE);
		meui.getString("share_screenshot", "");
		super.onCreate(savedInstanceState);
		addPreferencesFromResource(R.xml.wheel_progress);
		meui.registerOnSharedPreferenceChangeListener(new SharedPreferences. OnSharedPreferenceChangeListener(){
				@Override
				public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key)
				{
					save();
				}
			});
	}

	private SharedPreferences.OnSharedPreferenceChangeListener mListener = new SharedPreferences.OnSharedPreferenceChangeListener() {

		@Override
		public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key)
		{
			save();
		}
	};

    private void save()
	{
		final ContentResolver CR=getContentResolver();
		SharedPreferences meui=getSharedPreferences("com.meui.RomCtrl_preferences", Context.MODE_WORLD_READABLE);
		final Map<String,?> mdhp=meui.getAll();
		for (Map.Entry<String,?> entry:mdhp.entrySet())
		{
			switch (entry.getKey().toString())
			{
				case "mdhp_circleRadius":
				case "mdhp_barWidth":
				case "mdhp_rimWidth":
				case "mdhp_barColor":
				case "mdhp_rimColor":
					Settings.System.putInt(CR, entry.getKey().toString(), Integer.valueOf(entry.getValue()));
					break;
				case "mdhp_baseSpinSpeed":
					Settings.System.putFloat(CR, entry.getKey(), Float.parseFloat(entry.getValue().toString()) / 1000);
					break;
				case "mdhp_cycleTime":
					Settings.System.putString(CR, entry.getKey(), entry.getValue().toString());
					break;
				case "mdhp_linear":
				case "mdhp_quick":
				case "mdhp_cache":
					CheckBoxPreference cbp=(CheckBoxPreference)findPreference(entry.getKey().toString());
					Settings.System.putInt(CR, entry.getKey().toString(), cbp.isChecked() ?1: 0);
					break;
				default:
					Toast.makeText(this, "存在意外的选项,程序可能已被修改。", Toast.LENGTH_LONG);
					Log.d(RoundProgress.class.getSimpleName(), "Unexpected item:" + entry.getKey().toString());
					break;
			}
		}
	}

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
