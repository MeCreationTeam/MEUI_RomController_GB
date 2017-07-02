package com.meui;
import android.content.*;
import android.database.sqlite.*;

public final class MeDatabase extends SQLiteOpenHelper
{
    public MeDatabase(Context context, String name, SQLiteDatabase.CursorFactory factory, int version)
    {
        super(context,name,factory,version);
    }
    @Override
    public void onCreate(SQLiteDatabase p1)
    {
        p1.execSQL("create table if not exists BarColors ( id integer primary key autoincrement, packageName text, hasColor integer, color integer);");
    }

    @Override
    public void onUpgrade(SQLiteDatabase p1, int p2, int p3)
    {
    }
}
