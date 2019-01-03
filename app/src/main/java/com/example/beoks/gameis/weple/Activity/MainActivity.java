package com.example.beoks.gameis.weple.Activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.example.beoks.gameis.weple.Activity.LoginActivity.version02.LoginMainActivity;
import com.example.beoks.gameis.weple.R;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.google.firebase.auth.FirebaseAuth;

import java.net.InetAddress;
import java.util.Date;

/**
 * start point of activity
 *
 * description : 인터넷 연결확인 이후 로그인 액티비티로 이동
 */
public class MainActivity extends AppCompatActivity {
    private FirebaseAnalytics mFirebaseAnalytics;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FirebaseAuth.getInstance().signOut();
        LoginMainActivity.kakaoOnClickLogout();

        //change layout page to waiting page
        setContentView(R.layout.activity_main);
        mFirebaseAnalytics = FirebaseAnalytics.getInstance(this);

        //set open time
        Bundle bundle=new Bundle();
        Date date=new Date();
        bundle.putString("Time",date.toString());
        mFirebaseAnalytics.logEvent(FirebaseAnalytics.Event.APP_OPEN,bundle);

        //check Internet Connection
        if(isInternetAvailable()){
            Toast.makeText(getApplicationContext(),"인터넷 연결후 다시 실행해주세요!",Toast.LENGTH_SHORT);
        }
        else{
            //move to loginActivity
            Intent intent=new Intent(this,LoginMainActivity.class);
            startActivity(intent);
            finish();
        }

    }
    public boolean isInternetAvailable() {
        try {
            InetAddress ipAddr = InetAddress.getByName("google.com");
            //You can replace it with your name
            return !ipAddr.equals("");

        } catch (Exception e) {
            return false;
        }
    }

}
