package com.meui.prefs;
import android.app.*;
import android.content.*;
import android.preference.*;
import android.util.*;
import com.meui.RomCtrl.*;
import android.widget.*;
import android.view.*;

public class MultiColorPreference extends DialogPreference
{
    private final Context CONTEXT;
    public MultiColorPreference(Context con, AttributeSet attrs){
        super(con, attrs);
        CONTEXT=con;
    }

    public MultiColorPreference(Context con, AttributeSet attrs, int defStyle){
        super(con, attrs, defStyle);
        CONTEXT=con;
    }

    @Override
    protected void onPrepareDialogBuilder(AlertDialog.Builder builder) {
        MultiColorSettingsView mColorChooser=new MultiColorSettingsView(getContext());
        builder.setView(mColorChooser)
               .setTitle("设置多种颜色")
               .setPositiveButton("确认",null)
               .setNegativeButton("取消",null)
               .create();
    }
}
