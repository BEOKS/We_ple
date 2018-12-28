package com.example.beoks.gameis.weple.Activity.JoinActivity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.beoks.gameis.weple.DataClass.Profile;
import com.example.beoks.gameis.weple.R;

/**
 *
 */
public class JoinActivityStep2 extends AppCompatActivity {
    private TextView textView;
    private EditText emailEditText;
    private Button clickButton;
    private String TAG="JoinActivityStep1";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_join02);
        initViewinstance();
    }
    private void initViewinstance(){
        textView=(TextView)findViewById(R.id.textView);
        emailEditText=(EditText)findViewById(R.id.emailEditText);
        clickButton=(Button)findViewById(R.id.button);
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
