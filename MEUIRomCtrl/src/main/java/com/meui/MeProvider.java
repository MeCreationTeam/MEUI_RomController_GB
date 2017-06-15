package com.meui;
import android.app.*;
import android.content.*;
import android.database.*;
import android.net.*;

public class MeProvider extends ContentProvider
{
	public static final Uri CONTENT_URI = Uri.parse("content://com.meui.RomCtrl");
	private ContentResolver contentResolver;
	private SharedPreferences preferences;
	@Override
	public boolean onCreate()
	{
		contentResolver=getContext().getContentResolver();
		Activity context=(Activity)getContext();
		preferences=context.getPreferences(Context.CONTEXT_IGNORE_SECURITY);		
		return true;
	}

	@Override
	public Cursor query(Uri p1, String[] p2, String p3, String[] p4, String p5)
	{
		// TODO: Implement this method
		return null;
	}

	@Override
	public String getType(Uri p1)
	{
		return null;
	}

	@Override
	public Uri insert(Uri p1, ContentValues p2)
	{
		// TODO: Implement this method
		//preferences.edit().putString( p2.getAsString("MEUISB");
		return p1;
	}

	@Override
	public int delete(Uri p1, String p2, String[] p3)
	{
		final int RESULT=preferences.edit().remove(p2).commit()?1:0;
		contentResolver.notifyChange(p1,null);
		return RESULT;
	}

	@Override
	public int update(Uri p1, ContentValues p2, String p3, String[] p4)
	{
		// TODO: Implement this method
		
		return 0;
	}
	
}
