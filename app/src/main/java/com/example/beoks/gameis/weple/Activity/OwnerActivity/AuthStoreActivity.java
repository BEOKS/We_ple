package com.example.beoks.gameis.weple.Activity.OwnerActivity;

import android.app.ProgressDialog;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.beoks.gameis.weple.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class AuthStoreActivity extends AppCompatActivity {
    private Button backButton,okButton;
    private EditText codeEditText;

    private ProgressDialog dialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_auth_store);
        initView();
    }
    private void initView(){
        backButton=(Button)findViewById(R.id.auth_backButton);
        okButton=(Button)findViewById(R.id.auth_okButton);
        initButton();

        codeEditText=(EditText)findViewById(R.id.auth_editText);
    }
    private void initButton(){
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        okButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String code=codeEditText.getText().toString();
                dialog=ProgressDialog.show(AuthStoreActivity.this,"","인증 코드 확인중...",true);
                FirebaseDatabase.getInstance().getReference("인증코드").child(code).addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        dialog.cancel();
                        if(dataSnapshot.exists()){
                            if(((String)dataSnapshot.getValue()).equals("가게 이름")){
                                Toast.makeText(getApplicationContext(),"개발자의 테스트용",Toast.LENGTH_SHORT).show();
                            }
                            else{
                                Toast.makeText(getApplicationContext(),"인증되었습니다.",Toast.LENGTH_SHORT).show();
                                String storeName=(String)dataSnapshot.getValue();
                                //TODO 초기 등록 액티비티로 이동, intent로 가게이름 전달
                            }
                        }
                        else{
                            Toast.makeText(getApplicationContext(),"잘못된 코드입니다.",Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                        dialog.cancel();
                        Toast.makeText(getApplicationContext(),"코드 인증이 중지되었습니다",Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }


}
