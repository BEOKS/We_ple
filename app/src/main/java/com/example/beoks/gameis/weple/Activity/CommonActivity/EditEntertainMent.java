package com.example.beoks.gameis.weple.Activity.CommonActivity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import com.example.beoks.gameis.weple.R;

import java.util.HashMap;

public class EditEntertainMent extends AppCompatActivity {
    protected static HashMap<String,EntertainButton> hashMap=new HashMap<String,EntertainButton>();
    private String[] strings={"음식","카페/디저트","생활","엔터테이먼트","술집"};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_entertain_ment);
        LinearLayout linearLayout=findViewById(R.id.entertain_layout);
        for(String string : strings){
            hashMap.put(string,new EntertainButton(getApplicationContext(),string));
            linearLayout.addView(hashMap.get(string));
        }
        if(StoreData.category!=null){
            hashMap.get(StoreData.category).check();
        }
        ((Button)findViewById(R.id.auth_backButton)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setResult(StoreInfoActivity.EDIT_CATEGORY);
                finish();
            }
        });
        ((Button)findViewById(R.id.storeInfo_all_editButton)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setResult(StoreInfoActivity.EDIT_CATEGORY);
                finish();
            }
        });
    }
}
