package com.meui.RomCtrl;
import android.content.*;
import android.preference.*;
import android.provider.*;
import android.util.*;
import android.widget.*;
import java.util.*;
import android.os.*;

/**
 * This Preference activity is used to control the Materialish ProgressBar in MEUI.
 * @author zhaozihanzzh
 */

public final class RoundProgress extends PreferenceActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		addPreferencesFromResource(R.xml.wheel_progress);
	}
}
