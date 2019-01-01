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
    public String key;
    public StoreContent wikiContent=null,ownerContent=null;
    public HashMap simpleReview=new HashMap(); // 리뷰이름 : 평점
    public List<LongReview> longReviewList=new ArrayList<LongReview>();
    public List<Menu> menuList=new ArrayList<Menu>();
    public long likeCount;

    public boolean ownerExist=false;
    public String ownerKey="";
    public boolean waitingOn=false,seatOn=false;
    public int seatNum,maxSeatNum;
    public List<WaitInfo> waitInfoList=new ArrayList<WaitInfo>();

    public Store(){

    }

    public Store(String key, StoreContent wikiContent, StoreContent ownerContent) {
        this.key = key;
        this.wikiContent = wikiContent;
        this.ownerContent = ownerContent;
    }

}
