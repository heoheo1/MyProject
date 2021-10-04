package com.hj.myproject;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ActionBar;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    Button btn_write;
    Dialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btn_write=findViewById(R.id.btn_write);
        View innerView = getLayoutInflater().inflate(R.layout.activity_writing,null);
        dialog=new Dialog(MainActivity.this);

        btn_write.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.setContentView(innerView);
                dialog.setCancelable(true);

                WindowManager.LayoutParams params = dialog.getWindow().getAttributes();
                params.width =  WindowManager.LayoutParams.MATCH_PARENT;
                params.height =WindowManager.LayoutParams.WRAP_CONTENT;
                dialog.getWindow().setAttributes(params);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

                dialog.show();
            }
        });
        //commit test
    }
}