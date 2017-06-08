package com.meui.RomCtrl;
import android.app.*;
import android.os.*;
import android.view.*;
import android.widget.*;

public class Crash extends Activity
{
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		LinearLayout root=new LinearLayout(Crash.this);
		TextView error=new TextView(Crash.this);
		error.setText("TEST");
		root.addView(error);
		setContentView(root);
		Toast.makeText(Crash.this,"TEST",Toast.LENGTH_SHORT).show();
	}
	
}
