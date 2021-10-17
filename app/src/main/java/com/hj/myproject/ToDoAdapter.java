package com.hj.myproject;

import android.content.Context;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.RelativeLayout;
import android.widget.Switch;
import android.widget.TextView;

import androidx.annotation.DrawableRes;
import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class ToDoAdapter extends RecyclerView.Adapter<ToDoAdapter.ToDoViewHolder> {

    List<String> data = new ArrayList<>();
    int gradient;
    ToDoDatabase db;
    boolean ch;
    Context context;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;

    public ToDoAdapter(ToDoDatabase db,Context context) {
        this.db = db;
        this.context = context;
    }

    @NonNull
    @Override
    public ToDoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_item,parent,false);
        return new ToDoViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ToDoViewHolder holder, int position) {
        holder.check_box_txt.setText(data.get(position));

        if(ch==true) {

            holder.checkToDo.setChecked(true);
            holder.re_grd.setBackgroundResource(R.drawable.gradientgray);
        }else{
            holder.checkToDo.setChecked(false);
        }
        holder.checkToDo.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    sharedPreferences=context.getSharedPreferences("pref",context.MODE_PRIVATE);
                    editor=sharedPreferences.edit();
                    editor.putBoolean("ch",true);
                    editor.commit();
                    holder.re_grd.setBackgroundResource(R.drawable.gradientgray);
                }else{
                    sharedPreferences=context.getSharedPreferences("pref",context.MODE_PRIVATE);
                    editor=sharedPreferences.edit();
                    editor.putBoolean("ch",false);
                    editor.commit();
                    if(gradient==0) {
                        holder.re_grd.setBackgroundResource(R.drawable.gradientpink);
                    }else if(gradient==1){
                        holder.re_grd.setBackgroundResource(R.drawable.gradientgreen);
                    }else if(gradient==2){
                        holder.re_grd.setBackgroundResource(R.drawable.gradientblue);
                    }else if(gradient==3){
                        holder.re_grd.setBackgroundResource(R.drawable.gradientpowderblue);
                    }else if(gradient==4){
                        holder.re_grd.setBackgroundResource(R.drawable.gradientgold);
                    }else if(gradient==5){
                        holder.re_grd.setBackgroundResource(R.drawable.line_white);
                    }
                }
            }
        });

        if(gradient==0) {
            holder.re_grd.setBackgroundResource(R.drawable.gradientpink);
        }else if(gradient==1){
            holder.re_grd.setBackgroundResource(R.drawable.gradientgreen);
        }else if(gradient==2){
            holder.re_grd.setBackgroundResource(R.drawable.gradientblue);
        }else if(gradient==3){
            holder.re_grd.setBackgroundResource(R.drawable.gradientpowderblue);
        }else if(gradient==4){
            holder.re_grd.setBackgroundResource(R.drawable.gradientgold);
        }else if(gradient==5){
            holder.re_grd.setBackgroundResource(R.drawable.line_white);
        }

        holder.check_box_txt.setOnLongClickListener(v -> { //길게 클릭하였을때 (checkBox가 view의 크기의 대부분을 차지하고 있어서 checkBox로 사용)
            db.delete(data.get(position)); // today날짜의 todo의 내용과 같은걸 지운다.
            data.remove(position); //recyclerView의 data를 지운다.
            notifyDataSetChanged();
            return false;
        });

    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public void setData(ArrayList<String> data){
        this.data = data;
    }

    public  void setgradient(int gradient){
        this.gradient=gradient;
    }

    public  void setch(boolean ch){
        this.ch=ch;
    }

    class ToDoViewHolder extends RecyclerView.ViewHolder{
        CheckBox checkToDo;
        TextView check_box_txt;
        RelativeLayout re_grd;

        public ToDoViewHolder(@NonNull View itemView) {
            super(itemView);
            checkToDo = itemView.findViewById(R.id.check_box);
            re_grd =itemView.findViewById(R.id.re_grd);
            check_box_txt=itemView.findViewById(R.id.check_box_txt);


            checkToDo.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                }
            });

        }


    }

    public void clear(){
        data.clear();
        notifyDataSetChanged();
    }
}
