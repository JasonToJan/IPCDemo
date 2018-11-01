package com.example.jasonjan.ipcdemo;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;

/**
 * tips :服务
 * *
 * time : 18-11-1下午8:24
 * owner: jasonjan
 */
public class MainService extends Service {

    private ServiceHelp mHelper = new ServiceHelp();

    public class ServiceHelp extends Binder {
        public MainService getMainService(){
            return MainService.this;
        }
    }

    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    public IBinder onBind(Intent intent) {
        return mHelper;
    }

    @Override
    public void onRebind(Intent intent) {
        super.onRebind(intent);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return super.onStartCommand(intent, flags, startId);
    }


    @Override
    public boolean onUnbind(Intent intent) {
        return super.onUnbind(intent);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

}
