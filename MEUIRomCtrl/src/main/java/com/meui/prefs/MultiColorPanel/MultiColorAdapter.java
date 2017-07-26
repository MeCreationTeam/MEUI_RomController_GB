package com.meui.prefs.MultiColorPanel;
import android.content.*;
import android.widget.*;
import java.util.*;
import android.view.*;
import com.meui.RomCtrl.*;

public class MultiColorAdapter extends ArrayAdapter<ColorSetting>
{
    private int mResId;
    public MultiColorAdapter(Context context, int resId, List<ColorSetting> objects){
        super(context, resId, objects);
        mResId=resId;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // TODO: Implement this method
        ColorSetting colorSetting=getItem(position);
        View view=LayoutInflater.from(getContext()).inflate(mResId, parent, false);
        TextView textView=(TextView)view.findViewById(R.id.list_color_name);
        textView.setText(colorSetting.getItemName());
        return view;
    }
    
}
