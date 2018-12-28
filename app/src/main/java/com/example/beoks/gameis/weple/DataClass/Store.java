package com.example.beoks.gameis.weple.DataClass;

import android.graphics.Bitmap;
import android.support.annotation.NonNull;
import android.util.Log;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;

public class Store {
    private final String TAG="Store";
    public String key;
    public DataSnapshot data;
    // 키 이름
    public static final String name="이름",location="장소",mainImagePath="메인 사진 경로",descript="상세설명",category="카테고리",hashTag="해쉬태그"; //필수 정보
    public static final String grade="평점",longReview="장문리뷰",likeCount="좋아요 숫자";

    public Store(final String key, String name, String location, Bitmap mainImage, String description,String category,String[] hashTagList){
        this.key=key;
        HashMap<String, Object> hashMap=new HashMap<>();
        hashMap.put(Store.name,name);
        hashMap.put(Store.location,location);
        hashMap.put(Store.mainImagePath,uploadImage(mainImage));
        hashMap.put(Store.descript,description);
        hashMap.put(Store.category,category);
        hashMap.put(Store.hashTag,hashTagList);
        hashMap.put(Store.likeCount,0);

        FirebaseDatabase.getInstance().getReference("Store").child(key).setValue(hashMap).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful()){
                    Log.i(TAG,"정보 생성 성공");
                    //sync
                    FirebaseDatabase.getInstance().getReference("Store").child(key).addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            data=dataSnapshot;
                            Log.i(TAG,"정보 동기화 성공");
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {
                            Log.i(TAG,"정보 동기화 취소");
                        }
                    });
                }
                else{
                    Log.e(TAG,"정보 생성 실패 + \n"+task.getException().toString());
                }
            }
        });


    }
    public String uploadImage(Bitmap mainImage){
        //TODO setting
        return null;
    }

}