package com.hj.myproject;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.animation.ObjectAnimator;
import android.app.AlarmManager;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Observable;
import java.util.Observer;
import java.util.Set;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    Button btn_write;
    WritingActivity dialog;
    RecyclerView recyclerView;
    FloatingActionButton fb_btn,fb_pink,fb_green,fb_blue,fb_powderblue,fb_gold,fb_lineWhite;
    ToDoDatabase db;
    int gradient =0 ;
    boolean isFabOpen=true;
    String tableName;
    ToDoAdapter adapter;
    SharedPreferences sharedPreferences;
    TextView text_today;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        String today = setToday();
        text_today=findViewById(R.id.text_today);
        text_today.setText(today);


        init();
        adapter = createAdapter();

        ArrayList<String> data = db.select();
        setMainPage(adapter,today);
        adapter.setData(data);

        sharedPreferences=getSharedPreferences("pref",MODE_PRIVATE);
        gradient=sharedPreferences.getInt("gradient",0);

        HashMap<Integer,Boolean> checkData = new HashMap<>(); //position과 boolean값 찾기
        for(int i = 0; i < data.size(); i++){
            boolean ch=sharedPreferences.getBoolean("ch"+i,false);
            int position = sharedPreferences.getInt("position"+i,-1);
            Log.d("yousin","main -> position : "+position+", ch : "+ch);
            checkData.put(position,ch);
        }
        adapter.setCheckBox(checkData); //포지션과 boolean값을 hashMap에 넣고 보내기
        adapter.setgradient(gradient);
        adapter.notifyDataSetChanged();

        btn_write.setOnClickListener(v -> {
            dialog.show();
            Window window = dialog.getWindow();
            window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
        });

        dialog.setOnDismissListener(new DialogInterface.OnDismissListener() { //dialog가 dismiss시 발동한다.
            @Override
            public void onDismiss(DialogInterface dialog) {
                adapter.setData(db.select());
                adapter.notifyDataSetChanged();
            }
        });
    }

    private void setMainPage(ToDoAdapter adapter, String today) {
        Button btnToDo = findViewById(R.id.btnToDoList);
        Button btnNotToDo = findViewById(R.id.btnNotToDoList);

        View.OnClickListener listener = v -> {
            switch(v.getId()){
                case R.id.btnToDoList :
                    btnToDo.setBackgroundResource(R.drawable.button_corners3);
                    btnToDo.setTextColor(Color.parseColor("#e1918b"));
                    btnNotToDo.setBackgroundResource(R.drawable.button_corners2);
                    btnNotToDo.setTextColor(Color.BLACK);
                    tableName = "tblToDo";
                    break;
                case R.id.btnNotToDoList:
                    btnToDo.setBackgroundResource(R.drawable.button_corners2);
                    btnToDo.setTextColor(Color.BLACK);
                    btnNotToDo.setBackgroundResource(R.drawable.button_corners3);
                    btnNotToDo.setTextColor(Color.parseColor("#e1918b"));
                    tableName = "tblNotToDo";
                    break;
            }
            db.setTableName(tableName);
            adapter.setData(db.select());
            adapter.notifyDataSetChanged();
        };

        btnToDo.setOnClickListener(listener);
        btnNotToDo.setOnClickListener(listener);

        findViewById(R.id.wasteBasket).setOnClickListener(v -> { //휴지통을 클릭한다면 모든 데이터를 지워야 한다!
            AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
            builder.setTitle("전체 삭제 하시겠습니까?")
                    .setNegativeButton("삭제", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    Toast.makeText(getApplicationContext(), "리스트를 전체 지웁니다.", Toast.LENGTH_SHORT).show();
                    db.clear();
                    adapter.clear();
                }
            }).setPositiveButton("취소", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {

                }
            }); //builder
            AlertDialog alertDialog = builder.create();
            alertDialog.setCancelable(false);
            alertDialog.show();
        });//wasteBasket
    }

    private void init(){ //초기화
        btn_write=findViewById(R.id.btn_write);
        recyclerView = findViewById(R.id.re_View);
        fb_btn =findViewById(R.id.fb_btn);
        fb_btn.setOnClickListener(this);
        fb_pink=findViewById(R.id.fb_pink);
        fb_pink.setOnClickListener(this);
        fb_green=findViewById(R.id.fb_green);
        fb_green.setOnClickListener(this);
        fb_blue=findViewById(R.id.fb_blue);
        fb_blue.setOnClickListener(this);
        fb_powderblue=findViewById(R.id.fb_powderblue);
        fb_powderblue.setOnClickListener(this);
        fb_gold= findViewById(R.id.fb_gold);
        fb_gold.setOnClickListener(this);
        fb_lineWhite=findViewById(R.id.fb_lineWhite);
        fb_lineWhite.setOnClickListener(this);
        db = ToDoDatabase.getInstance(this);
        dialog = new WritingActivity(MainActivity.this,db);
    }

    private String setToday(){ //오늘의 날짜를 구하기
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy/MM/dd");
        Date date = new Date();
        String today = simpleDateFormat.format(date);
        return today;
    }

    private ToDoAdapter createAdapter(){ //어댑터 생성
        ToDoAdapter adapter = new ToDoAdapter(db,this);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
        return adapter;
    }

    private void toggleFab(){   //눌렀을때 나오고 눌렀을때 사라지고
        if(!isFabOpen) {

            ObjectAnimator.ofFloat(fb_pink, "translationY", 0f).start();//특정 객체를 위로 올릴수있다 . ofFloat 띄워주겠다.
            ObjectAnimator.ofFloat(fb_green, "translationY", 0f).start();//ObjectAnimator.ofFloat 애니메이션 클래스
            ObjectAnimator.ofFloat(fb_blue, "translationY", 0f).start();//ObjectAnimator.ofFloat 애니메이션 클래스
            ObjectAnimator.ofFloat(fb_powderblue, "translationY", 0f).start();//ObjectAnimator.ofFloat 애니메이션 클래스
            ObjectAnimator.ofFloat(fb_gold, "translationY", 0f).start();//ObjectAnimator.ofFloat 애니메이션 클래스
            ObjectAnimator.ofFloat(fb_lineWhite, "translationY", 0f).start();//ObjectAnimator.ofFloat 애니메이션 클래스


//            fab.setImageResource(R.drawable.ic_add); //눌렀을때 원래 그림으로 바뀌게하겠다.플러스표시
        }else{
            ObjectAnimator.ofFloat(fb_pink, "translationY", -200f).start();//특정 객체를 위로 올릴수있다 . ofFloat 띄워주겠다.
            ObjectAnimator.ofFloat(fb_green, "translationY", -400f).start();//ObjectAnimator.ofFloat 애니메이션 클래스
            ObjectAnimator.ofFloat(fb_blue, "translationY", -600f).start();//ObjectAnimator.ofFloat 애니메이션 클래스
            ObjectAnimator.ofFloat(fb_powderblue, "translationY", -800f).start();//ObjectAnimator.ofFloat 애니메이션 클래스
            ObjectAnimator.ofFloat(fb_gold, "translationY", -1000f).start();//ObjectAnimator.ofFloat 애니메이션 클래스
            ObjectAnimator.ofFloat(fb_lineWhite, "translationY", -1200f).start();//ObjectAnimator.ofFloat 애니메이션 클래스
//            fab.setImageResource(R.drawable.ic_sub);//마이너스 표시
        }
        isFabOpen = !isFabOpen;
    }

    private void fab(int gradient){
        adapter.setgradient(gradient); //변수로 저장 다음에도 사용
        sharedPreferences =getSharedPreferences("pref",MODE_PRIVATE);
        SharedPreferences.Editor editor =sharedPreferences.edit();
        editor.putInt("gradient",gradient);
        editor.commit();
        adapter.notifyDataSetChanged();

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case (R.id.fb_btn):
                toggleFab();
                break;
            case (R.id.fb_pink):
                fab(0);
                break;
            case (R.id.fb_green):
                fab(1);
                break;
            case (R.id.fb_blue):
                fab(2);
                break;
            case (R.id.fb_powderblue):
                fab(3);
                break;
            case (R.id.fb_gold):
                fab(4);
                break;
            case (R.id.fb_lineWhite):
                fab(5);
                break;

        }
    }
}