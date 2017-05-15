package com.meui.RomCtrl;
import android.content.res.*;
import android.os.*;
import android.preference.*;
import android.util.*;
import android.provider.*;
import android.content.*;
import android.widget.*;

public class LockScreenPref extends PreferenceActivity
{
	/**
	 * This is used to control something in lockscreen.
	 * @author zhaozihanzzh
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		addPreferencesFromResource(R.xml.lockscreen);
		final CheckBoxPreference mLunar=(CheckBoxPreference)getPreferenceScreen().findPreference("show_lslunar");
		mLunar.setOnPreferenceChangeListener(new CheckBoxPreference.OnPreferenceChangeListener(){
			@Override
			public boolean onPreferenceChange(Preference p1,Object p2){
				final int result=getSharedPreferences("com.meui.RomCtrl_preferences",Context.MODE_WORLD_READABLE).getBoolean(p1.getKey(),false)?0:1;
				//上一行用0和1而不用1和0是取反，因为此时还没有return true，获取的是上次的值，而change后应该正好相反。
				Settings.System.putInt(getContentResolver(),"ls_lunar",result);
				return true;
			}
		});
	}
}
