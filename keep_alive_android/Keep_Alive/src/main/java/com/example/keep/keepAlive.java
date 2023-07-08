package com.example.keep;

import android.app.Activity;
import android.content.Intent;
import android.util.Log;

import androidx.work.OneTimeWorkRequest;
import androidx.work.WorkManager;

import com.alibaba.fastjson.JSONObject;
import com.example.keep.receiver.OnePxReceiver;
import com.example.keep.service.ForegroundService;
import com.example.keep.service.KeepAliveJobService;
import com.example.keep.worker.KeepLiveWork;

import org.json.JSONException;

import java.util.concurrent.TimeUnit;

import io.dcloud.feature.uniapp.annotation.UniJSMethod;
import io.dcloud.feature.uniapp.bridge.UniJSCallback;
import io.dcloud.feature.uniapp.common.UniModule;

public class keepAlive extends UniModule {

    String TAG = "KEEP_ALIVE";
    @UniJSMethod(uiThread = true)
    public void start(JSONObject options, UniJSCallback callback) throws JSONException {
        try {
            Log.e(TAG, "testAsyncFunc--"+options);
            if(mUniSDKInstance != null && mUniSDKInstance.getContext() instanceof Activity) {
                Intent intent = new Intent(mUniSDKInstance.getContext(), ForegroundService.class);
                intent.putExtra("title",options.getString("title"));
                intent.putExtra("text",options.getString("text"));
//                 开启前台通知服务
                mUniSDKInstance.getContext().startService(intent);

//                 开启一像素服务
                if(!options.containsKey("onePxEnabled") || options.getBoolean("onePxEnabled")){
                    OnePxReceiver.register1pxReceiver(mUniSDKInstance.getContext());
                }

//                 添加任务
                if(!options.containsKey("workerManager") || options.getBoolean("workerManager")) {
                    OneTimeWorkRequest oneTimeWorkRequest = new OneTimeWorkRequest
                            .Builder(KeepLiveWork.class)
                            .setInitialDelay(10, TimeUnit.SECONDS)
                            .build();
                    WorkManager.getInstance(mUniSDKInstance.getContext()).enqueue(oneTimeWorkRequest);
                }
            }
            if(callback != null) {
                JSONObject data = new JSONObject();
                data.put("code", 1);
                callback.invoke(data);
                //callback.invokeAndKeepAlive(data);
            }
        }catch (Exception e){
            if(callback != null) {
                JSONObject data = new JSONObject();
                data.put("code", 2);
                data.put("err",e.getMessage());
                callback.invoke(data);
                //callback.invokeAndKeepAlive(data);
            }
        }
    }

    @UniJSMethod(uiThread = false)
    public void destroy(UniJSCallback callback){
        if(mUniSDKInstance != null && mUniSDKInstance.getContext() instanceof Activity) {
            Intent intent = new Intent(mUniSDKInstance.getContext(), ForegroundService.class);
            mUniSDKInstance.getContext().stopService(intent);

            OnePxReceiver.unregister1pxReceiver(mUniSDKInstance.getContext());
            KeepAliveJobService.stopJob(mUniSDKInstance.getContext());
        }
        if(callback != null) {
            JSONObject data = new JSONObject();
            data.put("code", 0);
            callback.invoke(data);
            //callback.invokeAndKeepAlive(data);
        }
    }
}
