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
import android.widget.*;

/**
 * The Preference Activity of Status Bar Color.
 * http://m.2cto.com/kf/201504/387107.html
 * @author zhaozihanzzh
 */
 
public class StatusBarColor extends PreferenceActivity
{
	//private MeProvider provider=new MeProvider();
	private ContentResolver resolver;
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		resolver=getContentResolver();
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
						boolean exist=false;
						Cursor cursor=null;
						final ContentValues contentValues=new ContentValues();
						contentValues.put("hasColor",!dependent.isChecked()?1:0);
						contentValues.put("packageName",packageInfo.packageName);
						
						
						cursor=resolver.query(MeProvider.CONTENT_URI,null,null,null,null);
						
						if(!cursor.moveToFirst())resolver.insert(MeProvider.CONTENT_URI,contentValues);
						else{
						do{
							//int c=cursor.getCount();
							if(cursor.getCount()>0 && packageInfo.packageName.equals(
													  cursor.getString(cursor.getColumnIndex("packageName"))))
							{
								exist=true;
								int id=cursor.getInt(cursor.getColumnIndex("id"));
								Toast.makeText(StatusBarColor.this,"id="+id,Toast.LENGTH_SHORT).show();
								
								resolver.update(MeProvider.CONTENT_URI,contentValues,"packageName=? AND hasColor=?"/*String.valueOf(id)*/,null);
								break;
							}
						} while (cursor.moveToNext());
						if(cursor!=null)cursor.close();
					    if(!exist) {
							resolver.insert(MeProvider.CONTENT_URI,contentValues);
						}
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
						//final Cursor cursor=resolver.query(MeProvider.CONTENT_URI,null,null,null,null);
						
						//cursor.moveToFirst();
						//do {
						//	if(packageInfo.packageName.equals(cursor.getString(cursor.getColumnIndex("packageName")))){
								//cursor.updateInt(cursor.getColumnIndex("color"),Integer.parseInt(values.toString()));
								//cursor.commitUpdates();
								//exist=true;
								final ContentValues contentValues=new ContentValues();
								contentValues.put("packageName",packageInfo.packageName);
								contentValues.put("color",(int)values);
								resolver.update(MeProvider.CONTENT_URI,contentValues,"color=?",null);
								
							//	break;}
						//} while (cursor.moveToNext());
						//if(cursor!=null)cursor.close();
						return true;
					}
				});
		}
	}
}
