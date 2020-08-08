package com.meui.RomCtrl;
import android.content.*;
import android.preference.*;
import android.provider.*;
import android.util.*;
import android.widget.*;
import java.util.*;
import android.os.*;

/**
 * This is used to control something in lockscreen.
 * @author zhaozihanzzh
 */

public final class Lockscreen extends PreferenceActivity implements CheckBoxPreference.OnPreferenceChangeListener{

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		addPreferencesFromResource(R.xml.lockscreen);
		findPreference("config_beam_screen_on").setOnPreferenceChangeListener(this);
		findPreference("config_beam_screen_off").setOnPreferenceChangeListener(this);
	}

	@Override
	public boolean onPreferenceChange(Preference p1, Object p2) {
		Settings.System.putInt(getContentResolver(), p1.getKey(), ((CheckBoxPreference) p1).isChecked() ? 0 : 1);
		return true;
	}

/*
    @Override
    protected void save(String changedKey) {
		switch (changedKey) {
			case "ls_lunar_color":
			case "ls_st24_textcolor":
				Settings.System.putInt(mResolver, changedKey, getPreferenceManager().getSharedPreferences().getInt(changedKey, 0));
				break;
			case "ls_lunar_size":
			case "ls_st24_textsize":
				Settings.System.putFloat(mResolver, changedKey, getPreferenceManager().getSharedPreferences().getFloat(changedKey, 0));
				break;
			case "ls_lunar":
			case "ls_st24_show":
				Settings.System.putInt(mResolver, changedKey, getPreferenceManager().getSharedPreferences().getBoolean(changedKey, false) ?1: 0);
				break;
			default:
				break;
		}
	}*/

}
