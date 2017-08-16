package com.meui.prefs.MultiColorPanel;

/**
 * The class which is used to store name of color and value of color.
 * @author zhaozihanzzh
 */

public final class ColorSetting {
    private String mItemName;
    private int mColorValue;
    public ColorSetting(String itemName,int colorValue){
        mItemName=itemName;
        mColorValue=colorValue;
    }

    public void setItemName(String newName){
        mItemName = newName;
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
