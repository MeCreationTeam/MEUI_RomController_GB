package com.meui.RomCtrl;
import android.content.*;
import android.os.*;
import android.preference.*;
import java.io.*;
import android.widget.*;
import android.util.*;

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
		
		SharedPreferences meui= getPreferenceManager().getSharedPreferences();
		meui.getBoolean("meui", true);
		EditTextPreference pathPref=(EditTextPreference)findPreference("path");
		final String lastPath=meui.getString("path","NULL");
		pathPref.setText(lastPath);
		Toast.makeText(this,lastPath,Toast.LENGTH_SHORT).show();
		pathPref.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {
				@Override
				public boolean onPreferenceChange(Preference preference, Object newValue)
				{
					Log.wtf("MEUIss","啊啊，新值："+newValue.toString());
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
						Log.wtf("MEUIss","啊啊，错误"+e.toString());
						Toast.makeText(getApplicationContext(), "无法读取目录！", Toast.LENGTH_LONG).show();
						return false;
					}
					
					return true;
					//return true才会把新值保存起来
				}
			});
	}
}
