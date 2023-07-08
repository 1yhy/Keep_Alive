package com.example.keep.activity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.PowerManager;
import android.util.Log;
import android.view.Gravity;
import android.view.Window;
import android.view.WindowManager;

import androidx.appcompat.app.AppCompatActivity;

public class OnePxActivity  extends AppCompatActivity {

    private static final String TAG = OnePxActivity.class.getSimpleName();

    private BroadcastReceiver br;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Window window = getWindow();
        window.setGravity(Gravity.LEFT | Gravity.TOP);
        WindowManager.LayoutParams params = window.getAttributes();
        params.x = 0;
        params.y = 0;
        params.height = 1;
        params.width = 1;
        window.setAttributes(params);
        //结束该页面的广播
        br = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                Log.d(TAG,"OnepxActivity finish   ================");
                finish();
            }
        };
        registerReceiver(br, new IntentFilter("finish activity"));

        //检查屏幕状态
        checkScreenOn("onCreate");
    }

    /**
     * 检查屏幕状态  isScreenOn为true  屏幕“亮”结束该Activity
     * */
    private void checkScreenOn(String methodName) {
        Log.d(TAG,"from call method: " + methodName);
        PowerManager pm = (PowerManager) OnePxActivity.this.getSystemService(Context.POWER_SERVICE);
        boolean isScreenOn = pm.isScreenOn();
        Log.i(TAG,"isScreenOn: "+isScreenOn);
        if(isScreenOn){
            finish();
        }
    }

    @Override
    protected void onDestroy() {
        Log.i(TAG,"===onDestroy===");
        try{
            unregisterReceiver(br);
        }catch (IllegalArgumentException e){
            Log.e(TAG,"receiver is not resisted: "+e);
        }
        super.onDestroy();
    }

    @Override
    protected void onResume() {
        super.onResume();
        checkScreenOn("onResume");
    }
}
