package com.hj.myproject;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.Service;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Handler;
import android.os.IBinder;
import android.util.Log;
import android.widget.RemoteViews;
import android.widget.TextClock;

import java.util.Timer;
import java.util.TimerTask;

public class WidgetProvider extends AppWidgetProvider {


    @Override public void onReceive(Context context, Intent intent) {
        super.onReceive(context, intent);


    }
    /** * 위젯을 갱신할때 호출됨 * * 주의 : Configure Activity를 정의했을때는 위젯 등록시 처음 한번은 호출이 되지 않습니다 */
    @Override public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        // There may be multiple widgets active, so update all of them
        super.onUpdate(context, appWidgetManager, appWidgetIds);
        Timer timer = new Timer();
        timer.scheduleAtFixedRate(new ManiTime(context, appWidgetManager), 1, 1000);
        appWidgetIds = appWidgetManager.getAppWidgetIds(new ComponentName(
                context, getClass()));
        for (int i = 0; i < appWidgetIds.length; i++) {
            updateAppWidget(context, appWidgetManager, appWidgetIds[i]);
        }

    }

    /** * 위젯이 처음 생성될때 호출됨 * * 동일한 위젯이 생성되도 최초 생성때만 호출됨 */
    @Override public void onEnabled(Context context) {

    }
    /** * 위젯의 마지막 인스턴스가 제거될때 호출됨 * * onEnabled()에서 정의한 리소스 정리할때 */
    @Override public void onDisabled(Context context) {
        super.onDisabled(context);
    }
    /** * 위젯이 사용자에 의해 제거될때 호출됨 */
    @Override public void onDeleted(Context context, int[] appWidgetIds) {
        super.onDeleted(context, appWidgetIds);
    }

    public static void updateAppWidget(Context context, AppWidgetManager appWidgetManager, int appWidgetId) {
       // RemoteViewsService 실행 등록시키는 함수
//        Intent serviceIntent = new Intent(context, MyRemoteViewsService.class);
//        RemoteViews widget = new RemoteViews(context.getPackageName(), R.layout.widget_layout);
//        widget.setRemoteAdapter(R.id.widget_list, serviceIntent);
////        클릭이벤트 인텐트 유보.
//        //보내기
//
//        Intent intent = new Intent(context,MainActivity.class);
//        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent, 0);
//        widget.setOnClickPendingIntent(R.id.mLayout, pendingIntent);
//
//        appWidgetManager.updateAppWidget(appWidgetId, widget);

    }

    private class ManiTime extends TimerTask {

        // 리모트 뷰를 만들고 ( 앱위젯은 리모트 뷰를 통해서 접근 가능하다 )

        RemoteViews remoteViews;


        // 앱위젯 메니저와

        AppWidgetManager appWidgetManager;


        // 이 위젯의 컴퍼넌트명을 선언한다.

        ComponentName thisWidget;
        Intent serviceIntent;


        // 생성자.

// 초기화를 시켜 준비한다.

        public ManiTime(Context context, AppWidgetManager appWidgetManager) {
            this.appWidgetManager = appWidgetManager;
            serviceIntent = new Intent(context, MyRemoteViewsService.class);
            remoteViews = new RemoteViews(context.getPackageName(), R.layout.widget_layout);
            thisWidget = new ComponentName(context, WidgetProvider.class);
        }


        // 오버라이드 해서 내용을 넣어준다.

// 실제 이 부분을 통해서 위젯의 내용이 화면에 보인다.

        @Override

        public void run() {


            remoteViews.setRemoteAdapter(R.id.widget_list, serviceIntent);
            appWidgetManager.updateAppWidget(thisWidget, remoteViews);

        }

    }

}


