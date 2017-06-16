package com.meui;
import android.app.*;
import android.content.*;
import android.database.*;
import android.net.*;
import android.database.sqlite.*;

public class MeProvider extends ContentProvider
{
	// 胡乱写的，自知只能混过编译，还须仔细查阅相关资料再来重写。
	public static final Uri CONTENT_URI = Uri.parse("content://com.meui.RomCtrl");
	private final String STATUS_BAR_COLOR="";
	private ContentResolver contentResolver;
	private SharedPreferences preferences;
	private MeDatabase database;
	@Override
	public boolean onCreate()
	{
		contentResolver=getContext().getContentResolver();
		Activity context=(Activity)getContext();
		preferences=context.getPreferences(Context.CONTEXT_IGNORE_SECURITY);
		database=new MeDatabase(getContext(),"status_bar_colors",null,0);
		return false;
	}

	@Override
	public Cursor query(Uri p1, String[] p2, String p3, String[] p4, String p5)
	{
		SQLiteQueryBuilder queryBuilder= new SQLiteQueryBuilder();
		queryBuilder.setTables(STATUS_BAR_COLOR);
		//queryBuilder.setProjectionMap(
		return null;
	}

	@Override
	public String getType(Uri p1)
	{
		return null;
	}

	@Override
	public Uri insert(Uri uri, ContentValues values)
	{
		SQLiteDatabase sqL=database.getWritableDatabase();
		long rowId = sqL.insert(STATUS_BAR_COLOR, null, values);
		 if(rowId > 0){
			 //判断插入是否执行成功
			 final Uri insertedUserUri = ContentUris.withAppendedId(CONTENT_URI, rowId);
			 //通知监听器，数据已经改变
			 getContext().getContentResolver().notifyChange(insertedUserUri, null);
			 return insertedUserUri;
		 }
		 return uri;
	}

	@Override
	public int delete(Uri p1, String p2, String[] p3)
	{
		SQLiteDatabase sqL=database.getWritableDatabase();
		final int RESULT=sqL.delete(STATUS_BAR_COLOR,p2,p3);
		return RESULT;
	}

	@Override
	public int update(Uri p1, ContentValues p2, String p3, String[] p4)
	{
		// TODO: Implement this method
		
		return 0;
	}
	
}
