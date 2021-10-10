package com.hj.myproject;

import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.RelativeLayout;
import android.widget.Switch;
import android.widget.TextView;

import androidx.annotation.DrawableRes;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class ToDoAdapter extends RecyclerView.Adapter<ToDoAdapter.ToDoViewHolder> {

    List<String> data = new ArrayList<>();
    int gradient;

    @NonNull
    @Override
    public ToDoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_item,parent,false);
        return new ToDoViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ToDoViewHolder holder, int position) {
        holder.checkToDo.setText(data.get(position));
        if(gradient==1) {
            holder.re_grd.setBackgroundResource(R.drawable.gradientgreen);
        }else if(gradient==2){
            holder.re_grd.setBackgroundResource(R.drawable.gradientblue);
        }
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


    class ToDoViewHolder extends RecyclerView.ViewHolder{
        CheckBox checkToDo;
        Switch aSwitch;
        RelativeLayout re_grd;

        public ToDoViewHolder(@NonNull View itemView) {
            super(itemView);
            checkToDo = itemView.findViewById(R.id.check_box);
            aSwitch = itemView.findViewById(R.id.todo_switch);
            re_grd =itemView.findViewById(R.id.re_grd);

        }
    }
}
