package com.meui.prefs;
import android.content.*;
import android.preference.*;
import android.view.*;
import android.widget.*;
import com.meui.RomCtrl.*;
import com.meui.prefs.MultiColorPanel.*;
import java.util.*;
import net.margaritov.preference.colorpicker.*;

public class MultiColorSettingsView extends LinearLayout
{
    private List<ColorSetting> mColors = new ArrayList<>();
    private PreferenceActivity mActivity;
    private ColorSetting[] colorSettings;
    public MultiColorSettingsView(Context con){
        super(con);
        mActivity=(PreferenceActivity)con;
        initColors();
        
        setOrientation(LinearLayout.VERTICAL);
        LayoutParams params= new LayoutParams(LayoutParams.MATCH_PARENT,LayoutParams.WRAP_CONTENT);
        ListView listView = new ListView(con);
        FrameLayout addItem = new FrameLayout(con);
        TextView addText= new TextView(con);
        addText.setText("添加颜色");
        addItem.addView(addText, params);
        listView.addHeaderView(addItem);

        MultiColorAdapter adapter = new MultiColorAdapter(con, R.layout.list_color, mColors);

        listView.setAdapter(adapter);
        addView(listView, params);
        
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener(){
                @Override
                public void onItemClick(AdapterView<?> p1, View p2, final int p3, long p4) {
                    final ColorSetting colorSetting=(ColorSetting)p1.getAdapter().getItem(p3);
                    if(colorSetting == null){
                        // This is Add Item.
                        return;
                    }
                    // Toast.makeText(getContext(),colorSetting.getItemName()+",p3="+p3,Toast.LENGTH_SHORT).show();
                    ColorPickerDialog dialog=new ColorPickerDialog(getContext(), colorSetting.getColorValue());
                    dialog.setOnColorChangedListener(new ColorPickerDialog.OnColorChangedListener(){
                        @Override
                        public void onColorChanged(int color){
                            colorSetting.setColorValue(color);
                            colorSettings[p3-1]=colorSetting;
                        }
                    });
                    dialog.show();
                }
        });
        
    }
    private void initColors(){
        SharedPreferences sp = mActivity.getPreferenceManager().getSharedPreferences();
        int count=sp.getInt("spb_color_multi_count",2);
        colorSettings = new ColorSetting[count];
        for(int i=1;i<=count;i++){

            int currentColor=sp.getInt("spb_color_multi_value_"+i,0xff009688);
            colorSettings[i-1]=new ColorSetting("颜色"+i,currentColor);
            mColors.add(i-1,colorSettings[i-1]);
        }
    }
    public ColorSetting[] getColors(){
        return colorSettings;
    }
}
