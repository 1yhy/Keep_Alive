package com.example.keep.worker;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

import com.example.keep.service.KeepAliveJobService;

public class KeepLiveWork extends Worker {

    public KeepLiveWork(@NonNull Context context, @NonNull WorkerParameters workerParams) {
        super(context, workerParams);
    }

    @NonNull
    @Override
    public Result doWork() {
        Log.e("KeepAlive", "doWork: ");

        KeepAliveJobService.startJob(getApplicationContext());

        return Result.success();
    }

    @Override
    public void onStopped() {
        Log.e("KeepAlive", "stopWork: ");
        KeepAliveJobService.stopJob(getApplicationContext());
        super.onStopped();
    }
}