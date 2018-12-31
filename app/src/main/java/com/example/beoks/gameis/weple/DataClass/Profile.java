package com.example.beoks.gameis.weple.DataClass;

import android.support.annotation.NonNull;
import android.util.Log;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;

/**
 * 사용자 또는 사장님의 프로필 정보
 * 데이터 : 닉네임, 이메일, 프로필이미지 저장소위치(클라우드),타입
 */
public class Profile {
    public String key;
    //value instance
    public String name,email,profileImagePath,type;
    /**
     *  새로운 프로필을 생성 후 파이어베이스에 업로드
     * @param key
     * @param name
     * @param email
     * @param type
     */
    public Profile(String key, String name, String email, String type){
        this.key=key;
        this.name=name;
        this.email=email;
        this.type=type;
        this.key=key;
        FirebaseDatabase.getInstance().getReference("Profile").child(key).setValue(this).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(!task.isSuccessful()){
                    Log.i("Profile","uploadNewUser success.");
                    syncDataSnapShot();
                }
                else{
                    Log.e("Profile","uploadNewUser fail.");
                }
            }
        });

    }

    /**
     * 키를 참조해서 파이어베이스에서 다운로드
     * @param key
     */
    public Profile(String key){
        FirebaseDatabase.getInstance().getReference("Profile").child(key).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                convertToClass(dataSnapshot);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.e("Profile","syncDataSnapShot canceled.");
            }
        });
    }

    public void syncDataSnapShot(){
        FirebaseDatabase.getInstance().getReference("Profile").child(this.key).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                convertToClass(dataSnapshot);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.e("Profile","syncDataSnapShot canceled.");
            }
        });
    }
    private void convertToClass(DataSnapshot dataSnapshot){

    }

    //TODO set setter,getter about profileImage
}
