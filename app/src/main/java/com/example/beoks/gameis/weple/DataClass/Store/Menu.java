package com.example.beoks.gameis.weple.DataClass.Store;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.annotation.NonNull;
import android.util.Log;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;

public class Menu {
    public String key;
    public String imagePath;
    public String menuName;
    public int price;
    private final String TAG="Menu";

    public Menu() {
    }

    public Menu(String key,String imagePath, String menuName, int price) {
        this.key=key;
        this.imagePath = imagePath;
        this.menuName = menuName;
        this.price = price;
    }

    public void setImage(Bitmap bitmap){
        imagePath="Store/"+key+"/menu/"+menuName;
        StorageReference storageReference=FirebaseStorage.getInstance().getReference(imagePath);
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
    }

    public Bitmap getImage(int temp){
        StorageReference storageReference=FirebaseStorage.getInstance().getReference(imagePath);
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
