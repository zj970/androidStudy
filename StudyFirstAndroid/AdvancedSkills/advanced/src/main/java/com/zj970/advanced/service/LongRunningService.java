package com.zj970.advanced.service;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.SystemClock;
import androidx.annotation.Nullable;

/**
 * <p>
 *
 * </p>
 *
 * @author: zj970
 * @date: 2022/12/16
 */
public class LongRunningService extends Service {
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                //在这里执行具体的逻辑操作
            }
        }).start();

        AlarmManager manager = (AlarmManager) getSystemService(ALARM_SERVICE);
        int anHour = 60 * 60 * 1000;//这是一小时的毫秒数
        long triggerAtTime = SystemClock.elapsedRealtime() + anHour ;
        Intent i = new Intent(this, LongRunningService.class);
        PendingIntent pi = PendingIntent.getActivity(this,0,i,0);
        manager.set(AlarmManager.ELAPSED_REALTIME_WAKEUP,triggerAtTime,pi);
        return super.onStartCommand(intent, flags, startId);
    }
}
