package com.example.jasonjan.ipcdemo;

import android.app.Service;
import android.content.ComponentName;
import android.content.Intent;
import android.os.Binder;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.widget.Toast;

import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

/**
 * tips :服务
 * *
 * time : 18-11-1下午8:24
 * owner: jasonjan
 */
public class MainService extends Service {

    private Timer timer;//定时任务
    private static final String APP1PACKGENAME="com.example.ipcdemo1";
    private static final String APP1MAINACTIVITY="com.example.ipcdemo1.MainActivity";
    private ServiceHelp mHelper = new ServiceHelp();

    public class ServiceHelp extends Binder {
        public MainService getMainService(){
            return MainService.this;
        }
    }

    private Handler mHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case 0:
                    Intent intent = new Intent(MainService.this, MainActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
                    Toast.makeText(MainService.this,"跳转到APP0",Toast.LENGTH_SHORT).show();
                   break;
                case 1:
                    ComponentName componentName=new ComponentName(APP1PACKGENAME,APP1MAINACTIVITY);
                    Intent intent1=new Intent();
                    intent1.setComponent(componentName);
                    intent1.setAction("android.intent.action.VIEW");
                    intent1.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent1);
                    Toast.makeText(MainService.this,"跳转到APP1",Toast.LENGTH_SHORT).show();
                    break;
            }
        }
    };

    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    public IBinder onBind(Intent intent) {
        doTask();
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
        if(timer!=null){
            timer.cancel();
        }
    }

    /**
     * 开始任务
     */
    public void doTask(){
        timer = new Timer(true);

        TimerTask task = new TimerTask() {
            public void run() {
                //产生一个value
                Random random = new Random();
                int i = random.nextInt(1000);
                int value=i%2;

                //跳转逻辑
                if(value==0){
                    mHandler.sendEmptyMessage(0);
                }else{
                    mHandler.sendEmptyMessage(1);
                }
            }
        };

        timer.schedule(task,3000,3000);
    }

}
