package com.hj.myproject;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class MainActivity extends AppCompatActivity {

    Button btn_write;
    WritingActivity dialog;
    RecyclerView recyclerView;
    ToDoDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        String today = setToday();
        init(today);
        ToDoAdapter adapter = createAdapter();
        ArrayList<String> data = db.select(today);
        adapter.setData(data);

        btn_write.setOnClickListener(v -> {
            dialog.show();
            Window window = dialog.getWindow();
            window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
        });

        dialog.setOnDismissListener(new DialogInterface.OnDismissListener() { //dialog가 dismiss시 발동한다.
            @Override
            public void onDismiss(DialogInterface dialog) {
                adapter.setData(db.select(today));
                adapter.notifyDataSetChanged();
            }
        });
    }

    private void init(String today){ //초기화
        btn_write=findViewById(R.id.btn_write);
        recyclerView = findViewById(R.id.re_View);
        db = new ToDoDatabase(this,"data",null,1);
        dialog = new WritingActivity(MainActivity.this,today);
    }

    private String setToday(){ //오늘의 날짜를 구하기
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date();
        String today = simpleDateFormat.format(date);
        return today;
    }

    private ToDoAdapter createAdapter(){ //어댑터 생성
        ToDoAdapter adapter = new ToDoAdapter();
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
        return adapter;
    }
}