package com.hj.myproject;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

import java.util.ArrayList;

public class ToDoDatabase extends SQLiteOpenHelper {

    public ToDoDatabase(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE IF NOT EXISTS data(date TEXT, todo TEXT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public void insert(String date,String todo){
        SQLiteDatabase db = getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("date",date);
        contentValues.put("todo",todo);
        db.insert("data",null,contentValues);
    }

    public ArrayList<String> select(String date){
        SQLiteDatabase db = getWritableDatabase();
        String query = "SELECT todo FROM data WHERE date = '"+ date+"'";
        ArrayList<String> data = new ArrayList<>();

        Cursor cursor = db.rawQuery(query,null);
        while(cursor.moveToNext()){
            String todo = cursor.getString(0);
            Log.d("yousin",todo);
            data.add(todo);
        }
        return data;
    }
}
