package com.meui.prefs.MultiColorPanel;
import android.app.*;
import android.content.*;
import android.preference.*;
import android.util.*;
import com.meui.prefs.MultiColorPanel.*;
import android.widget.*;
import android.provider.*;
import java.util.*;

/**
 * Custom DialogPreference, used to show the color settings list and commit changes.
 * @author zhaozihanzzh
 */

public class MultiColorPreference extends DialogPreference {
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
        final SharedPreferences sharedPreferences = getPreferenceManager().getSharedPreferences();
        final int lastColorCount = sharedPreferences.getInt("spb_color_multi_count", 2);
        final MultiColorSettingsView mColorChooser=new MultiColorSettingsView(getContext());
        builder.setView(mColorChooser)
               .setTitle("设置多种颜色，最大色数：15")
               .setPositiveButton("确认",new DialogInterface.OnClickListener(){
                   @Override
                   public void onClick(DialogInterface p1, int p2){
                       // Ready to put data, initialize count first.
                       List<ColorSetting> mColors=mColorChooser.getColors();
                       SharedPreferences.Editor sp=sharedPreferences.edit();
                       sp.putInt("spb_color_multi_count",mColors.size());
                       // If deletions are more than additions, we need to delete remaining color data.
                       if(mColors.size() < lastColorCount){
                           for(int i2 = mColors.size() + 1; i2 <= lastColorCount; i2++){
                               sp.remove("spb_color_multi_value_"+ i2);
                               Log.d("MEUI","Remove spb_color_multi_value_"+ i2+1);
                           }
                       }
                       // Put color values and build result string.
                       String result = "";
                       for(int i=0; i<mColors.size(); i++){
                           result+="("+mColors.get(i).getColorValue()+")";
                           sp.putInt("spb_color_multi_value_"+(i+1),mColors.get(i).getColorValue());
                       }
                       // Commit.
                       sp.commit();
                       Settings.System.putString(getContext().getContentResolver(),"spb_color_string",result);
                   }
               })
               .setNegativeButton("取消",null)
               .create();
    }
}
