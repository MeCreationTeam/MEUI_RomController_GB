package com.meui;
import android.app.*;
import android.content.*;
import android.database.*;
import android.net.*;
import android.database.sqlite.*;
import android.util.*;

/**
 * Content Provider for MEUI Status Bar Color settings.
 * @author zhaozihanzzh
 */

public final class MeProvider extends ContentProvider
{
    public static final Uri CONTENT_URI = Uri.parse("content://com.meui.RomCtrl/BarColors");
    private final String STATUS_BAR_COLOR="BarColors";
    private MeDatabase database;
    private Context mContext;
    
    @Override
    public boolean onCreate()
    {
        mContext=getContext();
        database=new MeDatabase(mContext,STATUS_BAR_COLOR,null,1);
        return true;
    }

    @Override
    public Cursor query(Uri p1, String[] projection, String selection, String[] selectionArgs, String sortOrder)
    {
        if(database==null)database=new MeDatabase(mContext,STATUS_BAR_COLOR,null,1);
        
        final SQLiteDatabase db=database.getWritableDatabase();
        
        return db.query(STATUS_BAR_COLOR,projection,selection,selectionArgs,null,null,sortOrder);
    }

    @Override
    public String getType(Uri p1)
    {
        return null;
    }

    @Override
    public Uri insert(Uri uri, ContentValues values)
    {
        if(database==null)database=new MeDatabase(mContext,STATUS_BAR_COLOR,null,1);
        
        SQLiteDatabase sqL=database.getWritableDatabase();
        long rowId = sqL.insert(STATUS_BAR_COLOR, null, values);
         if(rowId > 0){
             //判断插入是否执行成功
             final Uri insertedUserUri = ContentUris.withAppendedId(CONTENT_URI, rowId);
             //通知监听器，数据已经改变
             mContext.getContentResolver().notifyChange(insertedUserUri, null);
             return insertedUserUri;
         }
         return uri;
    }

    @Override
    public int delete(Uri p1, String p2, String[] p3)
    {
        SQLiteDatabase sqL=database.getWritableDatabase();
        final int RESULT=sqL.delete(STATUS_BAR_COLOR,p2,p3);
        final Uri insertedUserUri = ContentUris.withAppendedId(CONTENT_URI, RESULT);
        //通知监听器，数据已经改变
        mContext.getContentResolver().notifyChange(insertedUserUri, null);
        return RESULT;
    }

    @Override
    public int update(Uri uri, ContentValues values, String p3, String[] p4)
    {
        final SQLiteDatabase sqL=database.getWritableDatabase();
        
        final int RESULT = sqL.update(STATUS_BAR_COLOR,values,p3,p4);
        // if(result>0){
            final Uri insertedUserUri = ContentUris.withAppendedId(CONTENT_URI, RESULT);
            //通知监听器，数据已经改变
            mContext.getContentResolver().notifyChange(insertedUserUri, null);
        // }
        return RESULT;
    }
    
}
