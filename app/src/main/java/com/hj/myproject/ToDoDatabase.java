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
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class ToDoDatabase extends SQLiteOpenHelper {
    private static ToDoDatabase database;
    Context context;
    String tableName = "tblToDo";


    private ToDoDatabase(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
        this.context = context;
    }

    public static ToDoDatabase getInstance(@Nullable Context context){
        if(database == null){
            database = new ToDoDatabase(context,"database",null,1);
        }
        return database;
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE IF NOT EXISTS tblToDo (todo TEXT PRIMARY KEY)");
        db.execSQL("CREATE TABLE IF NOT EXISTS tblNotToDo (todo TEXT PRIMARY KEY)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public void insert(String todo){
        SQLiteDatabase db = getWritableDatabase();
        try {
            String query = "INSERT INTO "+tableName+" VALUES('" + todo + "')";
            db.execSQL(query);
        }catch(Exception e){
            e.printStackTrace();
            Toast.makeText(context, "이미 등록된 내용입니다.", Toast.LENGTH_SHORT).show();
        }
    }

    public ArrayList<String> select(){
        SQLiteDatabase db = getWritableDatabase();
        String query = "SELECT * FROM "+ tableName;
        ArrayList<String> data = new ArrayList<>();

        Cursor cursor = db.rawQuery(query,null);
        while(cursor.moveToNext()){
            String todo = cursor.getString(0);
            data.add(todo);
        }
        return data;
    }

    public void delete(String todo){
        SQLiteDatabase db = getWritableDatabase();
        String query = "DELETE FROM "+tableName+" WHERE todo = '"+todo+"'";

        db.execSQL(query);
    }

    public void setTableName(String tableName){
        this.tableName = tableName;
    }

    public void clear(){
        SQLiteDatabase db = getWritableDatabase();
        String query = "DELETE FROM "+tableName;

        db.execSQL(query);
    }

    //현재 테이블 확인
    public void currentTable(){
        Log.d("yousin","현재 테이블 : "+tableName);
    }
}
