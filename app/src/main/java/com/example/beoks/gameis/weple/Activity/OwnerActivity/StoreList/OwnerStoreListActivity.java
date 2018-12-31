package com.example.beoks.gameis.weple.Activity.OwnerActivity.StoreList;

import android.content.Context;
import android.content.Intent;
import android.graphics.Canvas;
import android.graphics.Path;
import android.graphics.RectF;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.AttributeSet;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.example.beoks.gameis.weple.Activity.OwnerActivity.AuthStoreActivity;
import com.example.beoks.gameis.weple.R;

/**
 * 디자인 : design/Join/사장님/ 파일들 참고
 *
 */
public class OwnerStoreListActivity extends AppCompatActivity {
    private Button backButton,addButton;
    private LinearLayout linearLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_store_list);
        initView();

    }
    private void initView(){
        backButton=(Button)findViewById(R.id.storeList_backButton);
        addButton=(Button)findViewById(R.id.storeList_add_new_loc);
        setListener();

        linearLayout=(LinearLayout)findViewById(R.id.storeList_linearlayout);

    }
    private void setListener(){
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(getApplicationContext(),AuthStoreActivity.class);
                startActivity(intent);
            }
        });
    }
}


