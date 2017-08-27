package com.meui.prefs.MultiColorPanel;
import android.content.*;
import android.preference.*;
import android.util.*;
import android.view.*;
import android.widget.*;
import com.meui.RomCtrl.*;
import java.util.*;
import net.margaritov.preference.colorpicker.*;

/**
 * The LinearLayout in dialog, controls items directly.
 * @author zhaozihanzzh
 */

public class MultiColorSettingsView extends LinearLayout {
    private List<ColorSetting> mColors = new ArrayList<>();
    private PreferenceActivity mActivity;
    public MultiColorSettingsView(Context con) {
        super(con);
        mActivity=(PreferenceActivity) con;
        initColors();
        
        setOrientation(LinearLayout.VERTICAL);
        final LayoutParams params= new LayoutParams(LayoutParams.MATCH_PARENT,LayoutParams.WRAP_CONTENT);
        final ListView listView = new ListView(con);
        final TextView addText= new TextView(con);
        addText.setText("添加颜色");
        addText.setTextAppearance(con, android.R.style.TextAppearance_Medium);
        
        TypedValue value =  new TypedValue();
        con.getTheme().resolveAttribute(android.R.attr.listPreferredItemPaddingLeft, value, true);
        int paddingLeft = value.complexToDimensionPixelOffset(value.data, getResources().getDisplayMetrics());
        addText.setPadding(paddingLeft, addText.getPaddingTop(), addText.getPaddingRight(), addText.getPaddingBottom()); 
        listView.addHeaderView(addText);

        final MultiColorAdapter adapter = new MultiColorAdapter(con, R.layout.list_color, mColors);
        listView.setAdapter(adapter);
        addView(listView, params);
        
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener(){
                @Override
                public void onItemClick(AdapterView<?> p1, View p2, final int p3, long p4) {
                    final ColorSetting colorSetting=(ColorSetting)p1.getAdapter().getItem(p3);
                    if(colorSetting == null){
                        // This is Add Item.
                        if(mColors.size() <= 15) {
                            ColorSetting newSetting=new ColorSetting("颜色"+p1.getChildCount(), 0xff009688);
                            mColors.add(newSetting);
                            adapter.notifyDataSetChanged();
                            return;
                        }
                    }
                    ColorPickerDialog dialog=new ColorPickerDialog(getContext(), colorSetting.getColorValue());
                    dialog.setOnColorChangedListener(new ColorPickerDialog.OnColorChangedListener(){
                        @Override
                        public void onColorChanged(int color){
                            colorSetting.setColorValue(color);
                            mColors.set(p3-1, colorSetting);
                        }
                    });
                    dialog.show();
                }
        });
        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener(){
            // Delete action will take place when color count is more than one.
            @Override
            public boolean onItemLongClick(AdapterView<?> p1, View p2, final int p3, long p4) {
                if(p3 != 0 && mColors.size() > 1) {
                    mColors.remove(p3-1);
                    refreshItemName();
                    adapter.notifyDataSetChanged();
                }
                return true;
            }
        });
    }
    /**
     * Initialize colors from data in SharedPreferences.
     */
    private void initColors(){
        SharedPreferences sp = mActivity.getPreferenceManager().getSharedPreferences();
        int count=sp.getInt("spb_color_multi_count",2);
        for(int i=1; i<=count; i++){
            int currentColor=sp.getInt("spb_color_multi_value_"+i,0xff009688);
            mColors.add(i-1, new ColorSetting("颜色"+i,currentColor));
        }
    }
    /**
     * Refresh item names according to their number.
     */
    private void refreshItemName(){
        for(int i=0; i < mColors.size(); i++){
            mColors.get(i).setItemName("颜色" +( i + 1));
        }
    }
    /**
     * The getter for the List which stores color names and values.
     * @return List which stores color settings.
     */
    public List<ColorSetting> getColors(){
        return mColors;
    }
}
