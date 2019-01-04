package com.example.beoks.gameis.weple.Activity.LoginActivity.version02;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.beoks.gameis.weple.Activity.CustomerActivity.CustomerStoreListActivity;
import com.example.beoks.gameis.weple.Activity.JoinActivity.JoinActivityStep1;
import com.example.beoks.gameis.weple.Activity.LoginActivity.version01.UserSelectPopupActivity;
import com.example.beoks.gameis.weple.Activity.OwnerActivity.StoreList.OwnerStoreListActivity;
import com.example.beoks.gameis.weple.DataClass.GlobalData;
import com.example.beoks.gameis.weple.DataClass.Profile;
import com.example.beoks.gameis.weple.R;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.kakao.auth.ISessionCallback;
import com.kakao.auth.Session;
import com.kakao.network.ErrorResult;
import com.kakao.usermgmt.LoginButton;
import com.kakao.usermgmt.UserManagement;
import com.kakao.usermgmt.callback.LogoutResponseCallback;
import com.kakao.usermgmt.callback.MeV2ResponseCallback;
import com.kakao.usermgmt.response.MeV2Response;
import com.kakao.util.exception.KakaoException;
import com.kakao.util.helper.log.Logger;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;

import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;

import static com.kakao.util.helper.Utility.getPackageInfo;

public class LoginMainActivity extends AppCompatActivity {
    private FirebaseAuth mAuth;
    private FirebaseDatabase firebaseDatabase;

    private String type=null;
    private String nickname,email,pwd;

    public static String defaultEmail="default_main_page@default_main_page.com";

    /**
     * View instance
     */
    private Button typeSelectButton,loginCheckButton,googleLoginButton,kakaoLoginButton,signupButton;
    private LoginButton kakaoButton;
    private EditText emailEditText,pwdEditText;
    private TextView typeTextView;

    private String TAG="MainLoginActivity";
    private SessionCallback callback;
    public static final String owner="owner",customer="customer";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_empty_activity);

        initViewInstance();
        Log.i(TAG,getKeyHash(getApplicationContext()));

        //init FB instance
        mAuth = FirebaseAuth.getInstance();
        firebaseDatabase=FirebaseDatabase.getInstance();
        callback = new SessionCallback();
        Session.getCurrentSession().addCallback(callback);
        Session.getCurrentSession().checkAndImplicitOpen();

        //check login
        if(mAuth.getCurrentUser()!=null){ // already login
            firebaseDatabase.getReference("Profile").child(FirebaseAuth.getInstance().getUid()).child("type").addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    String type=(String)dataSnapshot.getValue();
                    moveActivity(type);
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                    Log.e(TAG,"Getting user type is canceled.");
                    Toast.makeText(getApplicationContext(),"사용자 정보 다운로드 실패",Toast.LENGTH_SHORT).show();
                }
            });

        }

    }
    private void initViewInstance(){
        // init view instance
        typeSelectButton=(Button)findViewById(R.id.loginmain_typeButton);
        loginCheckButton=(Button)findViewById(R.id.loginmain_loginButton);
        googleLoginButton=(Button)findViewById(R.id.loginmain_googleLoginButton);
        kakaoLoginButton=(Button)findViewById(R.id.loginmain_kakaoLoginButton);
        signupButton=(Button)findViewById(R.id.loginmain_signUpButton);
        kakaoButton=(LoginButton)findViewById(R.id.com_kakao_login);
        setClickListener();

        emailEditText=(EditText)findViewById(R.id.loginmain_emailEditText);
        pwdEditText=(EditText)findViewById(R.id.loginmain_pwdEditText);

        typeTextView=(TextView)findViewById(R.id.typeTextView);
    }
    private void setClickListener(){
        typeSelectButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(getApplicationContext(),UserSelectPopupActivity.class);
                startActivityForResult(intent,RQ_TYPE_SELECT);
            }
        });
        loginCheckButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(type!=null){
                    emailLogin();
                }
                else{
                    Toast.makeText(getApplicationContext(),"로그인 타입을 선택해주세요",Toast.LENGTH_SHORT).show();
                }
            }
        });
        googleLoginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(type!=null){
                    googleLogin();
                }
                else{
                    Toast.makeText(getApplicationContext(),"로그인 타입을 선택해주세요",Toast.LENGTH_SHORT).show();
                }
            }
        });
        kakaoLoginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(type!=null){
                    kakaoLogin();
                }
                else{
                    Toast.makeText(getApplicationContext(),"로그인 타입을 선택해주세요",Toast.LENGTH_SHORT).show();
                }
            }
        });
        signupButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(type!=null){
                    Intent intent=new Intent(getApplicationContext(),JoinActivityStep1.class);
                    intent.putExtra("type",type);
                    startActivity(intent);
                }
                else{
                    Toast.makeText(getApplicationContext(),"로그인 타입을 선택해주세요",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void moveActivity(String type){
        if(type.equals(owner)){
            Intent intent=new Intent(getApplicationContext(), OwnerStoreListActivity.class);
            startActivity(intent);
        }
        else{
            Intent intent=new Intent(getApplicationContext(),CustomerStoreListActivity.class);
            startActivity(intent);
        }
        finish();
    }

    public static final int RQ_TYPE_SELECT=1,RC_SIGN_IN=123;
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (Session.getCurrentSession().handleActivityResult(requestCode, resultCode, data)) {
            return;
        }

        super.onActivityResult(requestCode, resultCode, data);
        /**
         * Set type with typeTextView
         */
        if(requestCode==RQ_TYPE_SELECT&&resultCode==RESULT_OK&&data!=null){
            type=data.getStringExtra("type");
            typeTextView.setText(type.equals(owner)?"사장님":"사용자");
        }

        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                // Google Sign In was successful, authenticate with Firebase
                GoogleSignInAccount account = task.getResult(ApiException.class);
                firebaseAuthWithGoogle(account);
            } catch (ApiException e) {
                // Google Sign In failed, update UI appropriately
                Log.w(TAG, "Google sign in failed", e);
                // ...
            }
        }
    }


    private void emailLogin(){
        email=emailEditText.getText().toString();
        pwd=pwdEditText.getText().toString();
        if(isValidEmailAddress(email)){
            mAuth.signInWithEmailAndPassword(email,pwd).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if(task.isSuccessful()){
                        Toast.makeText(getApplicationContext(),"이메일 로그인 성공",Toast.LENGTH_SHORT).show();
                        Log.i(TAG,"email login success.");
                        GlobalData.loginProfile=new Profile(mAuth.getUid());
                        moveActivity(type);
                    }
                    else{
                        Toast.makeText(getApplicationContext(),"이메일 로그인 실패",Toast.LENGTH_SHORT).show();
                        Log.i(TAG,"email login fail.");
                    }
                }
            });
        }
        else{
            Toast.makeText(getApplicationContext(),"이메일 형식이 맞지 않습니다",Toast.LENGTH_SHORT).show();
        }
    }
    private void googleLogin(){
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();
        Snackbar.make(googleLoginButton,"구글 로그인 중",Snackbar.LENGTH_SHORT).show();
        GoogleSignInClient mGoogleSignInClient;
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }
    private void firebaseAuthWithGoogle(GoogleSignInAccount acct) {
        Log.d(TAG, "firebaseAuthWithGoogle:" + acct.getId());
        AuthCredential credential = GoogleAuthProvider.getCredential(acct.getIdToken(), null);
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "signInWithCredential:success");
                            Toast.makeText(getApplicationContext(), "구글 로그인 성공", Toast.LENGTH_SHORT).show();
                            if(task.getResult().getAdditionalUserInfo().isNewUser()){
                                email=mAuth.getCurrentUser().getEmail();
                                GlobalData.loginProfile=new Profile(mAuth.getUid(),mAuth.getCurrentUser().getDisplayName(),email,type);
                            }
                            else{
                                GlobalData.loginProfile=new Profile(mAuth.getUid());
                            }
                            moveActivity(type);
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "signInWithCredential:failure", task.getException());
                            Toast.makeText(getApplicationContext(), "구글 로그인 실패.", Toast.LENGTH_SHORT).show();
                        }

                        // ...
                    }
                });
    }

    private void kakaoLogin(){
        kakaoButton.performClick();
    }

    private class SessionCallback implements ISessionCallback {

        @Override
        public void onSessionOpened() {
            Snackbar.make(googleLoginButton,"카카오 로그인 중",Snackbar.LENGTH_SHORT).show();
            Log.i(TAG,"kakao : onSessionOpened");
            requestMe();
        }

        @Override
        public void onSessionOpenFailed(KakaoException exception) {
            Log.i(TAG,"kakao : onSessionFail");
            Log.e(TAG,exception.toString());
            if(exception != null) {
                Logger.e(exception);
            }
        }
    }

    public static void kakaoOnClickLogout() {
        Session.getCurrentSession().clearCallbacks();
        UserManagement.getInstance().requestLogout(new LogoutResponseCallback() {
            @Override
            public void onCompleteLogout() {

            }
        });
    }

    private void requestMe() {
        List<String> keys = new ArrayList<>();
        keys.add("properties.nickname");
        keys.add("properties.profile_image");
        keys.add("kakao_account.email");

        UserManagement.getInstance().me(keys, new MeV2ResponseCallback() {
            @Override
            public void onFailure(ErrorResult errorResult) {
                String message = "failed to get user info. msg=" + errorResult;
                Logger.d(message);
            }

            @Override
            public void onSessionClosed(ErrorResult errorResult) {
                Log.i(TAG,"kakao : onSessionClose");
            }

            @Override
            public void onSuccess(MeV2Response response) {
                email=response.getKakaoAccount().getEmail();
                Log.i(TAG,"kakao : onSuccess");
                if(email==null){
                    email=defaultEmail;
                }
                nickname=response.getNickname();
                mAuth.createUserWithEmailAndPassword(email,"default123456").addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            GlobalData.loginProfile=new Profile(mAuth.getUid(),nickname,email,type);
                            Toast.makeText(getApplicationContext(),"카카오 회원가입 성공",Toast.LENGTH_SHORT).show();
                            moveActivity(type);
                        }
                        else{
                            if(task.getException() instanceof FirebaseAuthUserCollisionException){
                                mAuth.signInWithEmailAndPassword(email,"default123456").addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                    @Override
                                    public void onComplete(@NonNull Task<AuthResult> task) {
                                        if(task.isSuccessful()){
                                            GlobalData.loginProfile=new Profile(mAuth.getUid());
                                            Toast.makeText(getApplicationContext(),"카카오 로그인 성공",Toast.LENGTH_SHORT).show();
                                            moveActivity(type);
                                        }
                                        else{
                                            Toast.makeText(getApplicationContext(),"카카오 로그인 실패",Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                });
                            }
                            else{
                                Log.e(TAG,task.getException().toString());
                                Toast.makeText(getApplicationContext(),"카카오 회원가입 실패",Toast.LENGTH_SHORT).show();
                            }
                        }
                    }
                });
            }

        });
    }

    public static boolean isValidEmailAddress(String email) {
        boolean result = true;
        try {
            InternetAddress emailAddr = new InternetAddress(email);
            emailAddr.validate();
        } catch (AddressException ex) {
            result = false;
        }
        return result;
    }

    public String getKeyHash(final Context context) {
        PackageInfo packageInfo = getPackageInfo(context, PackageManager.GET_SIGNATURES);
        if (packageInfo == null)
            return null;

        for (Signature signature : packageInfo.signatures) {
            try {
                MessageDigest md = MessageDigest.getInstance("SHA");
                md.update(signature.toByteArray());
                return Base64.encodeToString(md.digest(), Base64.NO_WRAP);
            } catch (NoSuchAlgorithmException e) {
                Log.w(TAG, "Unable to get MessageDigest. signature=" + signature, e);
            }
        }
        return null;
    }
}
