package com.meui.prefs;
import android.content.*;
import android.preference.*;
import android.util.*;
import android.view.*;
import android.widget.*;
import android.os.*;
import android.text.*;

/**
 * The following code was written by Matthew Wiggins 
 * and is released under the APACHE 2.0 license 
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 */

public class SeekBarPreference extends DialogPreference implements SeekBar.OnSeekBarChangeListener, TextView.OnEditorActionListener
{
    private static final String androidns="http://schemas.android.com/apk/res/android";

    private SeekBar mSeekBar;
    private TextView mSplashText;
    private EditText mValueText;
    private Context mContext;

    private String mDialogMessage, mSuffix;
    private int mDefault, mMax, mValue, newValue = 0;
    private boolean hasChanged=false;

    public SeekBarPreference(Context context, AttributeSet attrs) { 
        super(context,attrs); 
        mContext = context;

        mDialogMessage = attrs.getAttributeValue(androidns,"dialogMessage");
        mSuffix = attrs.getAttributeValue(androidns,"text");
        mDefault = attrs.getAttributeIntValue(androidns,"defaultValue", 0);
        mMax = attrs.getAttributeIntValue(androidns,"max", 100);

    }
    @Override 
    protected View onCreateDialogView() {
        final int paddingSide=Build.VERSION.SDK_INT>20 ? 48 : 6;
        final LinearLayout.LayoutParams params;
        final LinearLayout layout = new LinearLayout(mContext);
        layout.setOrientation(LinearLayout.VERTICAL);
        layout.setPadding(paddingSide,8,paddingSide,8);

        mSplashText = new TextView(mContext);
        if (mDialogMessage != null)
            mSplashText.setText(mDialogMessage);
        layout.addView(mSplashText);

        mValueText = new EditText(mContext);
        mValueText.setGravity(Gravity.CENTER_HORIZONTAL);
        mValueText.setTextSize(32);
        mValueText.setFilters(new InputFilter[]{new InputFilter.LengthFilter(Integer.toString(mMax).length())});
        mValueText.setSingleLine();
        mValueText.setInputType(InputType.TYPE_CLASS_NUMBER);
        mValueText.setOnEditorActionListener(this);
        params = new LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.MATCH_PARENT, 
            LinearLayout.LayoutParams.WRAP_CONTENT);
        layout.addView(mValueText, params);

        mSeekBar = new SeekBar(mContext);
        mSeekBar.setOnSeekBarChangeListener(this);
        layout.addView(mSeekBar, new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));

        if (shouldPersist())
            mValue = getPersistedInt(mDefault);

        mSeekBar.setMax(mMax);
        mSeekBar.setProgress(mValue);
        return layout;
    }
    @Override 
    protected void onBindDialogView(View v) {
        super.onBindDialogView(v);
        mSeekBar.setMax(mMax);
        mSeekBar.setProgress(mValue);
    }
    @Override
    protected void onSetInitialValue(boolean restore, Object defaultValue)  
    {
        super.onSetInitialValue(restore, defaultValue);
        if (restore) 
            mValue = shouldPersist() ? getPersistedInt(mDefault) : 0;
        else 
            mValue = (Integer)defaultValue;
    }
    @Override
    public void onProgressChanged(SeekBar seek, int value, boolean fromTouch)
    {
        final String t = String.valueOf(value);
        mValueText.setText(mSuffix == null ? t : t.concat(mSuffix));
        //if (shouldPersist())
        //    persistInt(value);
        newValue=value;
        hasChanged=true;
        callChangeListener(new Integer(value));
    }

    @Override
    public void onClick(DialogInterface dialog, int which)
    {
        super.onClick(dialog, which);
        if(which==dialog.BUTTON_POSITIVE&&shouldPersist()&&hasChanged)
                persistInt(newValue);
    }
    @Override
    public boolean onEditorAction(TextView p1, int p2, KeyEvent p3)
    {
        final int tempValue=Integer.parseInt(p1.getText().toString());
        if(tempValue<0||tempValue>mMax){
            mValueText.setText(String.valueOf(mSeekBar.getProgress()));
            return true;
        }
        onProgressChanged(mSeekBar,tempValue,false);
        mSeekBar.setProgress(newValue);
        return false;
    }
    
    @Override
    public void onStartTrackingTouch(SeekBar seek) {}
    @Override
    public void onStopTrackingTouch(SeekBar seek) {}
/*
    public void setMax(int max) { mMax = max; }
    public int getMax() { return mMax; }
    
    public void setProgress(int progress) { 
        mValue = progress;
        if (mSeekBar != null)
            mSeekBar.setProgress(progress); 
    }
    public int getProgress() { return mValue; }*/
}
