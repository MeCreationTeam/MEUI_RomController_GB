package com.meui.RomCtrl;
import android.content.*;
import android.os.*;
import android.preference.*;
import java.io.*;
import android.widget.*;

public class SSPref extends PreferenceActivity
{
    private CheckBoxPreference shareSS;
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		// TODO: Implement this method

		super.onCreate(savedInstanceState);

		addPreferencesFromResource(R.xml.screenshot);

		SharedPreferences meui= getPreferences(Context.MODE_WORLD_READABLE);
		meui.getString("share_screenshot","");
		PreferenceScreen ssScreen=getPreferenceScreen();
		Preference pathPref=ssScreen.findPreference("path");
		EditTextPreference pathPrefEdit=(EditTextPreference) pathPref;
		pathPrefEdit.setDialogMessage("请输入路径中/mnt/sdcard/后面的部分。\n默认:DCIM/Screenshots");
		shareSS=(CheckBoxPreference)ssScreen.findPreference("share_screenshot");
		pathPref.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {
				@Override
				public boolean onPreferenceChange(Preference preference, Object newValue) {
					boolean result=false;
					if((String)newValue==""){
						Toast.makeText(getApplicationContext(),"路径不能为空！",Toast.LENGTH_SHORT).show();
						return result;
					}
					String newPath=Environment.getExternalStorageDirectory().toString()+"/"+(String)newValue;
					try{File newPathFile=new File(newPath);
					if (!newPathFile.exists()) newPathFile.mkdirs();
					result=true;
					newPathFile=null;
					}
					catch (Exception ex)
					{
						
						Toast.makeText(SSPref.this,"无法读取目录！",Toast.LENGTH_LONG).show();
						result=false;
						}
					
					return result;
					//return true才会把新值保存起来
				}});
}
}

