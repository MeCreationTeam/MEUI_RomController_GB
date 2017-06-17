package com.meui.RomCtrl;
import android.content.*;
import android.content.pm.*;
import android.database.*;
import android.net.*;
import android.os.*;
import android.preference.*;
import com.meui.*;
import java.util.*;
import net.margaritov.preference.colorpicker.*;

/**
 * The Preference Activity of Status Bar Color.
 * http://m.2cto.com/kf/201504/387107.html
 * @author zhaozihanzzh
 */
 
public class StatusBarColor extends PreferenceActivity
{
	private MeProvider provider=new MeProvider();
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		
		addPreferencesFromResource(R.xml.status_bar_color);
		final PreferenceScreen appArea=(PreferenceScreen)findPreference("app_area");
		final PackageManager pm = getPackageManager();
		// Return a List of all packages that are installed on the device.
		List packages = pm.getInstalledPackages(0);
		for (final PackageInfo packageInfo : packages) {
			
				//final AppColorPreference info = new AppColorPreference(StatusBarColor.this,null);
				final PreferenceScreen info = new PreferenceScreen(StatusBarColor.this,null);
				info.setTitle(packageInfo.applicationInfo.loadLabel(pm).toString());
				info.setSummary(packageInfo.packageName);
				appArea.addPreference(info);
				
				final CheckBoxPreference dependent=new CheckBoxPreference(StatusBarColor.this);
				dependent.setKey(packageInfo.packageName+"_dependent");
				dependent.setTitle("使用独立设置");
				info.addPreference(dependent);
				dependent.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener(){
					@Override
					public boolean onPreferenceChange(Preference preference, Object values){
						// TODO: 存dependent.isChecked()
						// 如果不存在：
						boolean exist=false;
						final Cursor cursor=provider.query(MeProvider.CONTENT_URI,null,null,null,null);
						cursor.moveToFirst();
						do {
							if(packageInfo.packageName== cursor.getString( cursor.getColumnIndex("packageName"))){
								cursor.updateInt(cursor.getColumnIndex("color"),dependent.isChecked()?1:0);
								exist=true;
								break;}
						} while (cursor.moveToNext());
						
						if(!exist){
							final ContentValues value=new ContentValues();
							value.put("packageName",preference.getSummary().toString());
							value.put("hasColor",!dependent.isChecked() ?1:0);
							provider.insert(Uri.parse("com.meui.RomCtrl/BarColors"),value);
						}
						
						return true;
					}
				});
			
				final ColorPickerPreference colorPicker=new ColorPickerPreference(StatusBarColor.this);
				colorPicker.setTitle("设置状态栏颜色");
				colorPicker.setKey(packageInfo.packageName+"_color");
				info.addPreference(colorPicker);
				colorPicker.setDependency(dependent.getKey());
				colorPicker.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener(){
					
					@Override
					public boolean onPreferenceChange(Preference preference, Object values){
						// TODO: 存储Integer.parseInt(values);
						//如果不存在：
						final Cursor cursor=provider.query(MeProvider.CONTENT_URI,null,null,null,null);
						//boolean exist=false;
						cursor.moveToFirst();
						do {
							if(packageInfo.packageName== cursor.getString( cursor.getColumnIndex("packageName"))){
								cursor.updateInt(cursor.getColumnIndex("color"),Integer.parseInt(values.toString()));
								//exist=true;
								break;}
						} while (cursor.moveToNext());
						return true;
					}
				});
		}
	}
}
