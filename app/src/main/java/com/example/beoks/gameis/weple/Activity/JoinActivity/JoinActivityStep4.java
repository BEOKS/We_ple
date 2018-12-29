package com.example.beoks.gameis.weple.Activity.JoinActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.beoks.gameis.weple.DataClass.GlobalData;
import com.example.beoks.gameis.weple.DataClass.Profile;
import com.example.beoks.gameis.weple.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class JoinActivityStep4 extends AppCompatActivity {
    private TextView textView;
    private EditText editText;
    private Button clickButton;
    private String TAG="JoinActivityStep4";
    private ProgressDialog dialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_join_step4);
        initViewinstance();
        setTextView(Data.type);
        clickButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Data.nickname=editText.getText().toString();
                dialog=ProgressDialog.show(JoinActivityStep4.this,"","새로운 계정 생성중...",true);
                FirebaseAuth.getInstance().createUserWithEmailAndPassword(Data.email,Data.pwd1).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        dialog.cancel();
                        if(task.isSuccessful()){
                            GlobalData.loginProfile=new Profile(FirebaseAuth.getInstance().getUid(),Data.nickname,Data.email,Data.type);
                            Intent intent=new Intent(getApplicationContext(),JoinActivityStep5.class);
                            startActivity(intent);
                            finish();
                        }
                        else{
                            Toast.makeText(getApplicationContext(),"계정 생성실패 다시 시도해주세요",Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });
    }
    private void initViewinstance(){
        textView=(TextView)findViewById(R.id.textView4);
        editText =(EditText)findViewById(R.id.editText4);
        clickButton=(Button)findViewById(R.id.button4);
    }
    private void setTextView(String type){
        if(type.equals(Profile.customer)){
            textView.setText(Profile.customer+"로 로그인하기");
        }
        else if(type.equals(Profile.owner)){
            textView.setText(Profile.owner+"으로 로그인하기");
        }
        else{
            Log.e(TAG,"setTextView() : 타입이 정확하지 않습니다");
        }
    }
}
