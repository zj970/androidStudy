package com.zj970.broadcasttest;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

public class MainActivity extends AppCompatActivity {

    private IntentFilter intentFilter;
    private NetWorkChangeReceiver receiver;

    private LocalReceiver localReceiver;
    private LocalBroadcastManager localBroadcastManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        localBroadcastManager = LocalBroadcastManager.getInstance(this);//获取实例
        Button button = findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
/*                Intent intent = new Intent("com.zj970.broadcasttest.MY_BROADCAST");
                intent.setPackage("com.zj970.broadcasttest");//安卓8.0后静态注册的广播无法接收需要添加Intent.sendComponent();在暗中9.0后对静态注册则需要发送指定app所在的包名
                //intent.setPackage("com.zj970.broadcasttest2");
                //sendBroadcast(intent);
                sendOrderedBroadcast(intent,null);
                intent.setPackage("com.zj970.broadcasttest2");
                sendBroadcast(intent);*/

                Intent intent = new Intent("com.zj970.broadcasttest.LOCAL_BROADCAST");
                localBroadcastManager.sendBroadcast(intent);//发送本地广播
            }
        });

        intentFilter = new IntentFilter();
/*        intentFilter.addAction("android.net.conn.CONNECTIVITY_CHANGE");//ConnectivityManager.CONNECTIVITY_ACTION
        receiver = new NetWorkChangeReceiver();
        registerReceiver(receiver, intentFilter);//动态注册*/
        intentFilter.addAction("com.zj970.broadcasttest.LOCAL_BROADCAST");
        localReceiver = new LocalReceiver();
        localBroadcastManager.registerReceiver(localReceiver,intentFilter);//注册本地广播监听器
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (receiver != null) {
            /*unregisterReceiver(receiver);*/
            localBroadcastManager.unregisterReceiver(localReceiver);
        }
    }

    class NetWorkChangeReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {

            ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();//这里要添加一个权限    <uses-permission android:name="android.permission.ACCESS_NETWORK_STATE"/>

            if (networkInfo != null && networkInfo.isConnected()) {
                Toast.makeText(context, "network is connected", Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(context, "network is no connected", Toast.LENGTH_LONG).show();
            }
        }
    }

    class LocalReceiver extends BroadcastReceiver{

        @Override
        public void onReceive(Context context, Intent intent) {
            Toast.makeText(context,"receiver local broadcast",Toast.LENGTH_SHORT).show();
        }
    }
}