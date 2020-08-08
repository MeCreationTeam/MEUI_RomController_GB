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

public final class Screenshot extends PreferenceActivity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		addPreferencesFromResource(R.xml.screenshot);
        final EditTextPreference pathEditor=(EditTextPreference)findPreference("screenshot_path");
		pathEditor.setOnPreferenceChangeListener(new EditTextPreference.OnPreferenceChangeListener(){
			@Override
			public boolean onPreferenceChange(Preference p1, Object p2){
				String newPath = p2.toString();
				File newPathFile=new File(newPath);
				boolean canBeCreated = false;
				if (newPathFile.exists()) {
					canBeCreated = newPathFile.canWrite() && newPathFile.isDirectory();
				} else {
					canBeCreated = newPathFile.mkdirs();
				}
				newPathFile = null;
				if (!canBeCreated) Toast.makeText(Screenshot.this, "无效路径："+p2, Toast.LENGTH_LONG).show();
				return canBeCreated;
			}
		});
		
	}

ContentResolver mResolver;String PATH_ERROR;
    //@Override
    protected void save(String changedKey) {
		SharedPreferences sp = getPreferenceManager().getSharedPreferences();
        final EditTextPreference pathEditor=(EditTextPreference)findPreference("ss_path");
        final String lastValue=Settings.System.getString(mResolver, "ss_path");
		switch (changedKey) {
			case "ss_path":

				final String VALUE= sp.getString(changedKey, "DCIM/Screenshots");
				final String newPath=Environment.getExternalStorageDirectory().toString() + "/" + VALUE;
				try {
					File newPathFile=new File(newPath);
					boolean canBeCreated=false;
					final boolean exist=newPathFile.exists();
					if (!exist) canBeCreated = newPathFile.mkdirs();
					newPathFile = null;
					if (exist || canBeCreated) {
						Settings.System.putString(mResolver, changedKey, VALUE);
					} else {
						pathEditor.setText(lastValue);
						Toast.makeText(this, PATH_ERROR, Toast.LENGTH_LONG).show();
					}
				} catch (Exception e) {
					Log.e("MEUI", "Unable to create screenshot path: " + e);
					Toast.makeText(this, PATH_ERROR, Toast.LENGTH_LONG).show();
					pathEditor.setText(lastValue);
					Settings.System.putString(mResolver, changedKey, lastValue);
				}
				break;
			case "share_screenshot":
				Settings.System.putInt(mResolver, changedKey, sp.getBoolean(changedKey, false) ?1: 0);
				break;
			default:
				break;
		}
	}

}
