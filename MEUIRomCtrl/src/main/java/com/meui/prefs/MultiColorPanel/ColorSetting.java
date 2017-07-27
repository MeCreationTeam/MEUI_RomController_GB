package com.meui.prefs.MultiColorPanel;

public class ColorSetting
{
    private String mItemName;
    private int mColorValue;
    public ColorSetting(String itemName,int colorValue){
        mItemName=itemName;
        mColorValue=colorValue;
    }

    public void setColorValue(int colorValue) {
        mColorValue = colorValue;
    }
    public String getItemName() {
        return mItemName;
    }
    public int getColorValue(){
        return mColorValue;
    }
}
