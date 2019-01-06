package com.example.beoks.gameis.weple.DataClass.Store;

import android.support.annotation.NonNull;
import android.util.Log;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Store {
    public final String TAG="Store";
    public String key,name;
    public StoreContent wikiContent=new StoreContent("wiki"),ownerContent=new StoreContent("owner");
    public HashMap simpleReview=new HashMap(); // 리뷰이름 : 평점
    public List<LongReview> longReviewList=new ArrayList<LongReview>();

    public long likeCount=0;
    public List<String> likeUser=new ArrayList<String>();

    public boolean ownerExist=false;
    public String ownerKey="";
    public boolean waitingOn=false,seatOn=false;
    public int seatNum=0,maxSeatNum=0;
    public List<WaitInfo> waitInfoList=new ArrayList<WaitInfo>();

    public Store(){

    }

    public Store(String name,boolean isDownload){
        this.name=name;
        if(isDownload){
            syncData(0);
        }
        else{
            ownerContent.name=name;
            wikiContent.name=name;
        }
    }

    public Store(String key, StoreContent wikiContent, StoreContent ownerContent) {
        this.key = key;
        this.wikiContent = wikiContent;
        this.ownerContent = ownerContent;
    }
    public void updateData(int temp){
        FirebaseDatabase.getInstance().getReference("Store").child(name).setValue(this);
    }
    public void syncData(int temp){
        FirebaseDatabase.getInstance().getReference("Store").child(name).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Store store=dataSnapshot.getValue(Store.class);
                key=store.key;
                name=store.name;
                wikiContent=store.wikiContent;
                ownerContent=store.ownerContent;
                simpleReview=store.simpleReview;
                longReviewList=dataSnapshot.child("longReviewList").getValue(ArrayList.class);
                likeCount=store.likeCount;
                likeUser=dataSnapshot.child("likeUser").getValue(ArrayList.class);
                ownerExist=store.ownerExist;
                ownerKey=store.ownerKey;
                waitingOn=store.waitingOn;
                seatOn=store.seatOn;
                seatNum=store.seatNum;
                maxSeatNum=store.maxSeatNum;
                waitInfoList=dataSnapshot.child("waitInfoListr").getValue(ArrayList.class);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
