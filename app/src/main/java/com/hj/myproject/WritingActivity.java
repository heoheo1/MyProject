package com.hj.myproject;

import androidx.annotation.NonNull;

import android.app.Dialog;
import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.widget.EditText;
import android.widget.TextView;

import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.ObservableEmitter;
import io.reactivex.rxjava3.core.ObservableOnSubscribe;
import io.reactivex.rxjava3.subjects.BehaviorSubject;

public class WritingActivity extends Dialog {

    EditText edtTodo;
    ToDoDatabase db;
    Context context;

    public WritingActivity(@NonNull Context context,ToDoDatabase db) {
        super(context);
        this.context=context;
        this.db = db;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_writing);
        setCancelable(true); //바깥족 취소 불가
        getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT)); // 배경 투명

        init(); // UI ID찾기

        findViewById(R.id.btnOK).setOnClickListener(v -> { //OK버튼 클릭시
            AppWidgetManager appWidgetManager =AppWidgetManager.getInstance(context);
            int appWidgetIds[] =appWidgetManager.getAppWidgetIds(new ComponentName(context,WidgetProvider.class));
            appWidgetManager.notifyAppWidgetViewDataChanged(appWidgetIds,R.id.widget_list);
            String todo = edtTodo.getText().toString();
            db.insert(todo);
            edtTodo.setText("");
            dismiss();
        });
    }

    @Override
    public void show() {
        super.show();
        writingTextView();
    }

    private void writingTextView() {
        TextView txtTodo = findViewById(R.id.txtToDo);
        edtTodo.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                txtTodo.setText(edtTodo.getText().toString());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    private void init(){
        edtTodo = findViewById(R.id.edtTodo);
    }
}
