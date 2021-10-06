package com.hj.myproject;

import androidx.annotation.NonNull;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.widget.EditText;

public class WritingActivity extends Dialog {

    EditText edtTodo;
    ToDoDatabase db;
    String today;

    public WritingActivity(@NonNull Context context,String today) {
        super(context);
        this.today = today;
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
            db.insert(today,todo);
            edtTodo.setText("");
            dismiss();
        });
    }

    private void init(){
        edtTodo = findViewById(R.id.edtTodo);
        db = new ToDoDatabase(getContext(),"data",null,1);
    }
}
