package com.meui.RomCtrl;
import android.app.*;
import android.os.*;
import android.widget.*;

/**
 * When app crashes, start this activity.
 * @author zhaozihanzzh
 */

public class Crash extends Activity
{
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		final String log=getIntent().getStringExtra("log");
		LinearLayout root=new LinearLayout(Crash.this);
		TextView error=new TextView(Crash.this);
		error.setText(log);
		root.addView(error);
		setContentView(root);
	}
	
	@Override
	protected void onDestroy()
	{
		super.onDestroy();
		finish();
	}
}
