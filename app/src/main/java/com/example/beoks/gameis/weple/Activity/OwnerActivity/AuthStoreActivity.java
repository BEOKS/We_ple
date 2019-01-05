package com.example.beoks.gameis.weple.Activity.OwnerActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.beoks.gameis.weple.Activity.CommonActivity.StoreData;
import com.example.beoks.gameis.weple.Activity.CommonActivity.StoreInfoActivity;
import com.example.beoks.gameis.weple.DataClass.GlobalData;
import com.example.beoks.gameis.weple.DataClass.Store.Store;
import com.example.beoks.gameis.weple.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

/**
 * 사장님이 자신의 가게를 인증하는 액티비티입니다.
 */
public class AuthStoreActivity extends AppCompatActivity {
    private Button backButton,okButton;
    private EditText codeEditText;

    private ProgressDialog dialog;

    public final String NAME="name";
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
                final String code=codeEditText.getText().toString();
                dialog=ProgressDialog.show(AuthStoreActivity.this,"","인증 코드 확인중...",true);
                FirebaseDatabase.getInstance().getReference("인증코드").child(code).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        dialog.cancel();
                        if(dataSnapshot.exists()){
                            /**
                             * 인증코드는 항상 영문+숫자
                             */
                            if(dataSnapshot.child("소유자").exists()){
                                Toast.makeText(getApplicationContext(),"이미 주인이 등록되었습니다.",Toast.LENGTH_SHORT).show();
                            }
                            else{
                                Toast.makeText(getApplicationContext(),"인증되었습니다.",Toast.LENGTH_SHORT).show();
                                String storeName=(String)dataSnapshot.child("name").getValue();
                                FirebaseDatabase.getInstance().getReference("인증코드").child(code).child("소유자").setValue(GlobalData.loginProfile.key);
                                StoreData.store=new Store();
                                StoreData.store.name=storeName;
                                FirebaseDatabase.getInstance().getReference("Store").child(storeName).setValue(StoreData.store);
                                Intent intent=new Intent(getApplicationContext(),StoreInfoActivity.class);
                                intent.putExtra("type","owner");
                                startActivity(intent);
                                finish();
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
