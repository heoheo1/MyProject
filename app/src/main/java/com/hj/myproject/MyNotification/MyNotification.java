package com.hj.myproject.MyNotification;

import static android.content.Context.ACTIVITY_SERVICE;

import android.app.ActivityManager;
import android.app.AlarmManager;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Build;
import android.util.Log;
import android.widget.ImageView;

import androidx.core.content.ContextCompat;

import com.hj.myproject.R;

import java.util.Calendar;

public class MyNotification {

    private Notification.Builder builder;
    Context context;

    public MyNotification(Context context) {
        this.context = context;
        init();
    }

    private void init() {
        context.registerReceiver(receiver,new IntentFilter("com.hj.myProject.MyNotificationBroadcast"));
        createNotificationChannel();
    }

    public void changeNotificationImage(ImageView notificationImage, boolean isNotify) { //알람버튼을 클릭하면 이미지가 바뀌어야 한다!
        if (isNotify) {
            notificationImage.setBackground(ContextCompat.getDrawable(context, R.drawable.ic_baseline_notifications_active_24));
        } else {
            notificationImage.setBackground(ContextCompat.getDrawable(context, R.drawable.ic_baseline_notifications_off_24));
        }
    }

    private void createNotificationChannel() { //NotificationChannel 생성하기
        NotificationManager manager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(context.getString(R.string.app_name), context.getString(R.string.app_name), NotificationManager.IMPORTANCE_DEFAULT);
            manager.createNotificationChannel(channel);
            builder = new Notification.Builder(context, context.getString(R.string.app_name));
        } else {
            builder = new Notification.Builder(context);
        }
    }

    public void setNotificationAlarm(String title, String contents, int minute) {
        Log.d("yousin", "알람!");
        AlarmManager manager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);

        Calendar calendar = Calendar.getInstance();

        int hour = calendar.get(Calendar.HOUR);
        int min = minute;
        int seconds = calendar.get(Calendar.SECOND);

        Log.d("yousin","start time : "+hour+":"+min+":"+seconds);

        Intent intent = new Intent("com.hj.myProject.MyNotificationBroadcast");
        intent.putExtra("sendTitle", title);
        intent.putExtra("sendMessage", contents);

        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        calendar.set(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DATE), hour, minute, seconds);

        Log.d("yousin",(calendar.getTimeInMillis() / (1000 * 60)) % 60+"");

        if(Build.VERSION.SDK_INT >= 23) {
            manager.setExactAndAllowWhileIdle(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pendingIntent);
        }else{
            manager.set(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pendingIntent);
        }
    }

    public void cancelAlarm(){

    }

    public void sendMessage(String sendTitle, String sendMessage) {
        builder.setContentTitle(sendTitle)
                .setContentText(sendMessage)
                .setSmallIcon(R.mipmap.ic_launcher);

        Notification notification = builder.build();
        ((NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE)).notify(1, notification);
    }

    private BroadcastReceiver receiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            Log.d("yousin","broadcast 도착!");
            String sendTitle = intent.getStringExtra("sendTitle");
            String sendMessage = intent.getStringExtra("sendMessage");

            sendMessage(sendTitle, sendMessage);
        }
    };
}