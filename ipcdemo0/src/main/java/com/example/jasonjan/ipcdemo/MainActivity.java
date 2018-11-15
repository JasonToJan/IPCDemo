package com.example.jasonjan.ipcdemo;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";
    private boolean mIsBind = false;
    private boolean mIsConnected = false;
    private MainService mMainService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    /**
     * 按钮：模拟任务
     * @param v
     */
    public void timerTask(View v){
        if(null == startService(new Intent(MainActivity.this,MainService.class))){
            new Throwable("无法启动服务");
        }
        mIsBind = bindService(new Intent(MainActivity.this,MainService.class),mConnection, Context.BIND_AUTO_CREATE);
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
