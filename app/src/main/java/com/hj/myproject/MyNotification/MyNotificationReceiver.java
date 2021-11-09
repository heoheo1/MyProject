package com.hj.myproject.MyNotification;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

public class MyNotificationReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {

        MyNotification notification = MyNotification.getInstance(context);
        Log.d("yousin", "broadcast 도착!");
        String sendTitle = intent.getStringExtra("sendTitle");
        String sendMessage = intent.getStringExtra("sendMessage");

        notification.sendMessage(sendTitle, sendMessage);
    }
}