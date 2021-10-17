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
import java.util.HashMap;
import java.util.List;

public class ToDoAdapter extends RecyclerView.Adapter<ToDoAdapter.ToDoViewHolder> {
    final int CHECKED = 1;
    final int NOT_CHECKED = 0;

    List<String> data = new ArrayList<>();
    HashMap<String, Integer> checkData;
    int gradient, ch;
    ToDoDatabase db;
    Context context;

    public ToDoAdapter(ToDoDatabase db, Context context) {
        this.db = db;
        this.context = context;
    }

    @NonNull
    @Override
    public ToDoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_item, parent, false);
        return new ToDoViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ToDoViewHolder holder, int position) {
        holder.check_box_txt.setText(data.get(position));
        holder.checkToDo.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    ch = CHECKED;
                    holder.re_grd.setBackgroundResource(R.drawable.gradientgray);
                } else {
                    ch = NOT_CHECKED;
                    if (gradient == 0) {
                        holder.re_grd.setBackgroundResource(R.drawable.gradientpink);
                    } else if (gradient == 1) {
                        holder.re_grd.setBackgroundResource(R.drawable.gradientgreen);
                    } else if (gradient == 2) {
                        holder.re_grd.setBackgroundResource(R.drawable.gradientblue);
                    } else if (gradient == 3) {
                        holder.re_grd.setBackgroundResource(R.drawable.gradientpowderblue);
                    } else if (gradient == 4) {
                        holder.re_grd.setBackgroundResource(R.drawable.gradientgold);
                    } else if (gradient == 5) {
                        holder.re_grd.setBackgroundResource(R.drawable.line_white);
                    }
                }
            }
        });

        if (gradient == 0) {
            holder.re_grd.setBackgroundResource(R.drawable.gradientpink);
        } else if (gradient == 1) {
            holder.re_grd.setBackgroundResource(R.drawable.gradientgreen);
        } else if (gradient == 2) {
            holder.re_grd.setBackgroundResource(R.drawable.gradientblue);
        } else if (gradient == 3) {
            holder.re_grd.setBackgroundResource(R.drawable.gradientpowderblue);
        } else if (gradient == 4) {
            holder.re_grd.setBackgroundResource(R.drawable.gradientgold);
        } else if (gradient == 5) {
            holder.re_grd.setBackgroundResource(R.drawable.line_white);
        }

        holder.checkToDo.setChecked(false); //기본적으론 false의 형태로 나와야 한다.
        if(checkData.get(data.get(position)) != null) {
            int isChecked = checkData.get(data.get(position));

            if (isChecked == CHECKED) { // 만약 checkData를 통해 CHECKED를 확인하면 true의 형태로 바꿔준다.
                holder.checkToDo.setChecked(true);
                holder.re_grd.setBackgroundResource(R.drawable.gradientgray);
            }
        }

        holder.check_box_txt.setOnLongClickListener(v -> { //길게 클릭하였을때 (checkBox가 view의 크기의 대부분을 차지하고 있어서 checkBox로 사용)
            db.delete(data.get(position)); // today날짜의 todo의 내용과 같은걸 지운다.
            data.remove(position); //recyclerView의 data를 지운다.
            checkData = db.isChecked(); // checkBox의 데이터를 다시 검색해서 유지시킨다.
            notifyDataSetChanged();
            return false;
        });
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public void setData(ArrayList<String> data) {
        this.data = data;
    }

    public void setgradient(int gradient) {
        this.gradient = gradient;
    }

    class ToDoViewHolder extends RecyclerView.ViewHolder {
        CheckBox checkToDo;
        TextView check_box_txt;
        RelativeLayout re_grd;

        public ToDoViewHolder(@NonNull View itemView) {
            super(itemView);
            checkToDo = itemView.findViewById(R.id.check_box);
            re_grd = itemView.findViewById(R.id.re_grd);
            check_box_txt = itemView.findViewById(R.id.check_box_txt);

            checkToDo.setOnClickListener(v -> {
                String todo = check_box_txt.getText().toString();
                db.update(ch, todo);
            });
        }
    }

    public void clear() {
        data.clear();
        notifyDataSetChanged();
    }

    public void setCheckData(HashMap checkData){
        this.checkData = checkData;
    }
}
