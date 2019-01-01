package com.example.beoks.gameis.weple.DataClass.Store;

import android.support.annotation.NonNull;
import android.util.Log;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.List;

public class Store {
    public final String TAG="Store";
    public String key;
    public StoreContent wikiContent,ownerContent;
    public HashMap simpleReview; // 리뷰이름 : 평점
    public List<LongReview> longReviewList;
    public List<Menu> menuList;
    public long likeCount;

    public boolean ownerExist=false;
    public String ownerKey="";

    public boolean waitingOn,seatOn;

    public Store(){

    }

    public Store(String key, StoreContent wikiContent, StoreContent ownerContent) {
        this.key = key;
        this.wikiContent = wikiContent;
        this.ownerContent = ownerContent;
    }

}
