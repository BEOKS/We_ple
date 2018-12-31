package com.example.beoks.gameis.weple.DataClass.Store;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.annotation.NonNull;
import android.util.Log;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Store {
    private final String TAG="Store";
    class KEY{
        //필수
        public static final String
                name="이름",
                location="장소",
                mainImagePath="메인 사진 경로",
                descript="상세설명",
                category="카테고리",
                hashTag="해쉬태그";
        //초기 자동생성 정보
        public static final String
                grade="평점"
                ,longReview="장문리뷰",
                likeMember="좋아요한 멤버 키";
    }
    //data
    public String key;
    public String name,location,mainImagePath,description,category;
    public String[] hashTag;

    public List<String> likeMember; // 좋아요를 누른사람의 각자 키를 저장
    public HashMap grade; // 종류 : 평점

    public Store(){

    }

    /**
     * 필수 정보를 포함한 생성자, 메모리에서 정보를 저장하고 파이어베이스에도 새로운 정보를 저장한다.
     * 부가정버와 초기자동생성 정보는 초기화하여 업로드한다.
     * @param key
     * @param name
     * @param location
     * @param mainImage
     * @param description
     * @param category
     * @param hashTagList
     */
    public Store(final String key, String name, String location, Bitmap mainImage, String description,String category,String[] hashTagList){
        this.key=key;
        this.name=name;
        this.location=location;
        this.mainImagePath=uploadImage(mainImage); //실시간 DB에는 사진이 저장된 클라우드 위치만 저장
        this.description=description;
        this.category=category;
        this.hashTag=hashTagList;

        final HashMap<String, Object> hashMap=new HashMap<>();
        hashMap.put(KEY.name,name);
        hashMap.put(KEY.location,location);
        hashMap.put(KEY.mainImagePath,this.mainImagePath);
        hashMap.put(KEY.descript,description);
        hashMap.put(KEY.category,category);
        hashMap.put(KEY.hashTag,hashTagList);

        hashMap.put(KEY.likeMember,0);

        FirebaseDatabase.getInstance().getReference("Store").child(key).setValue(hashMap).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful()){
                    Log.i(TAG,"정보 생성 성공");
                }
                else{
                    Log.e(TAG,"정보 생성 실패 + \n"+task.getException().toString());
                }
            }
        });

    }

    /**
     * 인스턴스들을 모두 파이어베이스 데이터베이스에 업데이트함
     * @param temp : 의미없음 파이어베이스에 클래스 인스턴스를 업로드할때 캐스팅 방지로 넣은것
     */
    public void updateData(int temp){
        FirebaseDatabase.getInstance().getReference("Store").child(key).setValue(this).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful()){
                    Log.i(TAG,"정보 갱신 성공");
                }
                else{
                    Log.e(TAG,"정보 갱신 실패 + \n"+task.getException().toString());
                }
            }
        });
    }

    /**
     * 데이터를 파이어베이스 저장소에 저장한후 저장 위치를 반환
     * @param bitmap 업로드할 비트맵
     * @return 저장위치
     */
    public String uploadImage(Bitmap bitmap){
        StorageReference storageReference=FirebaseStorage.getInstance().getReference("Profile/Image").child(key+".jpg");
        //compress bitmap
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] data = baos.toByteArray();
        //upload bitmap
        UploadTask uploadTask = storageReference.putBytes(data);
        uploadTask.addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
                // Handle unsuccessful uploads
                Log.e(TAG,"이미지 업로드 실패");
            }
        }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                // taskSnapshot.getMetadata() contains file metadata such as size, content-type, etc.
                // ...
                Log.i(TAG,"이미지 업로드 완료");
            }
        });
        return "Profile/Image/"+key+".jpg";
    }

    /**
     * 비트맵을 다운로드하고난 후
     * @return
     */
    public Bitmap downloadImage(){
        //TODO setting
        StorageReference storageReference=FirebaseStorage.getInstance().getReference(mainImagePath);
        final long ONE_MEGABYTE = 1024 * 1024;
        final Bitmap[] bitmap = new Bitmap[1];
        storageReference.getBytes(ONE_MEGABYTE).addOnSuccessListener(new OnSuccessListener<byte[]>() {
            @Override
            public void onSuccess(byte[] bytes) {
                // Data for "images/island.jpg" is returns, use this as needed
                Log.i(TAG,"이미지 다운로드 완료");
                bitmap[0] =BitmapFactory.decodeByteArray(bytes,0,bytes.length);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
                // Handle any errors
                Log.e(TAG,"이미지 다운로드 실패");
            }
        });
        return bitmap[0];
    }
}