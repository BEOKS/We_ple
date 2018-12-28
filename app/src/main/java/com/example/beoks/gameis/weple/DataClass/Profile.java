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

public class Profile {
    //data
    public String key;
    public DataSnapshot data;
    public static final String name="닉네임",email="이메일",profileImagePath="프로필이미지 위치";
    public static final String type="타입",owner="사장님",customer="사용자";

    /**
     *  make new user
     * @param key
     * @param name
     * @param email
     * @param type
     */
    public Profile(String key, String name, String email, String type){
        HashMap<String,String> hashMap = new HashMap<String,String>();
        hashMap.put(Profile.name,name);
        hashMap.put(Profile.email,email);
        hashMap.put(Profile.type,type);
        this.key=key;
        FirebaseDatabase.getInstance().getReference("Profile").child(key).setValue(hashMap).addOnCompleteListener(new OnCompleteListener<Void>() {
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
     * Download user data from DB
     * @param key
     */
    public Profile(String key){
        FirebaseDatabase.getInstance().getReference("Profile").child(key).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                data =dataSnapshot;
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
                data =dataSnapshot;
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.e("Profile","syncDataSnapShot canceled.");
            }
        });
    }

    //TODO set setter,getter about profileImage
}
