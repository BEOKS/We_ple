package com.example.beoks.gameis.weple.Activity.LoginActivity.version01;

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

import com.example.beoks.gameis.weple.Activity.CustomerActivity.CustomerStoreListActivity;
import com.example.beoks.gameis.weple.Activity.OwnerActivity.OwnerStoreListActivity;
import com.example.beoks.gameis.weple.DataClass.GlobalData;
import com.example.beoks.gameis.weple.DataClass.Profile;
import com.example.beoks.gameis.weple.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

public class SignUpActivity extends AppCompatActivity {
    private FirebaseAuth mAuth;
    private FirebaseDatabase firebaseDatabase;

    private String type=null;
    private String nickname,email,pwd,pwdConfirm;

    /**
     * View instance
     */
    private Button typeSelectButton,signupButton;
    private EditText nickNameEditText,emailEditText,pwdEditText,pwdConfirmEditText;
    private TextView typeTextView;

    private String TAG="SignUpActivity";
    public static final String owner="사장님",customer="사용자";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        // init view instance
        typeSelectButton=(Button)findViewById(R.id.typeButton);
        signupButton=(Button)findViewById(R.id.signUpButton);
        setClickListener();

        nickNameEditText=(EditText)findViewById(R.id.nickNameEditText);
        emailEditText=(EditText)findViewById(R.id.emailEditText);
        pwdEditText=(EditText)findViewById(R.id.pwdEditText);
        pwdConfirmEditText=(EditText)findViewById(R.id.pwdConfirmEditText);

        typeTextView=(TextView)findViewById(R.id.typeTextView);

        //init FB instance
        mAuth = FirebaseAuth.getInstance();
        firebaseDatabase=FirebaseDatabase.getInstance();


    }

    private void setClickListener(){
        typeSelectButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(getApplicationContext(),UserSelectPopupActivity.class);
                startActivityForResult(intent,RQ_TYPE_SELECT);
            }
        });
        signupButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(type!=null){
                    emailSignUp();
                }
                else{
                    Toast.makeText(getApplicationContext(),"로그인 타입을 선택해주세요",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void moveActivity(String type){
        if(type.equals(Profile.owner)){
            Intent intent=new Intent(getApplicationContext(), OwnerStoreListActivity.class);
            startActivity(intent);
        }
        else{
            Intent intent=new Intent(getApplicationContext(),CustomerStoreListActivity.class);
            startActivity(intent);
        }
        finish();
    }

    public static final int RQ_TYPE_SELECT=1;
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        /**
         * Set type with typeTextView
         */
        if(requestCode==RQ_TYPE_SELECT&&resultCode==RESULT_OK&&data!=null){
            type=data.getStringExtra("type");
            typeTextView.setText(type);
        }
    }

    private void emailSignUp(){
        nickname=nickNameEditText.getText().toString();
        email=emailEditText.getText().toString();
        pwd=pwdEditText.getText().toString();
        pwdConfirm=pwdConfirmEditText.getText().toString();
        if(pwdConfirm.equals(pwd)){
            if(MainLoginActivity.isValidEmailAddress(email)){
                mAuth.createUserWithEmailAndPassword(email,pwd).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            Toast.makeText(getApplicationContext(),"회원가입 성공",Toast.LENGTH_SHORT).show();
                            GlobalData.loginProfile =new Profile(mAuth.getUid(),nickname,email,type);
                            moveActivity(type);
                        }
                        else{
                            Toast.makeText(getApplicationContext(),"회원가입 실패",Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
            else{
                Toast.makeText(getApplicationContext(),"이메일 형식이 올바르지 않습니다",Toast.LENGTH_SHORT).show();
            }
        }
        else{
            Toast.makeText(getApplicationContext(),"비밀 번호가 일치하지 않습니다",Toast.LENGTH_SHORT).show();
            Log.i(TAG,"password is not correct.");
        }

    }

}
