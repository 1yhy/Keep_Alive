package com.example.keep.service;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.os.Build;
import android.os.IBinder;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.example.keep.R;
import com.example.keep.constant.KeepAlive;


public class ForegroundService extends Service  {
    private final static int SERVICE_ID = 1;
    private static final String TAG = ForegroundService.class.getSimpleName();
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {

        super.onCreate();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        if (Build.VERSION.SDK_INT<Build.VERSION_CODES.JELLY_BEAN_MR2){
            //4.3以下
            startForeground(SERVICE_ID,new Notification());
        }else if (Build.VERSION.SDK_INT<Build.VERSION_CODES.O){
            //7.0以下
            startForeground(SERVICE_ID,new Notification());
            //删除通知栏
            startService(new Intent(this,InnerService.class));
        }else {
            //8.0以上
            NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
            //NotificationManager.IMPORTANCE_MIN 通知栏消息的重要级别  最低，不让弹出
            //IMPORTANCE_MIN 前台时，在阴影区能看到，后台时 阴影区不消失，增加显示 IMPORTANCE_NONE时 一样的提示
            //IMPORTANCE_NONE app在前台没有通知显示，后台时有
            NotificationChannel channel = new NotificationChannel("channel", "后台运行", NotificationManager.IMPORTANCE_NONE);
            // 取消小红点
            channel.enableLights(false);
            if (notificationManager!=null){
                CharSequence title = intent.getStringExtra("title");
                CharSequence text = intent.getStringExtra("text");
                // 点击跳转页面
                PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_IMMUTABLE);
                notificationManager.createNotificationChannel(channel);
                NotificationCompat.Builder builder = new NotificationCompat.Builder(this, "channel");
                builder.setContentTitle(title)
                        .setSmallIcon(R.drawable.baseline_lock_24) //图标
                        .setContentText(text) //标题
                        .setPriority(NotificationCompat.PRIORITY_DEFAULT) // 设置优先级 PRIORITY_DEFAULT
                        .setWhen(System.currentTimeMillis()) // 设置通知发送的时间戳
                        .setContentIntent(pendingIntent) // 跳转页面
                        .setVisibility(NotificationCompat.VISIBILITY_SECRET) // 屏幕可见性，1、VISIBILITY_PUBLIC 在所有锁定屏幕上完整显示此通知 2、VISIBILITY_PRIVATE 隐藏安全锁屏上的敏感或私人信息 3、VISIBILITY_SECRET 不显示任何部分
                        .setOngoing(true);
                Log.e(TAG, "onStart");
                NotificationManagerCompat notificationManagerCompat = NotificationManagerCompat.from(this);
                notificationManagerCompat.notify(SERVICE_ID,builder.build());
                startForeground(SERVICE_ID,builder.build());
            }

        }
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        Log.e(TAG, "onDestroy");
        NotificationManagerCompat notificationManagerCompat = NotificationManagerCompat.from(this);
        notificationManagerCompat.cancelAll();
        stopForeground(true);
        super.onDestroy();
    }

    private static class InnerService extends Service{
        @Nullable
        @Override
        public IBinder onBind(Intent intent) {
            return null;
        }

        @Override
        public void onCreate() {
            super.onCreate();
            Log.d(KeepAlive.TAG, "onCreate: ");
            startForeground(SERVICE_ID,new Notification());
            stopSelf();
        }
    }
}
