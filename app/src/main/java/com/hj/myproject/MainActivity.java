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
import android.appwidget.AppWidgetManager;
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
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.hj.myproject.MyNotification.MyNotification;

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
    boolean isFabOpen=true, isNotify;
    String tableName;
    ToDoAdapter adapter;
    SharedPreferences sharedPreferences;
    TextView text_today;
    HashMap<String,Integer> checkData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        sharedPreferences=getSharedPreferences("pref",MODE_PRIVATE);
        gradient=sharedPreferences.getInt("gradient",0);
        
        String today = setToday();
        text_today=findViewById(R.id.text_today);
        text_today.setText(today);
        init();
        adapter = createAdapter();

        ArrayList<String> data = db.select();
        checkData = db.isChecked(); //???????????? checkData??? ????????????.
        setMainPage(adapter);
        adapter.setData(data);
        adapter.setCheckData(checkData);

        adapter.setgradient(gradient);
        adapter.notifyDataSetChanged();

        btn_write.setOnClickListener(v -> {
            dialog.show();
            Window window = dialog.getWindow();
            window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
        });

        dialog.setOnDismissListener(new DialogInterface.OnDismissListener() { //dialog??? dismiss??? ????????????.
            @Override
            public void onDismiss(DialogInterface dialog) {
                adapter.setData(db.select());
                adapter.setCheckData(db.isChecked());
                adapter.notifyDataSetChanged();
            }
        });
    }

    private void setMainPage(ToDoAdapter adapter) {

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
            adapter.setCheckData(db.isChecked()); // tbl??? ??????????????? ????????? checkData??? select????????????.
            adapter.notifyDataSetChanged();
        };

        btnToDo.setOnClickListener(listener);
        btnNotToDo.setOnClickListener(listener);

        findViewById(R.id.wasteBasket).setOnClickListener(v -> { //???????????? ??????????????? ?????? ???????????? ????????? ??????!
            AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
            builder.setTitle("?????? ?????? ???????????????????")
                    .setNegativeButton("??????", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    Toast.makeText(getApplicationContext(), "???????????? ?????? ????????????.", Toast.LENGTH_SHORT).show();
                    db.clear();
                    adapter.clear();
                    checkData.clear(); //?????? ????????? ????????? ???????????? ????????? HashMap??? ???????????? ????????????. ????????? checkData??? ?????? ??????????????? ??????.
                }
            }).setPositiveButton("??????", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {

                }
            }); //builder
            AlertDialog alertDialog = builder.create();
            alertDialog.setCancelable(false);
            alertDialog.show();
        });//wasteBasket

        ImageView notificationImage = findViewById(R.id.notifi_img);

        MyNotification notification = MyNotification.getInstance(this);
        isNotify = sharedPreferences.getBoolean("isNotify",false);
        notification.changeNotificationImage(notificationImage, isNotify);
        if(isNotify) {
            notification.setNotificationAlarm("????????? ?????????!!", "?????? ??????????????????...");
        }else{
            notification.cancelAlarm();
        }

        notificationImage.setOnClickListener(v -> {
            isNotify = !isNotify;
            notification.changeNotificationImage(notificationImage, isNotify);
            if(isNotify) {
                notification.setNotificationAlarm("????????? ?????????!!", "?????? ??????????????????...");
            }else{
                notification.cancelAlarm();
            }
        });
    }

    @Override
    protected void onDestroy() {
        sharedPreferences.edit().putBoolean("isNotify",isNotify).commit();
        super.onDestroy();
    }

    private void init(){ //?????????
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

    private String setToday(){ //????????? ????????? ?????????
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy/MM/dd");
        Date date = new Date();
        String today = simpleDateFormat.format(date);
        return today;
    }

    private ToDoAdapter createAdapter(){ //????????? ??????
        ToDoAdapter adapter = new ToDoAdapter(db,this);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
        return adapter;
    }

    private void toggleFab(){   //???????????? ????????? ???????????? ????????????
        if(!isFabOpen) {

            ObjectAnimator.ofFloat(fb_pink, "translationY", 0f).start();//?????? ????????? ?????? ??????????????? . ofFloat ???????????????.
            ObjectAnimator.ofFloat(fb_green, "translationY", 0f).start();//ObjectAnimator.ofFloat ??????????????? ?????????
            ObjectAnimator.ofFloat(fb_blue, "translationY", 0f).start();//ObjectAnimator.ofFloat ??????????????? ?????????
            ObjectAnimator.ofFloat(fb_powderblue, "translationY", 0f).start();//ObjectAnimator.ofFloat ??????????????? ?????????
            ObjectAnimator.ofFloat(fb_gold, "translationY", 0f).start();//ObjectAnimator.ofFloat ??????????????? ?????????
            ObjectAnimator.ofFloat(fb_lineWhite, "translationY", 0f).start();//ObjectAnimator.ofFloat ??????????????? ?????????


//            fab.setImageResource(R.drawable.ic_add); //???????????? ?????? ???????????? ??????????????????.???????????????
        }else{
            ObjectAnimator.ofFloat(fb_pink, "translationY", -200f).start();//?????? ????????? ?????? ??????????????? . ofFloat ???????????????.
            ObjectAnimator.ofFloat(fb_green, "translationY", -400f).start();//ObjectAnimator.ofFloat ??????????????? ?????????
            ObjectAnimator.ofFloat(fb_blue, "translationY", -600f).start();//ObjectAnimator.ofFloat ??????????????? ?????????
            ObjectAnimator.ofFloat(fb_powderblue, "translationY", -800f).start();//ObjectAnimator.ofFloat ??????????????? ?????????
            ObjectAnimator.ofFloat(fb_gold, "translationY", -1000f).start();//ObjectAnimator.ofFloat ??????????????? ?????????
            ObjectAnimator.ofFloat(fb_lineWhite, "translationY", -1200f).start();//ObjectAnimator.ofFloat ??????????????? ?????????
//            fab.setImageResource(R.drawable.ic_sub);//???????????? ??????
        }
        isFabOpen = !isFabOpen;
    }

    private void fab(int gradient){
        adapter.setgradient(gradient); //????????? ?????? ???????????? ??????
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