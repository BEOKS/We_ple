package com.example.beoks.gameis.weple.Activity.LoginActivity.version02;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.beoks.gameis.weple.R;

/**
 * 메인 로그인 액티비티는 비로그인 상태시 처음 생성된 로그인 화면입니다.
 * 로그인 화면은 res/design/Login 안의 이미지 파일을 참조해주세요
 *
 * 구현기능 :
 *  1. 사장님 or 일반 사용자 선택 버튼 -> 버튼을 선택시 팝업( Alert Dialog )를 띄어주도록 하여 선택을 유도한다 ( 이미지 파일 참조 )
 *  2. 1번 버튼을 통해 선택한후 로고 밑에 textView를 변경하도록함 사장님이면 "사장님 로그인"으로 사용자면 "사용자 로그인"으로 내용 변경
 *  3. 로그인 기능
 *      1. 로그인 버튼 클릭시 이메일 EditText의 내용을 가져온후 이메일이 유효한지 확인
 *      2. 3.1을 만족한 경우 Firebase에 로그인 시도
 *           1. 로그인을 성공한 경우 사장님은 사장님 액티비티로, 사용자는 카드뷰( 음식점 리스트 )로 이동하도록함
 *  4. 회원가입기능
 */
public class MainLoginActivity02 extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_login02);
    }
}
