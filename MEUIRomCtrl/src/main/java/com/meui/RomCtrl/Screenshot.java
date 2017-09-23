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

public final class Screenshot extends BaseSettings {
    private final String PATH_ERROR="截图路径修改失败。";
    @Override
    protected int getXmlId() {
        return R.xml.screenshot;
    }

    @Override
    protected void save() {
        final EditTextPreference pathEditor=(EditTextPreference)findPreference("ss_path");
        final String lastValue=Settings.System.getString(mResolver, "ss_path");

        final Map<String,?> ss=meuiPrefs.getAll();
        for (Map.Entry<String,?> entry:ss.entrySet()) {
            final String KEY=entry.getKey().toString();
            switch (KEY) {
                case "ss_path":
                    final String VALUE=entry.getValue().toString();
                    final String newPath=Environment.getExternalStorageDirectory().toString() + "/" + VALUE;
                    try {
                        File newPathFile=new File(newPath);
                        boolean canBeCreated=false;
                        final boolean exist=newPathFile.exists();
                        if (!exist) canBeCreated = newPathFile.mkdirs();
                        newPathFile = null;
                        if (exist || canBeCreated) {
                            Settings.System.putString(mResolver, KEY, VALUE);
                        } else {
                            pathEditor.setText(lastValue);
                            Toast.makeText(this, PATH_ERROR, Toast.LENGTH_LONG).show();
                        }
                    } catch (Exception e) {
                        Log.e("MEUI", "Unable to create screenshot path: " + e);
                        Toast.makeText(this, PATH_ERROR, Toast.LENGTH_LONG).show();
                        pathEditor.setText(lastValue);
                        Settings.System.putString(mResolver, KEY, lastValue);
                    }
                    break;
                case "screenshot_format":
                    Settings.System.putString(mResolver, KEY, entry.getValue().toString());
                    break;
                case "screenshot_quality":
                case "screenshot_delay":
                    Settings.System.putInt(mResolver, KEY, Integer.valueOf(entry.getValue()));
                    break;
                case "share_screenshot":
                    final CheckBoxPreference cbp=(CheckBoxPreference)findPreference(KEY);
                    Settings.System.putInt(mResolver, KEY, cbp.isChecked() ?1: 0);
                    break;
                default:
                    break;
            }
        }
    }
}
