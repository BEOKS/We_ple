package com.example.beoks.gameis.weple.Activity.JoinActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;

import com.example.beoks.gameis.weple.Activity.CustomerActivity.CustomerStoreListActivity;
import com.example.beoks.gameis.weple.Activity.OwnerActivity.OwnerStoreListActivity;
import com.example.beoks.gameis.weple.DataClass.GlobalData;
import com.example.beoks.gameis.weple.DataClass.Profile;
import com.example.beoks.gameis.weple.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class JoinActivityStep5 extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_join_step5);
        Button butto=(Button)findViewById(R.id.button5);
        butto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent;
                if(Data.type.equals(Profile.owner)){
                    intent=new Intent(getApplicationContext(),OwnerStoreListActivity.class);
                    startActivity(intent);
                    finish();
                }
                else if(Data.type.equals(Profile.customer)){
                    intent=new Intent(getApplicationContext(),CustomerStoreListActivity.class);
                    startActivity(intent);
                    finish();
                }
                else{
                    Log.e("JoinActivityStep5","타입이 올바르지 않습니다.");
                }
            }
        });
    }
}
