package com.example.beoks.gameis.weple.DataClass.Store;

import android.support.annotation.NonNull;
import android.util.Log;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Store {
    public final String TAG="Store";
    public String key,name;
    public StoreContent wikiContent=null,ownerContent=null;
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

    public Store(String key, StoreContent wikiContent, StoreContent ownerContent) {
        this.key = key;
        this.wikiContent = wikiContent;
        this.ownerContent = ownerContent;
    }
    public void updateData(int temp){
        FirebaseDatabase.getInstance().getReference("Store").child(name).setValue(this);
    }
}
