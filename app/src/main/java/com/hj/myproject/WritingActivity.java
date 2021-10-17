package com.hj.myproject;

import androidx.annotation.NonNull;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.widget.EditText;
import android.widget.Toast;

import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.ObservableEmitter;
import io.reactivex.rxjava3.core.ObservableOnSubscribe;
import io.reactivex.rxjava3.subjects.BehaviorSubject;

public class WritingActivity extends Dialog {

    EditText edtTodo;
    ToDoDatabase db;
    String today;

    public WritingActivity(@NonNull Context context,String today, ToDoDatabase db) {
        super(context);
        this.today = today;
        this.db = db;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_writing);
        setCancelable(true); //바깥족 취소
        getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT)); // 배경 투명

        init(); // UI ID찾기

        findViewById(R.id.btnOK).setOnClickListener(v -> { //OK버튼 클릭시
            String todo = edtTodo.getText().toString();
            if(!todo.isEmpty()){
                db.currentTable();
                db.insert(today,todo);
                edtTodo.setText("");
                dismiss();
            }else{
                Toast.makeText(getContext(), "텍스트를 입력해 주세요", Toast.LENGTH_SHORT).show();
            }

        });
    }

    private void init(){
        edtTodo = findViewById(R.id.edtTodo);
    }
}
