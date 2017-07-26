package com.meui.prefs;
import android.content.*;
import android.preference.*;
import android.widget.*;
import com.meui.prefs.MultiColorPanel.*;
import java.util.*;
import com.meui.RomCtrl.*;

public class MultiColorSettingsView extends LinearLayout
{
    private List<ColorSetting> mColors = new ArrayList<>();
    private PreferenceActivity mActivity;
    public MultiColorSettingsView(Context con){
        super(con);
        mActivity=(PreferenceActivity)con;
        setOrientation(LinearLayout.VERTICAL);
        LayoutParams params= new LayoutParams(LayoutParams.MATCH_PARENT,LayoutParams.WRAP_CONTENT);
        ListView listView = new ListView(con);
        FrameLayout addItem = new FrameLayout(con);
        TextView addText= new TextView(con);
        addText.setText("添加颜色");
        addItem.addView(addText, params);
        listView.addHeaderView(addItem);
        
        initColors();
        MultiColorAdapter adapter = new MultiColorAdapter(con, R.layout.list_color, mColors);
        
        listView.setAdapter(adapter);
        addView(listView, params);
        
    }
    private void initColors(){
        SharedPreferences sp= mActivity.getPreferenceManager().getSharedPreferences();
        int count=sp.getInt("spb_color_multi_count",2);
        ColorSetting[] colorSettings = new ColorSetting[count];
        for(int i=1;i<=count;i++){

            int currentColor=sp.getInt("spb_color_multi_value_"+i,0xff009688);
            colorSettings[i-1]=new ColorSetting("颜色"+i,currentColor);
            mColors.add(i-1,colorSettings[i-1]);
        }
    }
}
