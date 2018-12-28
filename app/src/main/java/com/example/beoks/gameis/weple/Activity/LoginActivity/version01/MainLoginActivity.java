package com.example.beoks.gameis.weple.Activity.LoginActivity.version01;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.beoks.gameis.weple.Activity.CustomerActivity.CustomerStoreListActivity;
import com.example.beoks.gameis.weple.Activity.OwnerActivity.OwnerStoreListActivity;
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

import java.util.ArrayList;
import java.util.List;

import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;

public class MainLoginActivity extends AppCompatActivity {
    private FirebaseAuth mAuth;
    private FirebaseDatabase firebaseDatabase;

    private String type=null;
    private String nickname,email,pwd;

    public static String defaultEmail="default@default.com";

    /**
     * View instance
     */
    private Button typeSelectButton,loginCheckButton,googleLoginButton,kakaoLoginButton,signupButton;
    private LoginButton kakaoButton;
    private EditText emailEditText,pwdEditText;
    private TextView typeTextView;

    private String TAG="MainLoginActivity";
    private SessionCallback callback;
    public static final String owner="사장님",customer="사용자";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_login);

        initViewInstance();

        //init FB instance
        mAuth = FirebaseAuth.getInstance();
        firebaseDatabase=FirebaseDatabase.getInstance();
        callback = new SessionCallback();
        Session.getCurrentSession().addCallback(callback);
        Session.getCurrentSession().checkAndImplicitOpen();

        //check login
        if(mAuth.getCurrentUser()!=null){ // already login
            firebaseDatabase.getReference("Profile").child(FirebaseAuth.getInstance().getUid()).child(Profile.type).addListenerForSingleValueEvent(new ValueEventListener() {
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
        typeSelectButton=(Button)findViewById(R.id.typeButton);
        loginCheckButton=(Button)findViewById(R.id.loginButton);
        googleLoginButton=(Button)findViewById(R.id.googleLoginButton);
        kakaoLoginButton=(Button)findViewById(R.id.kakaoLoginButton);
        signupButton=(Button)findViewById(R.id.signUpButton);
        kakaoButton=(LoginButton)findViewById(R.id.com_kakao_login);
        setClickListener();

        emailEditText=(EditText)findViewById(R.id.emailEditText);
        pwdEditText=(EditText)findViewById(R.id.pwdEditText);

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
                Intent intent=new Intent(getApplicationContext(),SignUpActivity.class);
                startActivity(intent);
            }
        });
    }

    private void moveActivity(String type){
        if(type.equals(Profile.owner)){
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
            typeTextView.setText(type);
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
                            Snackbar.make(findViewById(R.id.typeTextView), "구글 로그인 성공", Snackbar.LENGTH_SHORT).show();
                            if(task.getResult().getAdditionalUserInfo().isNewUser()){
                                email=mAuth.getCurrentUser().getEmail();
                                GlobalData.loginProfile=new Profile(mAuth.getUid(),mAuth.getCurrentUser().getDisplayName(),email,type);
                            }
                            else{
                                GlobalData.loginProfile=new Profile(mAuth.getUid());
                                moveActivity(type);
                            }
                            moveActivity(type);
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "signInWithCredential:failure", task.getException());
                            Snackbar.make(findViewById(R.id.typeTextView), "구글 로그인 실패.", Snackbar.LENGTH_SHORT).show();
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
            requestMe();
        }

        @Override
        public void onSessionOpenFailed(KakaoException exception) {
            if(exception != null) {
                Logger.e(exception);
            }
        }
    }

    public static void kakaoOnClickLogout() {
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

            }

            @Override
            public void onSuccess(MeV2Response response) {
                email=response.getKakaoAccount().getEmail();
                if(email==null){
                    email=defaultEmail;
                }
                nickname=response.getNickname();
                mAuth.createUserWithEmailAndPassword(email,"default123456").addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            GlobalData.loginProfile=new Profile(mAuth.getUid(),nickname,email,type);
                            Snackbar.make(findViewById(R.id.com_kakao_login),"카카오 회원가입 성공",Snackbar.LENGTH_SHORT).show();
                            moveActivity(type);
                        }
                        else{
                            if(task.getException() instanceof FirebaseAuthUserCollisionException){
                                mAuth.signInWithEmailAndPassword(email,"default123456").addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                    @Override
                                    public void onComplete(@NonNull Task<AuthResult> task) {
                                        if(task.isSuccessful()){
                                            GlobalData.loginProfile=new Profile(mAuth.getUid());
                                            Snackbar.make(findViewById(R.id.com_kakao_login),"카카오 로그인 성공",Snackbar.LENGTH_SHORT).show();
                                            moveActivity(type);
                                        }
                                        else{
                                            Snackbar.make(findViewById(R.id.com_kakao_login),"카카오 로그인 실패",Snackbar.LENGTH_SHORT).show();
                                        }
                                    }
                                });
                            }
                            else{
                                Log.e(TAG,task.getException().toString());
                                Snackbar.make(findViewById(R.id.com_kakao_login),"카카오 회원가입 실패",Snackbar.LENGTH_SHORT).show();
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
}
