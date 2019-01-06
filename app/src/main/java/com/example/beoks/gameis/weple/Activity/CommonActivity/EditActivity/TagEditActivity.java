package com.example.beoks.gameis.weple.Activity.CommonActivity.EditActivity;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.example.beoks.gameis.weple.Activity.CommonActivity.StoreData;
import com.example.beoks.gameis.weple.Activity.CommonActivity.StoreInfoActivity;
import com.example.beoks.gameis.weple.DataClass.Store.Store;
import com.example.beoks.gameis.weple.R;
import com.google.android.flexbox.FlexboxLayout;

import java.util.ArrayList;
import java.util.List;

/**
 * 사용법 : hashTag에 intext에 타입을 추가한 후 액티비티를 실행한다.
 * 액티비티가 종료되면 updateHashTagList()를 통해서 업데이트한다.
 */
public class TagEditActivity extends AppCompatActivity {
    public EditText editText;
    public Button addButton;
    public FlexboxLayout flexboxLayout;
    public List<String> tagList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(getIntent().getStringExtra("type").equals("wiki")){
            tagList=StoreData.store.wikiContent.hashTag;
        }
        else{
            tagList=StoreData.store.ownerContent.hashTag;
        }
        setContentView(R.layout.activity_tag_edit);
        ((Button)findViewById(R.id.auth_backButton)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
        ((Button)findViewById(R.id.editTag_storeButton)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
        editText=findViewById(R.id.editTag_editText);
        addButton=findViewById(R.id.editTag_addbutton);
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                flexboxLayout.addView(new TagButton(getApplicationContext(),editText.getText().toString(),flexboxLayout,tagList));
                editText.setText("");
            }
        });
        flexboxLayout=findViewById(R.id.editTag_tagListLayout);

    }

    @Override
    public void onBackPressed() {
        StoreInfoActivity.wikiContentView.updateHashTagList();
        StoreInfoActivity.ownerContentView.updateHashTagList();
        super.onBackPressed();
    }
}
class TagButton extends android.support.v7.widget.AppCompatButton implements View.OnClickListener {
    public String text;
    public FlexboxLayout flexboxLayout;
    public List<String> hashTagList;
    public TagButton(Context context,String text,FlexboxLayout flexboxLayout,List<String> hashTagList) {
        super(context);
        this.text=text;
        this.flexboxLayout=flexboxLayout;
        this.hashTagList=hashTagList;
        setBackgroundResource(android.R.drawable.btn_default);
//        Drawable d = getResources().getDrawable(android.R.drawable.btn_default);
//        setBackgroundDrawable(d);
        setPadding(5,5,5,5);
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        setLayoutParams(layoutParams);
        setText(text);
    }


    @Override
    public void onClick(View view) {
        flexboxLayout.removeView(this);
        hashTagList.remove(text);
    }
}