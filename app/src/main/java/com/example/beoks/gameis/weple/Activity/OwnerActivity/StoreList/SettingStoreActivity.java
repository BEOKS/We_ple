package com.example.beoks.gameis.weple.Activity.OwnerActivity.StoreList;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Switch;
import android.widget.TextView;

import com.example.beoks.gameis.weple.R;

public class SettingStoreActivity extends AppCompatActivity {
    public TextView nameTextView;
    public Switch waitSwitch,seatSwitch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting_store);
        initView();
    }
    public void initView(){
        nameTextView =(TextView)findViewById(R.id.setting_store_name);
        waitSwitch=(Switch)findViewById(R.id.waitingSwitch);
        seatSwitch=(Switch)findViewById(R.id.seatSwitch);

        ((Button)findViewById(R.id.setting_store_backButton)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }
}
