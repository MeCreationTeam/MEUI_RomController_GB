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

public final class Lockscreen extends BaseSettings {

    @Override
    protected int getXmlId() {
        return R.xml.lockscreen;
    }

    @Override
    protected void save(String changedKey) {
		switch (changedKey) {
			case "ls_lunar_color":
			case "ls_st24_textcolor":
				Settings.System.putInt(mResolver, changedKey, getPreferenceManager().getSharedPreferences().getInt(changedKey, 0));
				break;
			case "ls_lunar_size":
			case "ls_st24_textsize":
				Settings.System.putFloat(mResolver, changedKey, getPreferenceManager().getSharedPreferences().getFloat(changedKey, 0));
				break;
			case "ls_lunar":
			case "ls_st24_show":
				Settings.System.putInt(mResolver, changedKey, getPreferenceManager().getSharedPreferences().getBoolean(changedKey, false) ?1: 0);
				break;
			default:
				break;
		}
	}

}
