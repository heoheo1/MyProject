package com.hj.myproject;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import java.util.ArrayList;

public class MyRemoteViewsFactory implements RemoteViewsService.RemoteViewsFactory {

    Context context;
    ArrayList<String> arrayList;
    String tableName ="tblToDo";
    RemoteViews listviewWidget;

    public MyRemoteViewsFactory(Context context) {
        this.context = context;
    }

    public void setData() {
        ToDoDatabase  database = ToDoDatabase.getInstance(context);
        SQLiteDatabase db = database.getWritableDatabase();

        String query = "SELECT * FROM "+ tableName + " WHERE isChecked = 0 ";
        arrayList = new ArrayList<>();

        Cursor cursor = db.rawQuery(query,null);
        while(cursor.moveToNext()){
            String todo = cursor.getString(0);
            arrayList.add(todo);
        }
    }


    @Override
    public void onCreate() {
        setData();
    }

    @Override
    public void onDataSetChanged() {
        setData();
    }

    @Override
    public void onDestroy() {

    }

    @Override
    public int getCount() {
        return arrayList.size();
    }

    //각 항목을 구현하기 위해 호출, 매개변수 값을 참조하여 각 항목을 구성하기위한 로직이 담긴다.
    // 항목 선택 이벤트 발생 시 인텐트에 담겨야 할 항목 데이터를 추가해주어야 하는 함수
    @Override
    public RemoteViews getViewAt(int position) {

        listviewWidget = new RemoteViews(context.getPackageName(), R.layout.widget_item);
        listviewWidget.setTextViewText(R.id.text1, arrayList.get(position));

        // 항목 선택 이벤트 발생 시 인텐트에 담겨야 할 항목 데이터를 추가해주는 코드
        Intent dataIntent = new Intent();
        dataIntent.putExtra("item_data", arrayList.get(position));
        listviewWidget.setOnClickFillInIntent(R.id.text1, dataIntent);
        //setOnClickFillInIntent 브로드캐스트 리시버에서 항목 선택 이벤트가 발생할 때 실행을 의뢰한 인텐트에 각 항목의 데이터를 추가해주는 함수
        //브로드캐스트 리시버의 인텐트와 Extra 데이터가 담긴 인텐트를 함치는 역할을 한다.

        return listviewWidget;
    }
    @Override
    public RemoteViews getLoadingView() {
        RemoteViews remoteViews =new RemoteViews(context.getPackageName(),R.layout.widget_item);
        return remoteViews;
    }
    //항목의 타입 갯수를 판단하기 위해 호출, 모든 항목이 같은 뷰 타입이라면 1을 반환하면 된다.
    @Override
    public int getViewTypeCount() {
        return 1;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }
}
