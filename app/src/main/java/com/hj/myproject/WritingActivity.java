package com.hj.myproject;

import androidx.annotation.NonNull;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.widget.EditText;

import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.ObservableEmitter;
import io.reactivex.rxjava3.core.ObservableOnSubscribe;
import io.reactivex.rxjava3.subjects.BehaviorSubject;

public class WritingActivity extends Dialog {

    EditText edtTodo;
    ToDoDatabase db;

    public WritingActivity(@NonNull Context context,ToDoDatabase db) {
        super(context);
        this.db = db;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_writing);
        setCancelable(false); //바깥족 취소 불가
        getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT)); // 배경 투명

        init(); // UI ID찾기

        findViewById(R.id.btnOK).setOnClickListener(v -> { //OK버튼 클릭시
            String todo = edtTodo.getText().toString();
            db.currentTable();
            db.insert(todo);
            edtTodo.setText("");
            dismiss();
        });
    }

    private void init(){
        edtTodo = findViewById(R.id.edtTodo);
    }
}
