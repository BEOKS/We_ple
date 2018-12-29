package com.example.beoks.gameis.weple.Activity.JoinActivity;

import android.content.Intent;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.beoks.gameis.weple.DataClass.Profile;
import com.example.beoks.gameis.weple.R;

/**
 * 비밀 번호 입력: 비밀번호를 입력받고 유효하다면 Data클래스에 추가 후 다음 인텐트로 이동
 */
public class JoinActivityStep2 extends AppCompatActivity {
    private TextView textView;
    private EditText editText;
    private Button clickButton;
    private String TAG="JoinActivityStep2";
    int d=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_join_step2);
        initViewinstance();
        setTextView(Data.type);
        clickButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Data.pwd1=editText.getText().toString();
                if(isPwdValidate()){
                    Intent intent=new Intent(getApplicationContext(),JoinActivityStep3.class);
                    startActivity(intent);
                    finish();
                }
                else{
                    Snackbar.make(textView,"영문,숫자를 이용히여 6자리이상으로 입력해주세요",Snackbar.LENGTH_SHORT).show();
                }
            }
        });
    }
    private void initViewinstance(){
        textView=(TextView)findViewById(R.id.textView2);
        editText =(EditText)findViewById(R.id.editText2);
        clickButton=(Button)findViewById(R.id.button2);
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
    private boolean isPwdValidate(){
        if(Data.pwd1.length()<6){
            return false;
        }
        else{
            return true;
        }
    }
}
