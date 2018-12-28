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

import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;

/**
 * 회원가입 버튼을 클릭시 이동하는 첫 번째 레이아웃
 *
 * 사용법: 레이아웃을 요청할때 intent에 type : ( owner or customer)를 추가하여 액티비티 요청을 해야한다.
 */
public class JoinActivityStep1 extends AppCompatActivity {
    private TextView textView;
    private EditText emailEditText;
    private Button clickButton;

    private String TAG="JoinActivityStep1";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_join_step1);

        initViewinstance();
        setTextView(Data.type=getIntent().getStringExtra("type"));
        clickButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email=emailEditText.getText().toString();
                if(isValidEmailAddress(email)){
                    Intent intent=new Intent(getApplicationContext(),JoinActivityStep2.class);
                    Data.email=email;
                    startActivity(intent);
                    finish();
                }
                else{
                    Snackbar.make(textView,"이메일 형식이 올바르지 않습니다.",Snackbar.LENGTH_SHORT).show();
                }
            }
        });

    }
    private void initViewinstance(){
        textView=(TextView)findViewById(R.id.textView);
        emailEditText=(EditText)findViewById(R.id.editText);
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

    public static boolean isValidEmailAddress(String email) {
        boolean result = true;
        try {
            InternetAddress emailAddr = new InternetAddress(email);
            emailAddr.validate();
        } catch (AddressException ex) {
            result = false;
        }
        return result;
    }

}
