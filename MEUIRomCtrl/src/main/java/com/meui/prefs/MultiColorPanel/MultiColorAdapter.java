package com.meui.prefs.MultiColorPanel;
import android.content.*;
import android.widget.*;
import java.util.*;
import android.view.*;
import com.meui.RomCtrl.*;

/**
 * The adapter for the ListView in dialog. Thanks to the book Android First Line Code.
 * @author zhaozihanzzh
 */

public class MultiColorAdapter extends ArrayAdapter<ColorSetting> {
    private int mResId;
    public MultiColorAdapter(Context context, int resId, List<ColorSetting> objects){
        super(context, resId, objects);
        mResId=resId;
    }
    
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ColorSetting colorSetting=getItem(position);
        View row;
        ViewHolder viewHolder;
        if(convertView == null){
            row=LayoutInflater.from(getContext()).inflate(mResId, parent, false);
            viewHolder=new ViewHolder();
            viewHolder.textView=(TextView)row.findViewById(R.id.list_color_name);
            row.setTag(R.id.list_color_name, viewHolder);
            }else{
                row = convertView;
                viewHolder=(ViewHolder)row.getTag(R.id.list_color_name);
            }
        viewHolder.textView.setText(colorSetting.getItemName());
        
        return row;
    }
    
    /**
     * View Holder, used to speed up the ListView. Thanks to the book Android First Line Code.
     * @author zhaozihanzzh
     */
    class ViewHolder{
        TextView textView;
    }
}
