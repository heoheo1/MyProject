package com.hj.myproject;

import androidx.annotation.NonNull;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class WritingActivity extends Dialog {

    EditText edtTodo;
    ToDoDatabase db;
    String today;
    String todo;
    TextView txt_write;
    Handler handler =new Handler();

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

        edtTodo.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                txt_write.setText(edtTodo.getText().toString());

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });





        findViewById(R.id.btnOK).setOnClickListener(v -> { //OK버튼 클릭시
            todo = edtTodo.getText().toString();
            db.insert(today,todo);
            edtTodo.setText("");
            dismiss();
        });
    }

    private void init(){
        edtTodo = findViewById(R.id.edtTodo);
        txt_write=findViewById(R.id.txt_write);
        db = new ToDoDatabase(getContext(),"data",null,1);
    }
}
