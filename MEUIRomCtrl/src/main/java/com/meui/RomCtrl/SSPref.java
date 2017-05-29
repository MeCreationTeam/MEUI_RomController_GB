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

public class SSPref extends BaseProvider
{
	private final String PATH_ERROR="截图路径修改无效！";
	@Override
	protected int getXmlId(){
		return R.xml.screenshot;
	}

	@Override
	protected void save(ContentResolver CR, SharedPreferences meui)
	{
		EditTextPreference pathEditor=(EditTextPreference)findPreference("ss_path");
		final String lastValue=Settings.System.getString(CR,"ss_path");
		
		final Map<String,?> ss=meui.getAll();
		for (Map.Entry<String,?> entry:ss.entrySet())
		{
			final String KEY=entry.getKey().toString();
			switch (KEY)
			{
				case "ss_path":
					final String VALUE=entry.getValue().toString();
					String newPath=Environment.getExternalStorageDirectory().toString() + "/" + VALUE;
					try
					{
						File newPathFile=new File(newPath);
						boolean can=false;
						boolean exist=newPathFile.exists();
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
					Toast.makeText(this, "存在意外的选项,程序可能已被修改。", Toast.LENGTH_LONG);
					Log.d(LockScreenPref.class.getSimpleName(), "Unexpected item:" + entry.getKey().toString());
					break;
			}
		}
	}
	/*
    @Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		
		SharedPreferences meui=getPreferenceManager().getSharedPreferences();
		EditTextPreference pathPref=(EditTextPreference)findPreference("ss_path");
		final String lastPath=meui.getString("ss_path","DCIM/Screenshots");
		//pathPref.setText(lastPath);
		Toast.makeText(this,lastPath,Toast.LENGTH_SHORT).show();
		pathPref.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {
				@Override
				public boolean onPreferenceChange(Preference preference, Object newValue)
				{
					if ((String)newValue == "")
					{
						Toast.makeText(SSPref.this, "路径不能为空！", Toast.LENGTH_SHORT).show();
						return false;
					}
					String newPath=Environment.getExternalStorageDirectory().toString() + "/" + (String)newValue;
					try
					{
						File newPathFile=new File(newPath);
						if (!newPathFile.exists()) newPathFile.mkdirs();
						newPathFile = null;
					}
					catch (Exception e)
					{
						Toast.makeText(getApplicationContext(), "无法读取目录！", Toast.LENGTH_LONG).show();
						return false;
					}
					return true;
					//return true才会把新值保存起来
				}
			});
	}*/
}
