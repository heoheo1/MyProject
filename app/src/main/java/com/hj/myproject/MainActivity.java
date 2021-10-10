package com.hj.myproject;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.animation.ObjectAnimator;
import android.content.DialogInterface;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class MainActivity extends AppCompatActivity {

    Button btn_write;
    WritingActivity dialog;
    RecyclerView recyclerView;
    FloatingActionButton fb_btn,fb_pink,fb_green,fb_blue,fb_powderblue,fb_gold;
    ToDoDatabase db;
    int gradient =0 ;
    boolean isFabOpen=true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        String today = setToday();
        init(today);
        ToDoAdapter adapter = createAdapter();
        ArrayList<String> data = db.select(today);
        adapter.setData(data);
//        adapter.setgradient();

        fb_btn=findViewById(R.id.fb_btn);
        fb_pink=findViewById(R.id.fb_pink);
        fb_green=findViewById(R.id.fb_green);
        fb_blue=findViewById(R.id.fb_blue);
        fb_powderblue=findViewById(R.id.fb_powderblue);
        fb_gold=findViewById(R.id.fb_gold);

        fb_green.setOnClickListener(v -> {
            adapter.setgradient(1); //변수로 저장 다음에도 사용
            adapter.notifyDataSetChanged();
        });
        fb_blue.setOnClickListener(v->{
            adapter.setgradient(2);
            adapter.notifyDataSetChanged();
        });

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