package com.meui.RomCtrl;
import android.preference.*;
import android.os.*;
import android.widget.*;
import android.provider.*;
import android.content.*;

/**
 * Custom horizontal progress bar controller activity.
 * @author zhaozihanzzh
 */

final public class SmoothProgress extends PreferenceActivity implements Preference.OnPreferenceChangeListener
{
    private ContentResolver resolver;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.smooth_progress);
        resolver=getContentResolver();
        
        final PreferenceScreen baseScreen=getPreferenceScreen();
        for(int now=0;now<baseScreen.getPreferenceCount();now++){
            baseScreen.getPreference(now).setOnPreferenceChangeListener(this);
        }
        
    }
    @Override
    public boolean onPreferenceChange(Preference p1, Object p2) {
        if(p1 instanceof CheckBoxPreference){
            CheckBoxPreference newP1=(CheckBoxPreference)p1;
            Settings.System.putInt(resolver, p1.getKey(), newP1.isChecked() ? 0 : 1);
            return true;
        }
        
        switch(p1.getKey()){
            case "spb_color":
            case "spb_sections_count":
            case "spb_stroke_separator_length":
                Settings.System.putInt(resolver,p1.getKey(),p2);
                break;
            case "spb_stroke_width":
            case "spb_speed":
            case "spb_start_speed":
            case "spb_stop_speed":
                Settings.System.putFloat(resolver, p1.getKey(), Float.parseFloat( String.valueOf( p2)));
                break;
            case "spb_interpolator":
                Settings.System.putInt(resolver, p1.getKey(), Integer.parseInt((String)p2));
        }
        return true;
    }
    
}
