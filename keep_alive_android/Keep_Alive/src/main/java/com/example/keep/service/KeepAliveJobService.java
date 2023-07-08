package com.example.keep.service;


import android.annotation.SuppressLint;
import android.app.job.JobInfo;
import android.app.job.JobParameters;
import android.app.job.JobScheduler;
import android.app.job.JobService;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.util.Log;

import com.example.keep.utils.util;

@SuppressLint("SpecifyJobSchedulerIdRange")
public class KeepAliveJobService extends JobService {
    @Override
    public boolean onStartJob(JobParameters jobParameters) {
        if (Build.VERSION.SDK_INT>= Build.VERSION_CODES.O){
            startJob(this);
        }
        boolean isRunning = util.isRunningService(this, KeepAliveJobService.class.getName());
        if (!isRunning) {
            startService(new Intent(this, KeepAliveJobService.class));
        }
        return false;
    }

    public static void startJob(Context context) {
        JobScheduler jobScheduler = (JobScheduler) context.getSystemService(JOB_SCHEDULER_SERVICE);
        JobInfo.Builder builder = new JobInfo.Builder(27,
                new ComponentName(context.getPackageName(), KeepAliveJobService.class.getName()))
                .setPersisted(true);
        if (Build.VERSION.SDK_INT<Build.VERSION_CODES.N){
            builder.setPeriodic(1000);
        }else {
            builder.setMinimumLatency(1000);
        }
        jobScheduler.schedule(builder.build());
        Log.e("JobService", "jobService: ");
    }

    public static void stopJob(Context context){
        Log.e("JobService", "stop");
        JobScheduler jobScheduler = (JobScheduler) context.getSystemService(JOB_SCHEDULER_SERVICE);
        jobScheduler.cancel(27);
    }

    @Override
    public boolean onStopJob(JobParameters jobParameters) {
        Log.e("JobService", "stop_jobService: ");
        return false;
    }
}
