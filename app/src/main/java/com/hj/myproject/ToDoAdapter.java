package com.hj.myproject;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.Switch;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class ToDoAdapter extends RecyclerView.Adapter<ToDoAdapter.ToDoViewHolder> {

    List<String> data = new ArrayList<>();
    int gradient=0;

    @NonNull
    @Override
    public ToDoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_item,parent,false);
        return new ToDoViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ToDoViewHolder holder, int position) {
        holder.checkToDo.setText(data.get(position));
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

        public ToDoViewHolder(@NonNull View itemView) {
            super(itemView);
            checkToDo = itemView.findViewById(R.id.check_box);
            aSwitch = itemView.findViewById(R.id.todo_switch);
        }
    }
}
