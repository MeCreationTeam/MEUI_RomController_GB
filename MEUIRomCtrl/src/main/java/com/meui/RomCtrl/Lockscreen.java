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

public final class Lockscreen extends BaseSettings
{
	
	@Override
	protected int getXmlId()
	{
		return R.xml.lockscreen;
	}

	@Override
	protected void save(final ContentResolver CR, final SharedPreferences meui)
	{
		final Map<String,?> ls=meui.getAll();
		for (Map.Entry<String,?> entry:ls.entrySet())
		{
			switch (entry.getKey().toString())
			{
				case "ls_lunar_color":
				case "ls_st24_textcolor":
					Settings.System.putInt(CR, entry.getKey().toString(), Integer.valueOf(entry.getValue()));
					break;
				case "ls_lunar_size":
				case "ls_st24_textsize":
					Settings.System.putFloat(CR, entry.getKey().toString(), Float.parseFloat(entry.getValue().toString()));
					break;
				case "ls_lunar":
				case "ls_st24_show":
					final CheckBoxPreference cbp=(CheckBoxPreference)findPreference(entry.getKey().toString());
					Settings.System.putInt(CR, entry.getKey().toString(), cbp.isChecked() ?1: 0);
					break;
				default:
					break;
			}
		}
	}
}
