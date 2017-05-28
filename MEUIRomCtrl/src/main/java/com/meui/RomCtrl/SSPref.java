package com.meui.RomCtrl;
import android.content.*;
import android.os.*;
import android.preference.*;
import java.io.*;
import android.widget.*;

/**
 * This is used to control MEUI CMScreenshot.
 * @see <a href="https://github.com/zhaozihanzzh/MEUI_GingerBread_CMScreenshot">MEUI CM Screenshot</a>
 * @author zhaozihanzzh
 */

public class SSPref extends PreferenceActivity
{
    @Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		addPreferencesFromResource(R.xml.screenshot);
		SharedPreferences meui= getPreferences(Context.MODE_WORLD_READABLE);
		meui.getString("share_screenshot", "");
		PreferenceScreen ssScreen=getPreferenceScreen();
		Preference pathPref=ssScreen.findPreference("path");
		pathPref.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {
				@Override
				public boolean onPreferenceChange(Preference preference, Object newValue)
				{
					boolean result=false;
					if ((String)newValue == "")
					{
						Toast.makeText(getApplicationContext(), "路径不能为空！", Toast.LENGTH_SHORT).show();
						return result;
					}
					String newPath=Environment.getExternalStorageDirectory().toString() + "/" + (String)newValue;
					try
					{
						File newPathFile=new File(newPath);
						if (!newPathFile.exists()) newPathFile.mkdirs();
						result = true;
						newPathFile = null;
					}
					catch (Exception ex)
					{
						Toast.makeText(SSPref.this, "无法读取目录！", Toast.LENGTH_LONG).show();
						result = false;
					}
					return result;
					//return true才会把新值保存起来
				}
			});
	}
}
