package com.example.beoks.gameis.weple.DataClass.Store;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.annotation.NonNull;
import android.util.Log;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class StoreContent {
    private final String TAG="StoreContent";
    //data
    //필수
    public String key;
    public String name,location,mainImagePath,description,category;
    public List<String> hashTag;
    public List<String> additionalInfo; // 제목 : 내용

    //추가 입력정보
    public List<Article> articles;
    public List<Menu> menus;
    /**
     * no-argument 생성자는 파이어베이스 저장에 필요하다
     */
    public StoreContent(){

    }

    public StoreContent(final String key, String name, String location, Bitmap mainImage, String description, String category, ArrayList<String> hashTagList,
                        ArrayList<String> additionalInfo){
        this.key=key;
        this.name=name;
        this.location=location;
        this.mainImagePath= uploadMainImage(mainImage); //실시간 DB에는 사진이 저장된 클라우드 위치만 저장
        this.description=description;
        this.category=category;
        this.hashTag=hashTagList;
        this.additionalInfo=additionalInfo;
        //추가 입력정보
        articles=new ArrayList<Article>();
        menus=new ArrayList<Menu>();

    }


    /**
     * 데이터를 파이어베이스 저장소에 저장한후 저장 위치를 반환
     * @param bitmap 업로드할 비트맵
     * @return 저장위치
     */
    public String uploadMainImage(Bitmap bitmap){
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
     * 비트맵을 다운로드한다.
     * @param temp 의미없음
     * @return 다운로드한 비트맵
     */
    public Bitmap getMainImage(int temp){
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