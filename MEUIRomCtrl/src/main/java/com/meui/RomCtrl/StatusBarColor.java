package com.meui.RomCtrl;
import android.content.*;
import android.content.pm.*;
import android.database.*;
import android.os.*;
import android.preference.*;
import android.provider.*;
import com.meui.*;
import java.util.*;
import net.margaritov.preference.colorpicker.*;
import android.widget.*;
import android.view.*;
import java.text.*;

/**
 * The Preference Activity of Status Bar Color.
 * http://m.2cto.com/kf/201504/387107.html
 * https://m.runoob.com/sqlite/sqlite-update.html
 * http://stackoverflow.com/questions/12710292/sorting-list-in-alphabetical-order
 * @author zhaozihanzzh
 */

public final class StatusBarColor extends PreferenceActivity {
    private ContentResolver resolver;
    private boolean isFirst=true;
    private Preference loadApp;
    private Handler mHandler=new Handler() {

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            addPerAppTint();
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        resolver = getContentResolver();

        addPreferencesFromResource(R.xml.status_bar_color);

        final Preference checkDelay=findPreference("sb_check_delay");
        final Preference defaultColor=findPreference("sb_default_color");
        loadApp = findPreference("click_to_load");
        checkDelay.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener(){
                @Override
                public boolean onPreferenceChange(Preference p1, Object p2) {
                    Settings.System.putInt(resolver, "sb_check_delay", (int)p2);
                    return true;
                }
            });
        defaultColor.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener(){
                @Override
                public boolean onPreferenceChange(Preference p1, Object p2) {
                    Settings.System.putInt(resolver, "sb_check_delay", (int)p2);
                    return true;
                }
            });
        loadApp.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener(){
                @Override
                public boolean onPreferenceClick(Preference p1) {
                    p1.setTitle("正在加载……");
                    mHandler.sendEmptyMessage(0);
                    return true;
                }
            });
    }

    private void addPerAppTint() {
        if (!isFirst)return;
        isFirst = false;
        final PreferenceScreen appArea=(PreferenceScreen)findPreference("app_area");
        final PackageManager pm = getPackageManager();
        // Return a List of all packages that are installed on the device.
        List<PackageInfo> packages =  pm.getInstalledPackages(0);
        Collections.sort(packages, new Comparator<PackageInfo>(){
                @Override
                public int compare(PackageInfo one, PackageInfo another) {
                    String firstName=one.applicationInfo.loadLabel(pm).toString();
                    String secondName=another.applicationInfo.loadLabel(pm).toString();
                    int result = Collator.getInstance().compare(firstName, secondName);
                    if(result == 0){
                        result = Collator.getInstance().compare(one.packageName, another.packageName);
                    }
                    return result;
                    // Avoid using HanziToPinyin, thank @Brevent.
                }
            });

        for (final PackageInfo packageInfo : packages) {
            // TODO: Only load Preferences when the user click the item, in order to speed it up.
            final PreferenceScreen info = getPreferenceManager().createPreferenceScreen(StatusBarColor.this);
            info.setTitle(packageInfo.applicationInfo.loadLabel(pm).toString());
            info.setSummary(packageInfo.packageName);
            appArea.addPreference(info);

            final CheckBoxPreference dependent=new CheckBoxPreference(StatusBarColor.this);
            dependent.setKey(packageInfo.packageName + "_dependent");
            dependent.setTitle("使用独立设置");
            info.addPreference(dependent);

            final ColorPickerPreference colorPicker=new ColorPickerPreference(StatusBarColor.this);
            colorPicker.setTitle("设置状态栏颜色");
            colorPicker.setKey(packageInfo.packageName + "_color");
            info.addPreference(colorPicker);
            colorPicker.setDependency(dependent.getKey());

            dependent.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener(){
                    @Override
                    public boolean onPreferenceChange(Preference preference, Object values) {
                        boolean exist=false;
                        final Cursor cursor;
                        final CheckBoxPreference checkBoxPreference = (CheckBoxPreference)preference;
                        final ContentValues contentValues=new ContentValues();
                        contentValues.put("hasColor", !checkBoxPreference.isChecked() ?1: 0);
                        contentValues.put("packageName", packageInfo.packageName);

                        cursor = resolver.query(MeProvider.CONTENT_URI, null, null, null, null);
                        if (!cursor.moveToFirst()) {
                            resolver.insert(MeProvider.CONTENT_URI, contentValues);
                        }
                        else {
                            do{
                                if (cursor.getCount() > 0 && packageInfo.packageName.equals(
                                        cursor.getString(cursor.getColumnIndex("packageName")))) {
                                    exist = true;
                                    final int id=cursor.getInt(cursor.getColumnIndex("id"));

                                    resolver.update(MeProvider.CONTENT_URI, contentValues, "id=" + id, null);
                                    break;
                                }
                            } while (cursor.moveToNext());
                            if (cursor != null)cursor.close();
                            if (!exist) resolver.insert(MeProvider.CONTENT_URI, contentValues);
                        }
                        return true;
                    }
                });

            colorPicker.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener(){
                    @Override
                    public boolean onPreferenceChange(Preference preference, Object values) {
                        final Cursor cursor=resolver.query(MeProvider.CONTENT_URI, null, null, null, null);

                        cursor.moveToFirst();
                        do {
                            if (packageInfo.packageName.equals(cursor.getString(cursor.getColumnIndex("packageName")))) {
                                final ContentValues contentValues=new ContentValues();
                                contentValues.put("packageName", packageInfo.packageName);
                                contentValues.put("color", (int)values);
                                final int id=cursor.getInt(cursor.getColumnIndex("id"));

                                resolver.update(MeProvider.CONTENT_URI, contentValues, "id=" + id, null);
                                break;}
                        } while (cursor.moveToNext());
                        if (cursor != null)cursor.close();
                        return true;
                    }
                });

            appArea.removePreference(loadApp);
        }
    }}
