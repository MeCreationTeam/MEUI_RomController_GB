package com.meui.RomCtrl;
import android.content.*;
import android.content.pm.*;
import android.os.*;
import android.preference.*;
import com.meui.prefs.*;
import java.util.*;
import net.margaritov.preference.colorpicker.*;

/**
 * The Preference Activity of Status Bar Color.
 * http://m.2cto.com/kf/201504/387107.html
 * @author zhaozihanzzh
 */
 
public class StatusBarColor extends BaseSettings
{

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		final PreferenceScreen appArea=(PreferenceScreen)findPreference("app_area");
		final PackageManager pm = getPackageManager();
		// Return a List of all packages that are installed on the device.
		List packages = pm.getInstalledPackages(0);
		for (PackageInfo packageInfo : packages) {
			
				//final AppColorPreference info = new AppColorPreference(StatusBarColor.this,null);
				final PreferenceScreen info = new PreferenceScreen(StatusBarColor.this,null);
				info.setTitle(packageInfo.applicationInfo.loadLabel(pm).toString());
				info.setSummary(packageInfo.packageName);
				appArea.addPreference(info);
				
				final CheckBoxPreference dependent=new CheckBoxPreference(StatusBarColor.this);
				dependent.setKey(packageInfo.packageName+"_dependent");
				dependent.setTitle("使用独立设置");
				info.addPreference(dependent);
			
				final ColorPickerPreference colorPicker=new ColorPickerPreference(StatusBarColor.this);
				colorPicker.setTitle("设置状态栏颜色");
				colorPicker.setKey(packageInfo.packageName+"_color");
				info.addPreference(colorPicker);
				colorPicker.setDependency(dependent.getKey());
			
		}
	}

	@Override
	protected int getXmlId()
	{
		return R.xml.status_bar_color;
	}

	@Override
	protected void save(ContentResolver CR, SharedPreferences meui)
	{
		// TODO: Implement this method
	}
}
