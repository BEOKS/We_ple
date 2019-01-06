package com.example.beoks.gameis.weple.Activity.CommonActivity.EditActivity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.beoks.gameis.weple.R;

/**
 * 사용법 intext에 태그 배열을 추가한후 액티비티를 실행한다.
 * 액티비티가 종료되면 updateHashTagList()를 통해서 업데이트한다.
 */
public class TagEditActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tag_edit);
    }
}
