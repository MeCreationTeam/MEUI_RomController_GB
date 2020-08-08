package com.meui;
import android.content.*;
import android.database.*;
import android.net.*;

/**
 * Provide our settings to others.
 * @author zhaozihanzzh
 */

public class SettingsProvider extends ContentProvider {

	private static final UriMatcher MATCHER = new UriMatcher(UriMatcher.NO_MATCH);
	static {
		MATCHER.addURI("com.meui.settings", "int", 0);
		MATCHER.addURI("com.meui.settings", "String", 1);
		MATCHER.addURI("com.meui.settings", "float", 2);
		MATCHER.addURI("com.meui.settings", "boolean", 3);
	}
	@Override
	public boolean onCreate() {
		return true;
	}

	@Override
	public Cursor query(Uri p1, String[] p2, String name, String[] p4, String defValue) {
		int type = MATCHER.match(p1);
		if (MATCHER.match(p1) == -1) return null;
		SharedPreferences prefs = getContext().getSharedPreferences("com.meui.RomCtrl_preferences", Context.MODE_PRIVATE);
		MatrixCursor cursor = new MatrixCursor(new String[]{"result"});
		switch (type) {
			case 3:
				cursor.addRow(new String[]{ String.valueOf(prefs.getBoolean(name, Boolean.parseBoolean(defValue)))});
				break;
			case 0:
				cursor.addRow(new String[]{ String.valueOf(prefs.getInt(name, Integer.parseInt(defValue)))});
				break;
			case 2:
				cursor.addRow(new String[]{ String.valueOf(prefs.getFloat(name, Float.parseFloat(defValue)))});
				break;
			case 1:
				cursor.addRow(new String[]{ String.valueOf(prefs.getString(name, defValue))});
				break;
			default:
				cursor.addRow(new String[]{defValue});
				break;
		}
		return cursor;
	}

	@Override
	public String getType(Uri p1) {
		return null;
	}

	@Override
	public Uri insert(Uri p1, ContentValues p2) {
		return null;
	}

	@Override
	public int delete(Uri p1, String p2, String[] p3) {
		return 0;
	}

	@Override
	public int update(Uri p1, ContentValues p2, String p3, String[] p4) {
		return 0;
	}
}
