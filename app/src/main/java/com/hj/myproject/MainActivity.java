package com.hj.myproject;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.animation.ObjectAnimator;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Observable;
import java.util.Observer;
import java.util.Set;

public class MainActivity extends AppCompatActivity {

    Button btn_write;
    WritingActivity dialog;
    RecyclerView recyclerView;
    FloatingActionButton fb_btn,fb_pink,fb_green,fb_blue,fb_powderblue,fb_gold;
    ToDoDatabase db;
    int gradient =0 ;
    boolean isFabOpen=true;
    String tableName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        String today = setToday();

        init(today);
        ToDoAdapter adapter = createAdapter(today);
        ArrayList<String> data = db.select(today);

        setMainPage(adapter,today);
        adapter.setData(data);

//        adapter.setgradient();

        fb_btn=findViewById(R.id.fb_btn);
        fb_pink=findViewById(R.id.fb_pink);
        fb_green=findViewById(R.id.fb_green);
        fb_blue=findViewById(R.id.fb_blue);
        fb_powderblue=findViewById(R.id.fb_powderblue);
        fb_gold=findViewById(R.id.fb_gold);

        fb_btn.setOnClickListener(v -> {
            toggleFab();
        });

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
                  break;
              case R.id.btnNotToDoList:
                  btnToDo.setBackgroundResource(R.drawable.button_corners2);
                  btnToDo.setTextColor(Color.BLACK);
                  btnNotToDo.setBackgroundResource(R.drawable.button_corners3);
                  btnNotToDo.setTextColor(Color.parseColor("#e1918b"));
                  break;
          }
            db.tableChange();
          adapter.setData(db.select(today));
          adapter.notifyDataSetChanged();
        };

        btnToDo.setOnClickListener(listener);
        btnNotToDo.setOnClickListener(listener);
    }

    private void init(String today){ //초기화
        btn_write=findViewById(R.id.btn_write);
        recyclerView = findViewById(R.id.re_View);
        db = new ToDoDatabase(this,"data",null,1);
        dialog = new WritingActivity(MainActivity.this,today,db);
    }

    private String setToday(){ //오늘의 날짜를 구하기
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date();
        String today = simpleDateFormat.format(date);
        return today;
    }

    private ToDoAdapter createAdapter(String today){ //어댑터 생성
        ToDoAdapter adapter = new ToDoAdapter(db,today);
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


//            fab.setImageResource(R.drawable.ic_add); //눌렀을때 원래 그림으로 바뀌게하겠다.플러스표시
        }else{
            ObjectAnimator.ofFloat(fb_pink, "translationY", -200f).start();//특정 객체를 위로 올릴수있다 . ofFloat 띄워주겠다.
            ObjectAnimator.ofFloat(fb_green, "translationY", -400f).start();//ObjectAnimator.ofFloat 애니메이션 클래스
            ObjectAnimator.ofFloat(fb_blue, "translationY", -600f).start();//ObjectAnimator.ofFloat 애니메이션 클래스
            ObjectAnimator.ofFloat(fb_powderblue, "translationY", -800f).start();//ObjectAnimator.ofFloat 애니메이션 클래스
            ObjectAnimator.ofFloat(fb_gold, "translationY", -1000f).start();//ObjectAnimator.ofFloat 애니메이션 클래스
//            fab.setImageResource(R.drawable.ic_sub);//마이너스 표시
        }
        isFabOpen = !isFabOpen;
    }

}