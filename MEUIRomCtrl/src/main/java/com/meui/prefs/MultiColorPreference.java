package com.meui.prefs;
import android.app.*;
import android.content.*;
import android.preference.*;
import android.util.*;
import com.meui.prefs.MultiColorPanel.*;
import android.widget.*;
import android.provider.*;

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
        final MultiColorSettingsView mColorChooser=new MultiColorSettingsView(getContext());
        builder.setView(mColorChooser)
               .setTitle("设置多种颜色")
               .setPositiveButton("确认",new DialogInterface.OnClickListener(){
                   @Override
                   public void onClick(DialogInterface p1, int p2){
                       ColorSetting[] colorSettings=mColorChooser.getColors();
                       SharedPreferences.Editor sp=getPreferenceManager().getSharedPreferences().edit();
                       sp.putInt("spb_color_multi_count",colorSettings.length);
                       String result = "";
                       for(int i=0;i<colorSettings.length;i++){
                           result+="("+colorSettings[i].getColorValue()+")";
                           sp.putInt("spb_color_multi_value_"+(i+1),colorSettings[i].getColorValue());
                       }
                       sp.commit();
                       Settings.System.putString(getContext().getContentResolver(),"spb_color_string",result);
                   }
               })
               .setNegativeButton("取消",null)
               .create();
    }
}
