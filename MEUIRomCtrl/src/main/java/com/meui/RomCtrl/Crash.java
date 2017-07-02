package com.meui.RomCtrl;
import android.app.*;
import android.content.*;
import android.os.*;
import android.view.*;
import android.widget.*;

/**
 * When app crashes, start this activity.
 * @author zhaozihanzzh
 */

public class Crash extends Activity implements View.OnClickListener
{
    private String log;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setTheme(R.style.CrashTheme);
        
        log=getIntent().getStringExtra("log");
        setContentView(R.layout.crash);
        final TextView error=(TextView)findViewById(R.id.crash_text);
        error.setText(log);
        
        findViewById(R.id.crash_copy).setOnClickListener(this);
        findViewById(R.id.crash_close).setOnClickListener(this);
    }

    @Override
    public void onClick(View p1)
    {
        switch(p1.getId()){
            case R.id.crash_close:
                finish();
                System.exit(0);
                // System.exit(0) is used for avoiding opening a recent app while exiting.
                break;
            case R.id.crash_copy:
                final android.text.ClipboardManager cmb=(android.text.ClipboardManager)this.getSystemService(Context.CLIPBOARD_SERVICE);
                cmb.setText(log);
                Toast.makeText(this,"已将Log复制到剪贴板。",Toast.LENGTH_SHORT).show();
                break;
        }
    }
}
