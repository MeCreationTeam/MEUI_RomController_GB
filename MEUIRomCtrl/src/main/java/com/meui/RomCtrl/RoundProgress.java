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
    protected void save() {
        final Map<String,?> mdhp=meuiPrefs.getAll();
        for (Map.Entry<String,?> entry:mdhp.entrySet()) {
            switch (entry.getKey().toString()) {
                case "mdhp_circleRadius":
                case "mdhp_barWidth":
                case "mdhp_rimWidth":
                case "mdhp_barColor":
                case "mdhp_rimColor":
                    Settings.System.putInt(mResolver, entry.getKey().toString(), Integer.valueOf(entry.getValue()));
                    break;
                case "mdhp_baseSpinSpeed":
                    Settings.System.putFloat(mResolver, entry.getKey().toString(), Float.parseFloat(entry.getValue().toString()) / 1000);
                    break;
                case "mdhp_cycleTime":
                    Settings.System.putString(mResolver, entry.getKey().toString(), entry.getValue().toString());
                    break;
                case "mdhp_linear":
                case "mdhp_quick":
                case "mdhp_cache":
                    final CheckBoxPreference cbp=(CheckBoxPreference)findPreference(entry.getKey().toString());
                    Settings.System.putInt(mResolver, entry.getKey().toString(), cbp.isChecked() ?1: 0);
                    break;
                default:
                    break;
            }
        }
    }
}
