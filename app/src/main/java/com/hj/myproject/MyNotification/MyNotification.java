package com.hj.myproject.MyNotification;

import static android.content.Context.ACTIVITY_SERVICE;
import static android.content.Context.ALARM_SERVICE;

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
import android.os.SystemClock;
import android.util.Log;
import android.widget.ImageView;

import androidx.core.content.ContextCompat;

import com.hj.myproject.R;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class MyNotification {

    private static MyNotification notification;
    private Notification.Builder builder;
    Context context;
    private AlarmManager alarm;

    private  MyNotification(Context context){
        this.context = context;
        init();
    }
    public static MyNotification getInstance(Context context){
        if(notification == null){
            notification = new MyNotification(context);
        }
        return notification;
    }

    private void init() {
        createNotificationChannel();
        alarm = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
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

    public void setNotificationAlarm(String title, String contents) {
        Intent intent = new Intent(context,MyNotificationReceiver.class);
        intent.putExtra("sendTitle", title);
        intent.putExtra("sendMessage", contents);

        //BroadCast로 보내는 PendingIntent, 단 31(최신버전)부터는 FLAG_IMMUTABLE을 사용해주어야 한다.
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, 0, intent, PendingIntent.FLAG_IMMUTABLE);

        GregorianCalendar calendar1 = new GregorianCalendar();

        //PendingIntent의 requestCode를 다르게 줌으로써, Alarm을 여러개 등록이 가능하다.
        calendar1.set(GregorianCalendar.HOUR_OF_DAY,9);
        calendar1.set(GregorianCalendar.MINUTE,0);
        calendar1.set(GregorianCalendar.SECOND,0);
        calendar1.set(GregorianCalendar.MILLISECOND,0);

        alarm.set(AlarmManager.RTC_WAKEUP,calendar1.getTimeInMillis(),pendingIntent);

        calendar1.set(GregorianCalendar.HOUR_OF_DAY,12);
        calendar1.set(GregorianCalendar.MINUTE,0);
        calendar1.set(GregorianCalendar.SECOND,0);
        calendar1.set(GregorianCalendar.MILLISECOND,0);

        alarm.set(AlarmManager.RTC_WAKEUP,calendar1.getTimeInMillis(),PendingIntent.getBroadcast(context,1,intent,PendingIntent.FLAG_IMMUTABLE));

        calendar1.set(GregorianCalendar.HOUR_OF_DAY,18);
        calendar1.set(GregorianCalendar.MINUTE,0);
        calendar1.set(GregorianCalendar.SECOND,0);
        calendar1.set(GregorianCalendar.MILLISECOND,0);

        alarm.set(AlarmManager.RTC_WAKEUP,calendar1.getTimeInMillis(),PendingIntent.getBroadcast(context,2,intent,PendingIntent.FLAG_IMMUTABLE));
    }

    public void cancelAlarm() {
        Intent intent = new Intent(context,MyNotificationReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context,0,intent,0);
        alarm.cancel(pendingIntent);
        alarm.cancel(PendingIntent.getBroadcast(context,1,intent,0));
        alarm.cancel(PendingIntent.getBroadcast(context,2,intent,0));
    }

    public void sendMessage(String sendTitle, String sendMessage) {
        builder.setContentTitle(sendTitle)
                .setContentText(sendMessage)
                .setSmallIcon(R.mipmap.ic_launcher);

        Notification notification = builder.build();
        ((NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE)).notify(1, notification);
    }
}