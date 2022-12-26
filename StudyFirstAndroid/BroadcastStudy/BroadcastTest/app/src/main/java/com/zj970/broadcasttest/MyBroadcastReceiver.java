package com.zj970.broadcasttest;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

public class MyBroadcastReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        // TODO: This method is called when the BroadcastReceiver is receiving
        // an Intent broadcast.
        Toast.makeText(context,"received in MyBroadcastReceiver",Toast.LENGTH_LONG).show();
        abortBroadcast();
        Log.d("zj970", "onReceive: MyBroadcastReceiver");
    }
}