package com.meui;
import android.content.*;
import android.database.sqlite.*;

public class MeDatabase extends SQLiteOpenHelper
{
	public MeDatabase(Context context, String name, SQLiteDatabase.CursorFactory factory, int version)
	{
		super(context,name,factory,version);
	}
	@Override
	public void onCreate(SQLiteDatabase p1)
	{
		// TODO: Implement this method
	}

	@Override
	public void onUpgrade(SQLiteDatabase p1, int p2, int p3)
	{
		// TODO: Implement this method
	}
	
}
