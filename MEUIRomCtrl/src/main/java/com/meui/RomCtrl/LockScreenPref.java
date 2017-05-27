package com.meui.RomCtrl;
import android.content.*;
import android.preference.*;
import android.provider.*;
import android.util.*;
import android.widget.*;
import java.util.*;

/**
 * This is used to control something in lockscreen.
 * @author zhaozihanzzh
 */

public class LockScreenPref extends BaseProvider
{

	@Override
	protected int getXmlId()
	{
		return R.xml.lockscreen;
	}

	@Override
	protected void save()
	{
		final ContentResolver CR=getContentResolver();
		SharedPreferences meui=getSharedPreferences("com.meui.RomCtrl_preferences", Context.MODE_WORLD_READABLE);
		final Map<String,?> mdhp=meui.getAll();
		for (Map.Entry<String,?> entry:mdhp.entrySet())
		{
			switch (entry.getKey().toString())
			{
				case "ls_lunar_color":
					Settings.System.putInt(CR, entry.getKey().toString(), Integer.valueOf(entry.getValue()));
					break;
				case "ls_lunar_size":
					Settings.System.putFloat(CR, entry.getKey(), Float.parseFloat(entry.getValue().toString()));
					break;
				case "ls_lunar":
					final CheckBoxPreference cbp=(CheckBoxPreference)findPreference(entry.getKey().toString());
					Settings.System.putInt(CR, entry.getKey().toString(), cbp.isChecked() ?1: 0);
					break;
				default:
					Toast.makeText(this, "存在意外的选项,程序可能已被修改。", Toast.LENGTH_LONG);
					Log.d(LockScreenPref.class.getSimpleName(), "Unexpected item:" + entry.getKey().toString());
					break;
			}
		}
	}
}
