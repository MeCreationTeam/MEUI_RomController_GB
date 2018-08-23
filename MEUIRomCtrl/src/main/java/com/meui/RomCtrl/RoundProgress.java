package com.meui.RomCtrl;
import android.content.*;
import android.preference.*;
import android.provider.*;
import android.util.*;
import android.widget.*;
import java.util.*;

/**
 * This Preference activity is used to control the Materialish ProgressBar in MEUI.
 * When the preferences change, it will save settings to system.
 * @author zhaozihanzzh
 */

public final class RoundProgress extends BaseSettings {
    @Override
    protected int getXmlId() {
        return R.xml.wheel_progress;
    }

    @Override
    protected void save(String changedKey) {
            switch (changedKey) {
                case "mdhp_circleRadius":
                case "mdhp_barWidth":
                case "mdhp_rimWidth":
                case "mdhp_barColor":
                case "mdhp_rimColor":
                    Settings.System.putInt(mResolver, changedKey, getPreferenceManager().getSharedPreferences().getInt(changedKey, 0));
                    break;
                case "mdhp_baseSpinSpeed":
                    Settings.System.putFloat(mResolver, changedKey,getPreferenceManager().getSharedPreferences().getFloat(changedKey, 0) / 1000);
                    break;
                case "mdhp_cycleTime":
                    Settings.System.putString(mResolver, changedKey, getPreferenceManager().getSharedPreferences().getString(changedKey, "460"));
                    break;
                case "mdhp_linear":
                case "mdhp_quick":
                case "mdhp_cache":
                    Settings.System.putInt(mResolver, changedKey, getPreferenceManager().getSharedPreferences().getBoolean(changedKey, false) ?1: 0);
                    break;
                default:
                    break;
            }
        
    }
}
