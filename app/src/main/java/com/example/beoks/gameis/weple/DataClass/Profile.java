package com.example.beoks.gameis.weple.DataClass;

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
import java.util.HashMap;

/**
 * 사용자 또는 사장님의 프로필 정보
 * 데이터 : 닉네임, 이메일, 프로필이미지 저장소위치(클라우드),타입
 */
public class Profile {
    private final String TAG="Profile";
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
        Profile profile=dataSnapshot.getValue(Profile.class);
        key=profile.key;
        name=profile.name;
        email=profile.email;
        type=profile.type;
        profileImagePath=profile.profileImagePath;
    }

    //TODO set setter,getter about profileImage
    /**
     * 데이터를 파이어베이스 저장소에 저장한후 저장 위치를 반환
     * @param bitmap 업로드할 비트맵
     * @return 저장위치
     */
    public String uploadMainImage(Bitmap bitmap){
        profileImagePath="Profile/Image/"+key+".jpg";
        StorageReference storageReference=FirebaseStorage.getInstance().getReference(profileImagePath);
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
        return profileImagePath;
    }
    public Bitmap getMainImage(int temp){
        //TODO setting
        StorageReference storageReference=FirebaseStorage.getInstance().getReference(profileImagePath);
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
