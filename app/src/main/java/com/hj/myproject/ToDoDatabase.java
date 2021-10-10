package com.hj.myproject;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class ToDoDatabase extends SQLiteOpenHelper {
    Context context;
    String tableName = "tblToDo";

    public ToDoDatabase(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE IF NOT EXISTS tblToDo (date TEXT, todo TEXT PRIMARY KEY)");
        db.execSQL("CREATE TABLE IF NOT EXISTS tblNotToDo (date TEXT, todo TEXT PRIMARY KEY)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public void insert(String date,String todo){
        SQLiteDatabase db = getWritableDatabase();
        if(todo.equals("")){
            Toast.makeText(context, "할일을 작성해주세요!", Toast.LENGTH_SHORT).show();
            return;
        }
        try {
            String query = "INSERT INTO "+tableName+" VALUES('" + date + "','" + todo + "')";
            db.execSQL(query);
        }catch(Exception e){
            e.printStackTrace();
            Toast.makeText(context, "이미 등록된 내용입니다....", Toast.LENGTH_SHORT).show();
        }
    }

    public ArrayList<String> select(String date){
        SQLiteDatabase db = getWritableDatabase();
        String query = "SELECT todo FROM "+ tableName +" WHERE date = '"+ date+"'";
        ArrayList<String> data = new ArrayList<>();

        Cursor cursor = db.rawQuery(query,null);
        while(cursor.moveToNext()){
            String todo = cursor.getString(0);
            data.add(todo);
        }
        return data;
    }

    public void delete(String date, String todo){
        SQLiteDatabase db = getWritableDatabase();
        String query = "DELETE FROM "+tableName+" WHERE todo = '"+todo+"' AND date = '"+date+"'";

        db.execSQL(query);
    }

    public void tableChange(){
        Log.d("yousin",tableName);
        if(tableName.equals("tblToDo")){
            tableName = "tblNotToDo";
        }else{
            tableName = "tblToDo";
        }
        Log.d("yousin","바뀐후 : "+tableName);
    }

    //현재 테이블 확인
    public void currentTable(){
        Log.d("yousin","현재 테이블 : "+tableName);
    }
}
