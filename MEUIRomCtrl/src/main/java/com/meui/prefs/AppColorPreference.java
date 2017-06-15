package com.meui.prefs;
import android.content.*;
import android.preference.*;
import android.util.*;
import android.view.*;
import android.content.pm.*;

public class AppColorPreference extends DialogPreference
{
	private Context context;
	private PackageInfo mInfo;
	public AppColorPreference(Context c,AttributeSet attrs){
		super(c,attrs);
		context=c;
	}
	public AppColorPreference(Context c,AttributeSet attrs,int defStyle){
		super(c,attrs,defStyle);
		context=c;
	}
	@Override
	protected View onCreateView(ViewGroup parent)
	{
		return super.onCreateView(parent);
	}
	
}
