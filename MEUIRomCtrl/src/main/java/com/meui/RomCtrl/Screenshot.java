package com.meui.RomCtrl;
import android.content.*;
import android.os.*;
import android.preference.*;
import android.provider.*;
import android.util.*;
import android.widget.*;
import java.io.*;
import java.util.*;

/**
 * This is used to control MEUI CMScreenshot.
 * @see <a href="https://github.com/zhaozihanzzh/MEUI_GingerBread_CMScreenshot">MEUI CM Screenshot</a>
 * @author zhaozihanzzh
 */

public final class Screenshot extends BaseSettings
{
	private final String PATH_ERROR="截图路径修改无效！";
	@Override
	protected int getXmlId(){
		return R.xml.screenshot;
	}

	@Override
	protected void save(ContentResolver CR, SharedPreferences meui)
	{
		final EditTextPreference pathEditor=(EditTextPreference)findPreference("ss_path");
		final String lastValue=Settings.System.getString(CR,"ss_path");
		
		final Map<String,?> ss=meui.getAll();
		for (Map.Entry<String,?> entry:ss.entrySet())
		{
			final String KEY=entry.getKey().toString();
			switch (KEY)
			{
				case "ss_path":
					final String VALUE=entry.getValue().toString();
					final String newPath=Environment.getExternalStorageDirectory().toString() + "/" + VALUE;
					try
					{
						File newPathFile=new File(newPath);
						boolean can=false;
						final boolean exist=newPathFile.exists();
						if (!exist) can=newPathFile.mkdirs();
						newPathFile = null;
						if(exist||can)Settings.System.putString(CR,KEY,VALUE);
						else{
							pathEditor.setText(lastValue);
							Toast.makeText(this,PATH_ERROR, Toast.LENGTH_LONG).show();
						}
					}
					catch (Exception e)
					{
						Toast.makeText(this, PATH_ERROR, Toast.LENGTH_LONG).show();
						pathEditor.setText(lastValue);
						Settings.System.putString(CR,KEY,lastValue);
					}
					break;
				case "screenshot_format":
					Settings.System.putString(CR,KEY,entry.getValue().toString());
					break;
				case "screenshot_quality":
				case "screenshot_delay":
					Settings.System.putInt(CR, KEY, Integer.valueOf(entry.getValue()));
					break;
				case "share_screenshot":
					final CheckBoxPreference cbp=(CheckBoxPreference)findPreference(KEY);
					Settings.System.putInt(CR, KEY, cbp.isChecked() ?1: 0);
					break;
				default:
					break;
			}
		}
	}
}
