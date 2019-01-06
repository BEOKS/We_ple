package com.example.beoks.gameis.weple.Activity.CommonActivity.EditActivity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import com.example.beoks.gameis.weple.Activity.CommonActivity.EditActivity.EditEntertainMent;
import com.example.beoks.gameis.weple.Activity.CommonActivity.StoreData;
import com.example.beoks.gameis.weple.R;

class EntertainButton extends LinearLayout {
    private boolean isCheck =false;
    private Button button;
    private String text;
    public EntertainButton(Context context, final String text) {
        super(context);
        this.text=text;
        String infService = Context.LAYOUT_INFLATER_SERVICE;
        LayoutInflater li = (LayoutInflater) getContext().getSystemService(infService);
        View v = li.inflate(R.layout.entertain_button, this, false);
        addView(v);
        button=findViewById(R.id.entertain_clickButton);
        button.setText(text);
        button.setBackgroundColor(Color.WHITE);
        button.setOnClickListener(new OnClickListener() {
            @SuppressLint("ResourceAsColor")
            @Override
            public void onClick(View view) {
                if(isCheck){
                    //나중에 배경 편집으로 바꿔주세요
                    unCheck();
                }
                else{
                    //나중에 배경 편집으로 바꿔주세요
                    check();
                    for(String string : EditEntertainMent.hashMap.keySet()){
                        if(!string.equals(text)){
                            EditEntertainMent.hashMap.get(string).unCheck();
                        }
                    }
                }
            }
        });
    }
    @SuppressLint("ResourceAsColor")
    public void check(){
        isCheck=true;
        StoreData.category =text;
        button.setBackgroundColor(R.color.colorAccent);
    }
    public void unCheck(){
        isCheck=false;
        button.setBackgroundColor(Color.WHITE);
    }
}