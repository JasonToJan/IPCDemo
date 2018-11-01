package com.example.jasonjan.ipcdemo;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.graphics.drawable.BitmapDrawable;
import android.os.IBinder;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RemoteViews;
import android.widget.Toast;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";

    private boolean mIsBind = false;
    private boolean mIsConnected = false;
    private MainService mMainService;
    private final int NOTIFICATION_ID = 98;
    private boolean mIsForegroundService = false;
    private static final String APP1PACKGENAME="com.example.ipcdemo1";
    private static final String APP1MAINACTIVITY="com.example.ipcdemo1.MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    protected void onStart() {
        super.onStart();
        if(null == startService(new Intent(MainActivity.this,MainService.class))){
            new Throwable("无法启动服务");
        }
        mIsBind = bindService(new Intent(MainActivity.this,MainService.class),mConnection, Context.BIND_AUTO_CREATE);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if(mIsConnected && mIsForegroundService){
            mMainService.stopForeground(true);
            mIsForegroundService = false;
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        if(mIsBind && mMainService != null && !mIsForegroundService){
            mMainService.startForeground(NOTIFICATION_ID,getNotification());
            mIsForegroundService = true;
        }
    }

    /**
     * 产生一个前台通知
     * @return
     */
    private Notification getNotification(){

        RemoteViews remoteViews = new RemoteViews(this.getPackageName(), R.layout.view_notification_type_0);
        //定义值为0时跳转到App0的逻辑
        Intent intent0 = new Intent(this, MainActivity.class);
        PendingIntent pendingIntent0=PendingIntent.getActivity(this,100,intent0,PendingIntent.FLAG_UPDATE_CURRENT);
        remoteViews.setOnClickPendingIntent(R.id.value0_btn,pendingIntent0);

        //定义值为1时跳转到App1的逻辑
        Intent intent1=new Intent(Intent.ACTION_MAIN);
        ComponentName componentName = new ComponentName(APP1PACKGENAME, APP1MAINACTIVITY);
        intent1.setComponent(componentName);
        PendingIntent pendingIntent1=PendingIntent.getActivity(this,101,intent1,PendingIntent.FLAG_UPDATE_CURRENT);
        remoteViews.setOnClickPendingIntent(R.id.value1_btn,pendingIntent1);


        NotificationCompat.Builder builder = new NotificationCompat.Builder(this);
        builder.setOngoing(true);
        builder.setPriority(NotificationCompat.PRIORITY_MAX);
        builder.setSmallIcon(R.drawable.notification);

        Notification notification = builder.build();
        notification.contentView = remoteViews;

        return notification;
    }

    private ServiceConnection mConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            mMainService = ((MainService.ServiceHelp)service).getMainService();
            if (mMainService != null){
                MainActivity.this.mIsConnected = true;
            }else{
                new Throwable("服务绑定失败");
            }
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            MainActivity.this.mIsConnected = false;
        }
    };
}
